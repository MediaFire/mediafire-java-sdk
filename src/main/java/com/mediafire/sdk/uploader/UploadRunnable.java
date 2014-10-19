package com.mediafire.sdk.uploader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.upload.CheckResponse;
import com.mediafire.sdk.api_responses.upload.InstantResponse;
import com.mediafire.sdk.api_responses.upload.PollResponse;
import com.mediafire.sdk.api_responses.upload.ResumableResponse;
import com.mediafire.sdk.client.PhpApiClient;
import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.RequestGenerator;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.uploader.uploaditem.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris Najar on 7/21/2014.
 */
public class UploadRunnable implements Runnable {
    private static final String TAG = UploadRunnable.class.getCanonicalName();
    private final int maxPolls;
    private final long millisecondsBetweenPolls;
    private final Configuration configuration;
    private final UploadItem uploadItem;
    private final UploadListenerInterface mfUploadListener;
    private final int maxUploadAttempts;
    private String utf8EncodedFileName;
    private final NetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor;
    private final UserCredentialsInterface mfCredentials;
    private final ActionTokenManagerInterface actionTokenManagerInterface;
    private final SessionTokenManagerInterface sessionTokenManagerInterface;

    private UploadRunnable(Builder builder) {
        this.maxPolls = builder.maxPolls;
        this.millisecondsBetweenPolls = builder.millisecondsBetweenPolls;
        this.uploadItem = builder.uploadItem;
        this.mfUploadListener = builder.mfUploadListener;
        this.maxUploadAttempts = builder.maxUploadAttempts;

        this.configuration = builder.configuration;

        this.mfNetworkConnectivityMonitor = configuration.getNetworkConnectivityMonitorInterface();
        this.mfCredentials = configuration.getUserCredentialsInterface();
        this.actionTokenManagerInterface = configuration.getActionTokenManagerInterface();
        this.sessionTokenManagerInterface = configuration.getSessionTokenManagerInterface();
    }

    @Override
    public void run() {
        configuration.getLogger().d(TAG, "run()");
        notifyUploadListenerStarted();
        if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
            notifyUploadListenerCancelled();
            return;
        }

