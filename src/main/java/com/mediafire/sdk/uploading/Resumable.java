package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.ResumableResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Resumable extends UploadRunnable {

    private ResumableUpload mUpload;
    private UploadProcess mProcessMonitor;

    public Resumable(ResumableUpload upload, HttpHandler http, TokenManager tokenManager, UploadProcess processMonitor) {
        super(http, tokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    @Override
    public void run() {
        Map<String, Object> requestParams = makeQueryParams();

        String customFileName = mUpload.getOptions().getCustomFileName();

        String filename = customFileName != null ? customFileName : mUpload.getFile().getName();

        long fileSize = mUpload.getFile().length();

        int numUnits = mUpload.getNumberOfUnits();
        int unitSize = mUpload.getUnitSize();

        String responsePollKey = null;
        boolean allUnitsReady = false;

        for (int chunkNumber = 0; chunkNumber < numUnits; chunkNumber++) {
            if (!mUpload.isChunkUploaded(chunkNumber)) {
                int chunkSize = getChunkSize(chunkNumber, numUnits, fileSize, unitSize);

                byte[] chunk;
                String chunkHash;

                try {
                    chunk = makeChunk(unitSize, chunkNumber);
                    chunkHash = Hasher.getSHA256Hash(chunk);
                } catch (IOException exception) {
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                } catch (NoSuchAlgorithmException exception) {
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                }

                Map<String, Object> headerParams = makeHeaderParams(chunkNumber, chunkSize, chunkHash, filename);

                Result result = getUploadClient().resumable(requestParams, headerParams, chunk);

                if (!resultValid(result)) {
                    mProcessMonitor.generalCancel(mUpload, result);
                    return;
                }

                byte[] responseBytes = result.getResponse().getBytes();
                String fullResponse = new String(responseBytes);
                String response = getResponseStringForGson(fullResponse);

                ResumableResponse apiResponse;
                try {
                    apiResponse = new Gson().fromJson(response, ResumableResponse.class);
                } catch (JsonSyntaxException exception) {
                    mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                    return;
                }

                if (apiResponse == null) {
                    mProcessMonitor.generalCancel(mUpload, result);
                    return;
                }

                if (apiResponse.hasError()) {
                    mProcessMonitor.apiError(mUpload, result);
                    return;
                }

                if (responsePollKey == null) {
                    String pollKey = apiResponse.getDoUpload().getPollUploadKey();
                    if (pollKey != null) {
                        responsePollKey = pollKey;
                    }
                }

                allUnitsReady = apiResponse.getResumableUpload().areAllUnitsReady();

                int newCount = apiResponse.getResumableUpload().getBitmap().getCount();
                List<Integer> newWords = apiResponse.getResumableUpload().getBitmap().getWords();
                mUpload.updateUploadBitmap(newCount, newWords);
            }

            int numUploaded = 0;
            for (int chunkCount = 0; chunkCount < numUnits; chunkCount++) {
                if (mUpload.isChunkUploaded(chunkCount)) {
                    numUploaded++;
                }
            }

            double percentFinished = (double) numUploaded / (double) numUnits;
            percentFinished *= 100;
            mProcessMonitor.resumableProgress(mUpload, percentFinished);
        }

        mProcessMonitor.resumableFinished(mUpload, responsePollKey, allUnitsReady);
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

    static class ResumableUpload extends Instant.InstantUpload {

        private int mNumUnits;
        private int mUnitSize;
        private List<Boolean> mUploadUnits;

        public ResumableUpload(Upload upload, String hash, int numUnits, int unitSize, int count, List<Integer> words) {
            super(upload, hash);
            mNumUnits = numUnits;
            mUnitSize = unitSize;
            mUploadUnits = decodeBitmap(count, words);
        }

        private List<Boolean> decodeBitmap(int count, List<Integer> words) {
            List<Boolean> uploadUnits = new LinkedList<Boolean>();

            if (words == null || words.isEmpty()) {
                return uploadUnits;
            }

            //loop count times
            for (int i = 0; i < count; i++) {
                //convert words to binary string
                String word = Integer.toBinaryString(words.get(i));

                //ensure number is 16 bit by adding 0 until there are 16 bits
                while (word.length() < 16) {
                    word = "0" + word;
                }

                //add boolean to collection depending on bit value
                for (int j = 0; j < word.length(); j++) {
                    uploadUnits.add(i * 16 + j, word.charAt(15 - j) == '1');
                }
            }

            return uploadUnits;
        }

        public boolean isChunkUploaded(int chunkId) {
            if (mUploadUnits.isEmpty()) {
                return false;
            }
            return mUploadUnits.get(chunkId);
        }

        public int getNumberOfUnits() {
            return mNumUnits;
        }

        public int getUnitSize() {
            return mUnitSize;
        }

        public void updateUploadBitmap(int newCount, List<Integer> newWords) {
            mUploadUnits = decodeBitmap(newCount, newWords);
        }
    }

}
