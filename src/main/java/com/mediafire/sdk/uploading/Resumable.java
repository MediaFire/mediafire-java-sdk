package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.ResumableDoUpload;
import com.mediafire.sdk.api.responses.upload.ResumableBitmap;
import com.mediafire.sdk.api.responses.upload.ResumableResponse;
import com.mediafire.sdk.api.responses.upload.ResumableUpload;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Resumable extends UploadRunnable {

    private Upload mUpload;
    private UploadProcess mProcessMonitor;

    public Resumable(Upload upload, HttpHandler http, TokenManager tokenManager, UploadProcess processMonitor) {
        super(http, tokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    @Override
    public void run() {
        if (isDebugging()) {
            System.out.println("upload/resumable has started for " + mUpload);
        }
        Map<String, Object> requestParams = makeQueryParams();

        String customFileName = mUpload.getOptions().getCustomFileName();

        String filename = customFileName != null ? customFileName : mUpload.getFile().getName();

        long fileSize = mUpload.getFile().length();

        int numUnits = mUpload.getNumberOfUnits();
        int unitSize = mUpload.getUnitSize();

        ResumableResponse apiResponse = null;
        for (int chunkNumber = 0; chunkNumber < numUnits; chunkNumber++) {
            if (!mUpload.isChunkUploaded(chunkNumber)) {
                if (isDebugging()) {
                    System.out.println("upload/resumable has begun uploading chunk #" + chunkNumber + " for " + mUpload);
                }
                int chunkSize = getChunkSize(chunkNumber, numUnits, fileSize, unitSize);

                byte[] chunk;
                String chunkHash;

                try {
                    chunk = makeChunk(unitSize, chunkNumber);
                    chunkHash = Hasher.getSHA256Hash(chunk);
                } catch (IOException exception) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to an exception: " + exception);
                    }
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                } catch (NoSuchAlgorithmException exception) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to an exception: " + exception);
                    }
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                }

                Map<String, Object> headerParams = makeHeaderParams(chunkNumber, chunkSize, chunkHash, filename);

                Result result = getUploadClient().resumable(requestParams, headerParams, chunk);

                if (!resultValid(result)) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to invalid result object");
                    }
                    mProcessMonitor.generalCancel(mUpload, result);
                    return;
                }

                if (isDebugging()) {
                    System.out.println("upload/resumable request: " + result.getRequest());
                    System.out.println("upload/resumable response: " + result.getResponse());
                }

                byte[] responseBytes = result.getResponse().getBytes();
                String fullResponse = new String(responseBytes);
                String response = getResponseStringForGson(fullResponse);

                try {
                    apiResponse = new Gson().fromJson(response, ResumableResponse.class);
                } catch (JsonSyntaxException exception) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to an exception: " + exception);
                    }
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                }

                if (apiResponse == null) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to a null ApiResponse object");
                    }
                    mProcessMonitor.generalCancel(mUpload, result);
                    return;
                }

                if (apiResponse.hasError()) {
                    if (isDebugging()) {
                        System.out.println("cancelling upload/resumable while uploaded chunk #" + chunkNumber + " for " + mUpload + " due to an ApiResponse error (" + apiResponse.getMessage() + ", error " + apiResponse.getError() + ")");
                    }
                    mProcessMonitor.apiError(mUpload, result);
                    return;
                }

                ResumableUpload resumableUpload = apiResponse.getResumableUpload();
                if (resumableUpload != null) {
                    String allUnitsReady = resumableUpload.getAllUnitsReady();
                    int numberOfUnits = resumableUpload.getNumberOfUnits();
                    int size = resumableUpload.getUnitSize();

                    mUpload.setAllUnitsReady(allUnitsReady != null && "yes".equals(allUnitsReady));
                    mUpload.setNumberOfUnits(numberOfUnits);
                    mUpload.setUnitSize(size);

                    ResumableBitmap bitmap = resumableUpload.getBitmap();
                    if (bitmap != null) {
                        int count = bitmap.getCount();
                        List<Integer> words = bitmap.getWords();

                        mUpload.updateUploadBitmap(count, words);
                    }
                }

                ResumableDoUpload resumableDoUpload = apiResponse.getDoUpload();
                if (resumableDoUpload != null) {
                    String key = resumableDoUpload.getKey();
                    mUpload.setPollKey(key);
                }
            } else {
                if (isDebugging()) {
                    System.out.println("upload/resumable has already uploaded chunk #" + chunkNumber + " for " + mUpload);
                }
            }

            int numUploaded = 0;
            for (int chunkCount = 0; chunkCount < numUnits; chunkCount++) {
                if (mUpload.isChunkUploaded(chunkCount)) {
                    numUploaded++;
                }
            }

            double percentFinished = (double) numUploaded / (double) numUnits;
            percentFinished *= 100;

            if (isDebugging()) {
                System.out.println("upload/resumable is updating upload progress for " + mUpload + " to " + percentFinished + "%");
            }
            mProcessMonitor.resumableProgress(mUpload, percentFinished);
        }
        if (isDebugging()) {
            System.out.println("upload/resumable has finished attempting to upload all chunks for " + mUpload);
        }
        mProcessMonitor.resumableFinished(mUpload);
    }

    @Override
    public boolean resultValid(Result result) {
        if (result == null){
            return false;
        }

        if (result.getResponse() == null) {
            return false;
        }

        if (result.getResponse().getBytes() == null) {
            return false;
        }

        if (result.getResponse().getHeaderFields() == null) {
            return false;
        }

        if (!result.getResponse().getHeaderFields().containsKey("Content-Type")) {
            return false;
        }

        List<String> contentTypeHeaders = result.getResponse().getHeaderFields().get("Content-Type");

        // TODO(cnajar) - once MFBU is fixed and returning the correct Content-Type in the response headers, this
        // TODO(cnajar) -          override method from UploadRunnable can be removed.
//        if (!contentTypeHeaders.contains("application/json")) {
//            return false;
//        }

        return true;
    }

    private byte[] makeChunk(int unitSize, int chunkNumber) throws IOException {
        // generate the chunk
        FileInputStream fis = new FileInputStream(mUpload.getFile());
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] uploadChunk = createUploadChunk(unitSize, chunkNumber, bis);
        fis.close();
        bis.close();
        return uploadChunk;
    }

    private int getChunkSize(int chunkNumber, int numChunks, long fileSize, int unitSize) {
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

    private byte[] createUploadChunk(long unitSize, int chunkNumber, BufferedInputStream fileStream) throws IOException {
        int offset = (int) (unitSize * chunkNumber);
        fileStream.skip(offset);

        ByteArrayOutputStream output = new ByteArrayOutputStream( (int) unitSize);
        int bufferSize = 65536;

        byte[] buffer = new byte[bufferSize];
        int readSize;
        int t = 0;

        while ((readSize = fileStream.read(buffer)) > 0 && t <= unitSize) {
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

        return data;
    }

    @Override
    protected Map<String, Object> makeQueryParams() {
        String folderKey = mUpload.getOptions().getUploadFolderKey();
        String uploadPath = mUpload.getOptions().getUploadPath();
        String actionOnDuplicate = "keep";

        String responseFormat = "json";

        Map<String, Object> requestParams = new LinkedHashMap<String, Object>();
        if (folderKey != null) {
            requestParams.put("folder_key", folderKey);
        }

        if (uploadPath != null) {
            requestParams.put("path", uploadPath);
        }

        requestParams.put("action_on_duplicate", actionOnDuplicate);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }

    private Map<String, Object> makeHeaderParams(int unitId, int chunkSize, String chunkHash, String filename) {
        long fileSize = mUpload.getFile().length();
        String fileHash = mUpload.getHash();

        Map<String, Object> headerParams = new LinkedHashMap<String, Object>();

        headerParams.put("x-filesize", fileSize);
        headerParams.put("x-filehash", fileHash);
        headerParams.put("x-unit-hash", chunkHash);
        headerParams.put("x-unit-id", unitId);
        headerParams.put("x-unit-size", chunkSize);
        headerParams.put("x-filename", filename);

        return headerParams;
    }

}