        try {
            encodeFileNameUTF8();
            startOrRestartUpload();
        } catch (UnsupportedEncodingException e) {
            configuration.getLogger().e(TAG, "UnsupportedEncodingException during UploadRunnable", e);
            notifyUploadListenerCancelled();
        } catch (NoSuchAlgorithmException e) {
            configuration.getLogger().e(TAG, "NoSuchAlgorithmException during UploadRunnable", e);
            notifyUploadListenerCancelled();
        } catch (IOException e) {
            configuration.getLogger().e(TAG, "IOException during UploadRunnable", e);
            notifyUploadListenerCancelled();
        }
    }

    private void encodeFileNameUTF8() throws UnsupportedEncodingException {
        configuration.getLogger().d(TAG, "encodeFileNameUTF8");
        utf8EncodedFileName = new String(uploadItem.getFileName().getBytes("UTF-8"), "UTF-8");
    }

    private void checkUploadFinished(UploadItem uploadItem, CheckResponse checkResponse) throws NoSuchAlgorithmException, IOException {
        configuration.getLogger().d(TAG, "checkUploadFinished()");
        if (checkResponse == null) {
            notifyUploadListenerCancelled();
            return;
        }

        //as a preventable infinite loop measure, an upload item cannot continue after upload/check.php if it has gone through the process 20x
        //20x is high, but it should never happen and will allow for more information gathering.
        if (checkResponse.getStorageLimitExceeded()) {
            configuration.getLogger().d(TAG, "storage limit is exceeded");
            notifyUploadListenerCancelled();
        } else if (checkResponse.getResumableUpload().areAllUnitsReady() && !uploadItem.getPollUploadKey().isEmpty()) {
            configuration.getLogger().d(TAG, "all units are ready and poll upload key is not empty");
            // all units are ready and we have the poll upload key. start polling.
            doPollUpload();
        } else {
            if (checkResponse.doesHashExists()) { //hash does exist for the file
                hashExistsInCloud(checkResponse);
            } else {
                hashDoesNotExistInCloud(checkResponse);
            }
        }
    }

    private void hashExistsInCloud(CheckResponse checkResponse) {
        configuration.getLogger().d(TAG, "hash exists");
        if (!checkResponse.isInAccount()) { // hash which exists is not in the account
            hashExistsButDoesNotExistInAccount();
        } else { // hash exists and is in the account
            hashExistsInAccount(checkResponse);
        }
    }

    private void hashExistsInAccount(CheckResponse checkResponse) {
        configuration.getLogger().d(TAG, "hash is in account");
        boolean inFolder = checkResponse.isInFolder();
        configuration.getLogger().d(TAG, "ActionOnInAccount: " + uploadItem.getUploadOptions().getActionOnInAccount().toString());
        switch (uploadItem.getUploadOptions().getActionOnInAccount()) {
            case UPLOAD_ALWAYS:
                configuration.getLogger().d(TAG, "uploading...");
                doInstantUpload();
                break;
            case UPLOAD_IF_NOT_IN_FOLDER:
                configuration.getLogger().d(TAG, "uploading if not in folder.");
                if (!inFolder) {
                    configuration.getLogger().d(TAG, "uploading...");
                    doInstantUpload();
                } else {
                    configuration.getLogger().d(TAG, "already in folder, not uploading...");
                    notifyUploadListenerCompleted();
                }
                break;
            case DO_NOT_UPLOAD:
            default:
                configuration.getLogger().d(TAG, "not uploading...");
                notifyUploadListenerCompleted();
                break;
        }
    }

    private void hashExistsButDoesNotExistInAccount() {
        configuration.getLogger().d(TAG, "hash is not in account");
        doInstantUpload();
    }

    private void hashDoesNotExistInCloud(CheckResponse checkResponse) throws IOException, NoSuchAlgorithmException {
        // hash does not exist. call resumable.
        configuration.getLogger().d(TAG, "hash does not exist");
        if (checkResponse.getResumableUpload().getUnitSize() == 0) {
            configuration.getLogger().d(TAG, "unit size received from unit_size was 0. cancelling");
            notifyUploadListenerCancelled();
            return;
        }

        if (checkResponse.getResumableUpload().getNumberOfUnits() == 0) {
            configuration.getLogger().d(TAG, "number of units received from number_of_units was 0. cancelling");
            notifyUploadListenerCancelled();
            return;
        }

        if (checkResponse.getResumableUpload().areAllUnitsReady() && !uploadItem.getPollUploadKey().isEmpty()) {
            configuration.getLogger().d(TAG, "all units ready and have a poll upload key");
            // all units are ready and we have the poll upload key. start polling.
            doPollUpload();
        } else {
            configuration.getLogger().d(TAG, "all units not ready or do not have poll upload key");
            // either we don't have the poll upload key or all units are not ready
            doResumableUpload();
        }
    }

    private void instantUploadFinished() {
        configuration.getLogger().d(TAG, "instantUploadFinished()");
        notifyUploadListenerCompleted();
    }

    private void resumableUploadFinished(ResumableResponse response) throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "resumableUploadFinished()");
        if (response != null && response.getResumableUpload().areAllUnitsReady() && !response.getDoUpload().getPollUploadKey().isEmpty()) {
            doPollUpload();
        } else {
            doCheckUpload();
        }
    }

    private void pollUploadFinished(PollResponse pollResponse) throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "pollUploadFinished()");
        // if this method is called then file error and result codes are fine, but we may not have received status 99 so
        // check status code and then possibly send item to the backlog queue.
        PollResponse.DoUpload doUpload = pollResponse.getDoUpload();
        PollResponse.Status pollStatusCode = doUpload.getStatusCode();
        PollResponse.Result pollResultCode = doUpload.getResultCode();
        PollResponse.FileError pollFileErrorCode = doUpload.getFileErrorCode();

        configuration.getLogger().d(TAG, "status code: " + pollStatusCode.toString());
        configuration.getLogger().d(TAG, "result code: " + pollResultCode.toString());
        configuration.getLogger().d(TAG, "file error code: " + pollFileErrorCode.toString());

        if (pollStatusCode == PollResponse.Status.NO_MORE_REQUESTS_FOR_THIS_KEY && pollResultCode == PollResponse.Result.SUCCESS && pollFileErrorCode == PollResponse.FileError.NO_ERROR) {
            configuration.getLogger().d(TAG, "done polling");
            notifyUploadListenerCompleted();
        } else if (pollStatusCode != PollResponse.Status.NO_MORE_REQUESTS_FOR_THIS_KEY && pollResultCode == PollResponse.Result.SUCCESS && pollFileErrorCode == PollResponse.FileError.NO_ERROR) {
            configuration.getLogger().d(TAG, "still waiting for status code " + PollResponse.Status.NO_MORE_REQUESTS_FOR_THIS_KEY + ", but was " + pollStatusCode.toString() + " so restarting upload");
            startOrRestartUpload();
        } else {
            configuration.getLogger().d(TAG, "cancelling upload");
            notifyUploadListenerCancelled();
        }
    }

    private void doCheckUpload() throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "doCheckUpload()");
        if (!haveStoredCredentials()) {
            configuration.getLogger().d(TAG, "no credentials stored, cancelling upload for: " + uploadItem.getFileName());
            uploadItem.cancelUpload();
            return;
        }

        if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
            configuration.getLogger().d(TAG, "no network connection, cancelling upload for " + uploadItem.getFileName());
            notifyUploadListenerCancelled();
            return;
        }
        
        if (uploadItem.isCancelled()) {
            configuration.getLogger().d(TAG, "upload was cancelled for " + uploadItem.getFileName());
            notifyUploadListenerCancelled();
            return;
        }

        // generate map with request parameters
        Map<String, String> keyValue = generateCheckUploadRequestParameters();

        Request request = new RequestGenerator().generateRequestObject("1.2", "upload", "check.php");

        for (String key : keyValue.keySet()) {
            request.addQueryParameter(key, keyValue.get(key));
        }

        Result result = new PhpApiClient(configuration).doRequest(request);
        Response mfResponse = result.getResponse();

        if (mfResponse == null) {
            notifyUploadListenerCancelled();
            return;
        }

        if (mfResponse.getBytes() == null) {
            notifyUploadListenerCancelled();
            return;
        }

        if (mfResponse.getBytes().length == 0) {
            notifyUploadListenerCancelled();
            return;
        }

        if (getResponseObject(new String(mfResponse.getBytes()), ApiResponse.class) == null) {
            notifyUploadListenerCancelled();
            return;
        }

        CheckResponse response = getResponseObject(new String(mfResponse.getBytes()), CheckResponse.class);

        if (response == null) {
            notifyUploadListenerCancelled();
            return;
        }

        // if there is an error code, cancel the upload
        if (response.getErrorCode() != ApiResponse.ResponseCode.NO_ERROR) {
            notifyUploadListenerCancelled();
            return;
        }

        if (response.hasError()) {
            notifyUploadListenerCancelled();
            return;
        }

        uploadItem.getChunkData().setNumberOfUnits(response.getResumableUpload().getNumberOfUnits());
        uploadItem.getChunkData().setUnitSize(response.getResumableUpload().getUnitSize());
        int count = response.getResumableUpload().getBitmap().getCount();
        List<Integer> words = response.getResumableUpload().getBitmap().getWords();
        ResumableBitmap bitmap = new ResumableBitmap(count, words);
        uploadItem.setBitmap(bitmap);
        configuration.getLogger().d(TAG, uploadItem.getFileData().getFilePath() + " upload item bitmap: " + uploadItem.getBitmap().getCount() + " count, " + uploadItem.getBitmap().getWords().toString() + " words.");

        // notify listeners that check has completed
        checkUploadFinished(uploadItem, response);
    }

    private void doInstantUpload() {
        configuration.getLogger().d(TAG, "doInstantUpload()");

        if (!haveStoredCredentials()) {
            configuration.getLogger().d(TAG, "no credentials stored, task cancelling()");
            uploadItem.cancelUpload();
            return;
        }

        if (uploadItem.isCancelled()) {
            configuration.getLogger().d(TAG, "upload was cancelled for " + uploadItem.getFileName());
            notifyUploadListenerCancelled();
            return;
        }

        if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
            notifyUploadListenerCancelled();
            return;
        }

        // generate map with request parameters
        Map<String, String> keyValue = generateInstantUploadRequestParameters();
        Request request = new RequestGenerator().generateRequestObject("1.2", "upload", "instant.php");

        for (String key : keyValue.keySet()) {
            request.addQueryParameter(key, keyValue.get(key));
        }

        Result result = new PhpApiClient(configuration).doRequest(request);
        Response mfResponse = result.getResponse();

        if (mfResponse == null) {
            notifyUploadListenerCancelled();
            return;
        }

        if (mfResponse.getBytes() == null) {
            notifyUploadListenerCancelled();
            return;
        }

        if (mfResponse.getBytes().length == 0) {
            notifyUploadListenerCancelled();
            return;
        }

        if (getResponseObject(new String(mfResponse.getBytes()), ApiResponse.class) == null) {
            notifyUploadListenerCancelled();
            return;
        }

        InstantResponse response = getResponseObject(new String(mfResponse.getBytes()), InstantResponse.class);

        if (response == null) {
            notifyUploadListenerCancelled();
            return;
        }

        if (response.getErrorCode() != ApiResponse.ResponseCode.NO_ERROR) {
            notifyUploadListenerCancelled();
            return;
        }

        if (!response.getQuickkey().isEmpty()) {
            // notify listeners that check has completed
            instantUploadFinished();
        } else {
            notifyUploadListenerCancelled();
        }
    }

    private void doResumableUpload() throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "doResumableUpload()");

        //get file size. this will be used for chunks.
        FileData fileData = uploadItem.getFileData();
        long fileSize = fileData.getFileSize();

        // get chunk. these will be used for chunks.
        ChunkData ChunkData = uploadItem.getChunkData();
        int numChunks = ChunkData.getNumberOfUnits();
        int unitSize = ChunkData.getUnitSize();

        // loop through our chunks and create http post with header data and send after we are done looping,
        // let the listener know we are completed
        ResumableResponse response = null;
        for (int chunkNumber = 0; chunkNumber < numChunks; chunkNumber++) {
            if (!haveStoredCredentials()) {
                configuration.getLogger().d(TAG, "no credentials stored, task cancelling()");
                uploadItem.cancelUpload();
                return;
            }

            if (uploadItem.isCancelled()) {
                configuration.getLogger().d(TAG, "upload was cancelled for " + uploadItem.getFileName());
                notifyUploadListenerCancelled();
                return;
            }

            if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
                notifyUploadListenerCancelled();
                return;
            }

            // if the bitmap says this chunk number is uploaded then we can just skip it, if not, we upload it.
            if (!uploadItem.getBitmap().isUploaded(chunkNumber)) {
                // get the chunk size for this chunk
                int chunkSize = getChunkSize(chunkNumber, numChunks, fileSize, unitSize);

                ResumableChunkInfo resumableChunkInfo = createResumableChunkInfo(unitSize, chunkNumber);
                if (resumableChunkInfo == null) {
                    notifyUploadListenerCancelled();
                    return;
                }

                String chunkHash = resumableChunkInfo.getChunkHash();
                byte[] uploadChunk = resumableChunkInfo.getUploadChunk();

                printDebugCurrentChunk(chunkNumber, numChunks, chunkSize, unitSize, fileSize, chunkHash, uploadChunk);

                // generate the post headers
                Map<String, String> headers = generatePostHeaders(utf8EncodedFileName, fileSize, chunkNumber, chunkHash, chunkSize);
                // generate the get parameters
                Map<String, String> parameters = generateResumableRequestParameters();

                printDebugRequestData(headers, parameters);


                Request request = new RequestGenerator().generateRequestObject("1.2", "upload", "instant.php");

                for (String key : parameters.keySet()) {
                    request.addQueryParameter(key, parameters.get(key));
                }

                for (String key : headers.keySet()) {
                    request.addHeader(key, headers.get(key));
                }

                request.addPayload(uploadChunk);

                Result result = new PhpApiClient(configuration).doRequest(request);
                Response mfResponse = result.getResponse();

                if (mfResponse == null) {
                    notifyUploadListenerCancelled();
                    return;
                }

                if (mfResponse.getBytes() == null) {
                    notifyUploadListenerCancelled();
                    return;
                }

                if (mfResponse.getBytes().length == 0) {
                    notifyUploadListenerCancelled();
                    return;
                }

                if (getResponseObject(new String(mfResponse.getBytes()), ApiResponse.class) == null) {
                    notifyUploadListenerCancelled();
                    return;
                }

                response = getResponseObject(new String(mfResponse.getBytes()), ResumableResponse.class);

                // set poll upload key if possible
                if (shouldSetPollUploadKey(response)) {
                    uploadItem.setPollUploadKey(response.getDoUpload().getPollUploadKey());
                }

                if (shouldCancelUpload(response)) {
                    notifyUploadListenerCancelled();
                    return;
                }

                // update the response bitmap
                int count = response.getResumableUpload().getBitmap().getCount();
                List<Integer> words = response.getResumableUpload().getBitmap().getWords();
                ResumableBitmap bitmap = new ResumableBitmap(count, words);
                uploadItem.setBitmap(bitmap);
                configuration.getLogger().d(TAG, "(" + uploadItem.getFileData().getFilePath() + ") upload item bitmap: " + uploadItem.getBitmap().getCount() + " count, (" + uploadItem.getBitmap().getWords().toString() + ") words.");

                clearReferences(chunkSize, chunkHash, uploadChunk, headers, parameters);
            }

            notifyUploadListenerOnProgressUpdate(numChunks);

        } // end loop

        // let the listeners know that upload has attempted to upload all chunks.
        resumableUploadFinished(response);
    }

    private void doPollUpload() throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "doPollUpload()");
        //generate our request string
        Map<String, String> keyValue = generatePollRequestParameters();

        int pollCount = 0;
        do {
            if (!haveStoredCredentials()) {
                configuration.getLogger().d(TAG, "no credentials stored, task cancelling()");
                uploadItem.cancelUpload();
                return;
            }

            if (uploadItem.isCancelled()) {
                configuration.getLogger().d(TAG, "upload was cancelled for " + uploadItem.getFileName());
                notifyUploadListenerCancelled();
                return;
            }

            if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
                notifyUploadListenerCancelled();
                return;
            }
            // increment counter
            pollCount++;
            // get api response.

            Request request = new RequestGenerator().generateRequestObject("1.2", "upload", "poll.php");

            for (String key : keyValue.keySet()) {
                request.addQueryParameter(key, keyValue.get(key));
            }

            Result result = new PhpApiClient(configuration).doRequest(request);
            Response mfResponse = result.getResponse();

            if (mfResponse == null) {
                notifyUploadListenerCancelled();
                return;
            }

            if (mfResponse.getBytes() == null) {
                notifyUploadListenerCancelled();
                return;
            }

            if (mfResponse.getBytes().length == 0) {
                notifyUploadListenerCancelled();
                return;
            }


            if (getResponseObject(new String(mfResponse.getBytes()), ApiResponse.class) == null) {
                notifyUploadListenerCancelled();
                return;
            }

            PollResponse response = getResponseObject(new String(mfResponse.getBytes()), PollResponse.class);

            configuration.getLogger().d(TAG, "received error code: " + response.getErrorCode());
            //check to see if we need to call pollUploadCompleted or loop again
            switch (response.getErrorCode()) {
                case NO_ERROR:
                    //just because we had response/result "Success" doesn't mean everything is good.
                    //we need to find out if we should continue polling or not
                    //  conditions to check:
                    //      first   -   result code no error? yes, keep calm and poll on. no, cancel upload because error.
                    //      second  -   fileerror code no error? yes, carry on old chap!. no, cancel upload because error.
                    //      third   -   status code 99 (no more requests)? yes, done. no, continue.
                    if (response.getDoUpload().getResultCode() != PollResponse.Result.SUCCESS) {
                        configuration.getLogger().d(TAG, "result code: " + response.getDoUpload().getResultCode().toString() + " need to cancel");
                        notifyUploadListenerCancelled();
                        return;
                    }

                    if (response.getDoUpload().getFileErrorCode() != PollResponse.FileError.NO_ERROR) {
                        configuration.getLogger().d(TAG, "result code: " + response.getDoUpload().getFileErrorCode().toString() + " need to cancel");
                        notifyUploadListenerCancelled();
                        return;
                    }

                    if (response.getDoUpload().getStatusCode() == PollResponse.Status.NO_MORE_REQUESTS_FOR_THIS_KEY) {
                        configuration.getLogger().d(TAG, "status code: " + response.getDoUpload().getStatusCode().toString());
                        pollUploadFinished(response);
                        return;
                    }
                    break;
                default:
                    // stop polling and inform listeners we cancel because API result wasn't "Success"
                    notifyUploadListenerCancelled();
                    return;
            }

            notifyUploadListenerOnPolling(response.getDoUpload().getDescription());

            //wait before next api call
            try {
                Thread.sleep(millisecondsBetweenPolls);
            } catch (InterruptedException e) {
                configuration.getLogger().e(TAG, "Exception: " + e);
                notifyUploadListenerCancelled();
                return;
            }

            if (uploadItem.isCancelled()) {
                pollCount = maxPolls;
            }
        } while (pollCount < maxPolls);

        startOrRestartUpload();
    }

    private Map<String, String> generatePollRequestParameters() {
        configuration.getLogger().d(TAG, "generatePollRequestParameters()");
        LinkedHashMap<String, String> keyValue = new LinkedHashMap<String, String>();
        keyValue.put("key", uploadItem.getPollUploadKey());
        keyValue.put("response_format", "json");
        return keyValue;
    }

    @SuppressWarnings({"ParameterCanBeLocal", "UnusedParameters", "UnusedAssignment"})
    private void clearReferences(int chunkSize, String chunkHash, byte[] uploadChunk, Map<String, String> headers, Map<String, String> parameters) {
        chunkSize = 0;
        chunkHash = null;
        uploadChunk = null;
        headers = null;
        parameters = null;
    }

    private void printDebugRequestData(Map<String, String> headers, Map<String, String> parameters) {
        configuration.getLogger().d(TAG, "printDebugRequestData()");
        configuration.getLogger().d(TAG, "headers: " + headers.toString());
        configuration.getLogger().d(TAG, "parameters: " + parameters.toString());
    }

    @SuppressWarnings("UnusedParameters")
    private void printDebugCurrentChunk(int chunkNumber, int numChunks, int chunkSize, int unitSize, long fileSize, String chunkHash, byte[] uploadChunk) {
        configuration.getLogger().d(TAG, "printDebugCurrentChunk()");
        configuration.getLogger().d(TAG, "current thread: " + Thread.currentThread().getName());
        configuration.getLogger().d(TAG, "current chunk: " + chunkNumber);
        configuration.getLogger().d(TAG, "total chunks: " + numChunks);
        configuration.getLogger().d(TAG, "current chunk size: " + chunkSize);
        configuration.getLogger().d(TAG, "normal chunk size: " + unitSize);
        configuration.getLogger().d(TAG, "total file size: " + fileSize);
        configuration.getLogger().d(TAG, "current chunk hash: " + chunkHash);
        configuration.getLogger().d(TAG, "upload chunk ");
    }

    private boolean shouldCancelUpload(ResumableResponse response) {
        configuration.getLogger().d(TAG, "shouldCancelUpload()");
        // if API response code OR Upload Response Result code have an error then we need to terminate the process
        if (response.hasError()) {
            return true;
        }

        if (response.getDoUpload().getResultCode() != ResumableResponse.Result.NO_ERROR) {
            if (response.getDoUpload().getResultCode() != ResumableResponse.Result.SUCCESS_FILE_MOVED_TO_ROOT) {
                return true;
            }
        }

        return false;
    }

    private ResumableChunkInfo createResumableChunkInfo(int unitSize, int chunkNumber) throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "createResumableChunkInfo");
        ResumableChunkInfo resumableChunkInfo;
        // generate the chunk
        FileInputStream fis;
        BufferedInputStream bis;
        String chunkHash;
        byte[] uploadChunk;
        fis = new FileInputStream(uploadItem.getFileData().getFilePath());
        bis = new BufferedInputStream(fis);
        uploadChunk = createUploadChunk(unitSize, chunkNumber, bis);
        if (uploadChunk == null) {
            return null;
        }

        chunkHash = getSHA256(uploadChunk);
        resumableChunkInfo = new ResumableChunkInfo(chunkHash, uploadChunk);
        fis.close();
        bis.close();
        return resumableChunkInfo;
    }

    private Map<String, String> generateResumableRequestParameters() {
        configuration.getLogger().d(TAG, "generateResumableRequestParameters()");
        // get upload options. these will be passed as request parameters
        UploadItemOptions uploadItemOptions = uploadItem.getUploadOptions();
        String actionOnDuplicate = uploadItemOptions.getActionOnDuplicate();
        String versionControl = uploadItemOptions.getVersionControl();
        String uploadFolderKey = uploadItemOptions.getUploadFolderKey();
        String uploadPath = uploadItemOptions.getUploadPath();

        String actionToken = actionTokenManagerInterface.borrowUploadActionToken().getTokenString();
        LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
        parameters.put("session_token", actionToken);
        parameters.put("action_on_duplicate", actionOnDuplicate);
        parameters.put("response_format", "json");
        parameters.put("version_control", versionControl);
        if (uploadPath != null && !uploadPath.isEmpty()) {
            parameters.put("path", uploadPath);
        } else {
            parameters.put("folder_key", uploadFolderKey);
        }

        return parameters;
    }

    private Map<String, String> generatePostHeaders(String encodedShortFileName, long fileSize, int chunkNumber, String chunkHash, int chunkSize) {
        configuration.getLogger().d(TAG, "generatePostHeaders()");
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        // these headers are related to the entire file
        headers.put("x-filename", encodedShortFileName);
        headers.put("x-filesize", String.valueOf(fileSize));
        headers.put("x-filehash", uploadItem.getFileData().getFileHash());
        // these headers are related to the individual chunk
        headers.put("x-unit-id", Integer.toString(chunkNumber));
        headers.put("x-unit-hash", chunkHash);
        headers.put("x-unit-size", Integer.toString(chunkSize));
        return headers;
    }

    private boolean shouldSetPollUploadKey(ResumableResponse response) {
        configuration.getLogger().d(TAG, "shouldSetPollUploadKey()");
        switch (response.getDoUpload().getResultCode()) {
            case NO_ERROR:
            case SUCCESS_FILE_MOVED_TO_ROOT:
                return true;
            default:
                return false;
        }
    }

    private int getChunkSize(int chunkNumber, int numChunks, long fileSize, int unitSize) {
        configuration.getLogger().d(TAG, "getChunkSize()");
        int chunkSize;
        if (chunkNumber >= numChunks) {
            chunkSize = 0; // represents bad size
        } else {
            if (fileSize % unitSize == 0) { // all units will be of unitSize
                chunkSize = unitSize;
            } else if (chunkNumber < numChunks - 1) { // this unit is of unitSize
                chunkSize = unitSize;
            } else { // this unit is "special" and is the modulo of fileSize and unitSize;
                chunkSize = (int) (fileSize % unitSize);
            }
        }

        return chunkSize;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private byte[] createUploadChunk(long unitSize, int chunkNumber, BufferedInputStream fileStream) throws IOException {
        configuration.getLogger().d(TAG, "createUploadChunk()");

        configuration.getLogger().i(TAG, "starting read using fileStream.read()");
//        byte[] readBytes = new byte[(int) unitSize];
        int offset = (int) (unitSize * chunkNumber);
        fileStream.skip(offset);

        ByteArrayOutputStream output = new ByteArrayOutputStream( (int) unitSize);
        int bufferSize = 65536;

        configuration.getLogger().i(TAG, "starting read using ByteArrayOutputStream with buffer size: " + bufferSize);

        byte[] buffer = new byte[bufferSize];
        int readSize;
        int t = 0;

        while ((readSize = fileStream.read(buffer)) > 0 && t <= unitSize) {
            if (!haveStoredCredentials()) {
                configuration.getLogger().d(TAG, "no credentials stored, task cancelling()");
                uploadItem.cancelUpload();
                return null;
            }

            if (uploadItem.isCancelled()) {
                configuration.getLogger().d(TAG, "upload was cancelled for " + uploadItem.getFileName());
                notifyUploadListenerCancelled();
                return null;
            }

            if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
                notifyUploadListenerCancelled();
                return null;
            }

            if (output.size() + readSize > unitSize) {
                int actualReadSize = (int) unitSize - output.size();
                output.write(buffer, 0, actualReadSize);
            } else {
                output.write(buffer, 0, readSize);
            }

            if (readSize > 0) {
                t += readSize;
            }
        }

        byte[] data = output.toByteArray();

        configuration.getLogger().i(TAG, "total bytes read: " + t);
        configuration.getLogger().i(TAG, "data size: " + data.length);
        configuration.getLogger().i(TAG, "expected size: " + unitSize);

//        configuration.getLogger().i(TAG, "data size matches readBytes size: " + (data.length == readBytes.length));

        return data;
    }

    private String getSHA256(byte[] chunkData) throws NoSuchAlgorithmException, IOException {
        configuration.getLogger().d(TAG, "getSHA256()");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //test code
        InputStream in = new ByteArrayInputStream(chunkData, 0, chunkData.length);
        byte[] bytes = new byte[8192];
        int byteCount;
        while ((byteCount = in.read(bytes)) > 0) {
            md.update(bytes, 0, byteCount);
        }
        byte[] hashBytes = md.digest();
        //test code
        //byte[] hashBytes = md.digest(chunkData); //original code

        return convertHashBytesToString(hashBytes);
    }

    private String convertHashBytesToString(byte[] hashBytes) {
        configuration.getLogger().d(TAG, "convertHashBytesToString()");
        StringBuilder sb = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String tempString = Integer.toHexString((hashByte & 0xFF) | 0x100).substring(1, 3);
            sb.append(tempString);
        }

        return sb.toString();
    }

    private Map<String, String> generateInstantUploadRequestParameters() {
        configuration.getLogger().d(TAG, "generateInstantUploadRequestParameters()");
        // generate map with request parameters
        Map<String, String> keyValue = new LinkedHashMap<String, String>();
        keyValue.put("filename", utf8EncodedFileName);
        keyValue.put("hash", uploadItem.getFileData().getFileHash());
        keyValue.put("size", Long.toString(uploadItem.getFileData().getFileSize()));
        keyValue.put("response_format", "json");
        if (uploadItem.getUploadOptions().getUploadPath() != null && !uploadItem.getUploadOptions().getUploadPath().isEmpty()) {
            keyValue.put("path", uploadItem.getUploadOptions().getUploadPath());
        } else {
            keyValue.put("folder_key", uploadItem.getUploadOptions().getUploadFolderKey());
        }

        keyValue.put("action_on_duplicate", uploadItem.getUploadOptions().getActionOnDuplicate());
        return keyValue;
    }

    private Map<String, String> generateCheckUploadRequestParameters() {
        configuration.getLogger().d(TAG, "generateCheckUploadRequestParameters()");
        // generate map with request parameters
        Map<String, String> keyValue = new LinkedHashMap<String, String>();
        keyValue.put("filename", utf8EncodedFileName);
        keyValue.put("hash", uploadItem.getFileData().getFileHash());
        keyValue.put("size", Long.toString(uploadItem.getFileData().getFileSize()));
        keyValue.put("resumable", uploadItem.getUploadOptions().getResumable());
        keyValue.put("response_format", "json");
        if (uploadItem.getUploadOptions().getUploadPath() != null && !uploadItem.getUploadOptions().getUploadPath().isEmpty()) {
            keyValue.put("path", uploadItem.getUploadOptions().getUploadPath());
        } else {
            keyValue.put("folder_key", uploadItem.getUploadOptions().getUploadFolderKey());
        }
        return keyValue;
    }

    private boolean haveStoredCredentials() {
        configuration.getLogger().d(TAG, "haveStoredCredentials()");
        return !mfCredentials.getCredentials().isEmpty();
    }

    private void startOrRestartUpload() throws IOException, NoSuchAlgorithmException {
        configuration.getLogger().d(TAG, "startOrRestartUpload()");
        int uploadAttemptCount = uploadItem.getUploadAttemptCount();
        // don't start upload if cancelled or attempts > maxUploadAttempts
        if (uploadAttemptCount > maxUploadAttempts || uploadItem.isCancelled()) {
            if (uploadAttemptCount > maxUploadAttempts) {
                configuration.getLogger().d(TAG, "upload attempt count > maxUploadAttempts");
            }

            if (uploadItem.isCancelled()) {
                configuration.getLogger().d(TAG, "upload item was cancelled");
            }

            notifyUploadListenerCancelled();
            return;
        }
        //don't add the item to the backlog queue if it is null or the path is null
        if (uploadItem == null) {
            configuration.getLogger().d(TAG, "upload item is null");
            notifyUploadListenerCancelled();
            return;
        }

        configuration.getLogger().d(TAG, "getFileData() path: " + uploadItem.getFileData().getFilePath());
        configuration.getLogger().d(TAG, "getFileData() path: " + uploadItem.getFileData().getFilePath());
        configuration.getLogger().d(TAG, "getFileData() null: " + (uploadItem.getFileData() == null));
        configuration.getLogger().d(TAG, "getFileData().getFilePath() null: " + (uploadItem.getFileData().getFilePath() == null ));
        configuration.getLogger().d(TAG, "getFileData().getFilePath().isEmpty(): " + (uploadItem.getFileData().getFilePath().isEmpty()));
        configuration.getLogger().d(TAG, "getFileData().getFileHash().isEmpty(): " + (uploadItem.getFileData().getFileHash().isEmpty()));
        configuration.getLogger().d(TAG, "getFileData().getFileSize() == 0: " + (uploadItem.getFileData().getFileSize() == 0));
        if (uploadItem.getFileData() == null || uploadItem.getFileData().getFilePath() == null || uploadItem.getFileData().getFilePath().isEmpty() || uploadItem.getFileData().getFileHash().isEmpty() || uploadItem.getFileData().getFileSize() == 0) {
            configuration.getLogger().d(TAG, "one or more required parameters are invalid, not adding item to queue");
            notifyUploadListenerCancelled();
            return;
        }

        if (uploadAttemptCount <= maxUploadAttempts) {
            doCheckUpload();
        } else {
            configuration.getLogger().d(TAG, "upload attempt count > maxUploadAttempts");
            notifyUploadListenerCancelled();
        }
    }

    private void notifyUploadListenerStarted() {
        configuration.getLogger().d(TAG, "notifyUploadListenerStarted()");
        if (mfUploadListener != null) {
            mfUploadListener.onStarted(uploadItem);
        }
    }

    private void notifyUploadListenerCompleted() {
        configuration.getLogger().d(TAG, "notifyUploadListenerCompleted()");
        if (mfUploadListener != null) {
            mfUploadListener.onCompleted(uploadItem);
        }
    }

    private void notifyUploadListenerOnProgressUpdate(int totalChunks) {
        configuration.getLogger().d(TAG, "notifyUploadListenerOnProgressUpdate()");
        if (mfUploadListener != null) {
            // give number of chunks/numChunks for onProgressUpdate
            int numUploaded = 0;
            for (int chunkNumber = 0; chunkNumber < totalChunks; chunkNumber++) {
                if (uploadItem.getBitmap().isUploaded(chunkNumber)) {
                    numUploaded++;
                }
            }
            configuration.getLogger().d(TAG, numUploaded + "/" + totalChunks + " chunks uploaded");
            mfUploadListener.onProgressUpdate(uploadItem, numUploaded, totalChunks);
        }
    }

    private void notifyUploadListenerOnPolling(String message) {
        configuration.getLogger().d(TAG, "notifyUploadListenerOnPolling()");
        if (mfUploadListener != null) {
            mfUploadListener.onPolling(uploadItem, message);
        }
    }

    private void notifyUploadListenerCancelled() {
        configuration.getLogger().d(TAG, "notifyUploadListenerCancelled()");
        uploadItem.cancelUpload();
        if (mfUploadListener != null) {
            mfUploadListener.onCancelled(uploadItem);
        }
    }

    public static class Builder {
        private static final int DEFAULT_MAX_POLLS = 60;
        private static final int DEFAULT_MILLISECONDS_BETWEEN_POLLS = 2000;
        private static final int DEFAULT_MAX_UPLOAD_ATTEMPTS = 3;

        private int maxPolls = DEFAULT_MAX_POLLS;
        private long millisecondsBetweenPolls = DEFAULT_MILLISECONDS_BETWEEN_POLLS;
        private int maxUploadAttempts = DEFAULT_MAX_UPLOAD_ATTEMPTS;
        private final Configuration configuration;
        private final UploadItem uploadItem;
        private UploadListenerInterface mfUploadListener;

        /**
         * Constructor used to create an UploadRunnable.
         * @param configuration - an MFDefaultTokenFarm to use.
         * @param uploadItem - an UploadItem to use.
         */
        public Builder(Configuration configuration, UploadItem uploadItem) {
            if (configuration == null) {
                throw new IllegalArgumentException("MFDefaultTokenFarm cannot be null");
            }

            if (uploadItem == null) {
                throw new IllegalArgumentException("UploadItem cannot be null");
            }

            this.configuration = configuration;
            this.uploadItem = uploadItem;
        }

        /**
         * sets the max poll attempts for the upload.
         * @param maxPolls the max poll attempts.
         * @return a static UploadRunnable.Builder object to allow chaining calls.
         */
        public Builder maxPolls(int maxPolls) {
            if (maxPolls < 1) {
                throw new IllegalArgumentException("max polls cannot be less than 0");
            }
            this.maxPolls = maxPolls;
            return this;
        }

        /**
         * sets milliseconds between poll calls.
         * @param millisecondsBetweenPolls milliseconds between polls.
         * @return a static UploadRunnable.Builder object to allow chaining calls.
         */
        public Builder millisecondsBetweenPolls(long millisecondsBetweenPolls) {
            if (millisecondsBetweenPolls < 0) {
                throw new IllegalArgumentException("time between polls cannot be less than 0");
            }
            this.millisecondsBetweenPolls = millisecondsBetweenPolls;
            return this;
        }

        /**
         * sets the max attempts to try to upload the file.
         * @param maxUploadAttempts the max upload attempts.
         * @return a static UploadRunnable.Builder object to allow chaining calls.
         */
        public Builder maxUploadAttempts(int maxUploadAttempts) {
            if (maxUploadAttempts < 1) {
                throw new IllegalArgumentException("max upload attempts cannot be less than 1");
            }
            this.maxUploadAttempts = maxUploadAttempts;
            return this;
        }

        /**
         * sets the UploadListenerInterface used for callbacks.
         * @param mfUploadListener the UploadListenerInterface implementation.
         * @return a static UploadRunnable.Builder object to allow chaining calls.
         */
        public Builder uploadListener(UploadListenerInterface mfUploadListener) {
            this.mfUploadListener = mfUploadListener;
            return this;
        }

        /**
         * constructs a new UploadRunnable.
         * @return a new UploadRunnable.
         */
        public UploadRunnable build() {
            return new UploadRunnable(this);
        }
    }

    public <ResponseClass extends ApiResponse> ResponseClass getResponseObject(String responseString, Class<ResponseClass> responseClass) {
        if (responseString == null) {
            return null;
        }
        return new Gson().fromJson(getResponseStringForGson(responseString), responseClass);
    }

    private String getResponseStringForGson(String response) {
        if (response == null || response.isEmpty()) {
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        if (element.isJsonObject()) {
            JsonObject jsonResponse = element.getAsJsonObject().get("response").getAsJsonObject();
            return jsonResponse.toString();
        } else {
            return null;
        }
    }
}
