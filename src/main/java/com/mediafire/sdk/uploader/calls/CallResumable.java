package com.mediafire.sdk.uploader.calls;

import com.mediafire.sdk.api.clients.upload.HeaderParameters;
import com.mediafire.sdk.api.clients.upload.ResumableParameters;
import com.mediafire.sdk.api.clients.upload.UploadClient;
import com.mediafire.sdk.api.responses.upload.ResumableResponse;
import com.mediafire.sdk.api.responses.upload.ResumableResult;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.uploader.ResumableChunkInfo;
import com.mediafire.sdk.uploader.upload_items.Options;
import com.mediafire.sdk.uploader.upload_items.ResumableUpload;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Chris on 11/13/2014.
 */
public class CallResumable extends BaseCall {

    private final UploadClient mUploadClient;
    private final ResumableUpload mUpload;
    private String pollUploadKey;

    public CallResumable(UploadClient uploadClient, ResumableUpload upload) {
        mUploadClient = uploadClient;
        mUpload = upload;
    }

    public void resumable() throws IOException, NoSuchAlgorithmException {
        // TODO handle completed upload

        for (int chunkNumber = 0; chunkNumber < mUpload.getNumUnits(); chunkNumber++) {
            // if the bitmap says this chunk number is uploaded then we can just skip it, if not, we upload it.
            if (!mUpload.getResumableBitmap().isUploaded(chunkNumber)) {
                // get the chunk size for this chunk
                int chunkSize = getChunkSize(chunkNumber, mUpload.getNumUnits(), mUpload.getFilesize(), mUpload.getUnitSize());

                ResumableChunkInfo resumableChunkInfo = createResumableChunkInfo(mUpload.getUnitSize(), chunkNumber);
                if (resumableChunkInfo == null) {
                    return;
                }

                String chunkHash = resumableChunkInfo.getChunkHash();
                byte[] uploadChunk = resumableChunkInfo.getUploadChunk();

                Options options = mUpload.getOptions();

                ResumableParameters.Builder resumableParamsBuilder = new ResumableParameters.Builder();
                resumableParamsBuilder.versionControl(options.getVersionControl());
                resumableParamsBuilder.quickKey(options.getQuickKey());
                resumableParamsBuilder.previousHash(options.getPreviousHash());
                resumableParamsBuilder.mTime(options.getModificationTime());
                resumableParamsBuilder.actionOnDuplicate(options.getActionOnDuplicate());
                resumableParamsBuilder.filedropKey(options.getFiledropKey());
                resumableParamsBuilder.folderKey(options.getUploadFolderKey());
                resumableParamsBuilder.path(options.getUploadPath());
                resumableParamsBuilder.sourceHash(options.getSourceHash());
                resumableParamsBuilder.targetHash(options.getTargetHash());
                resumableParamsBuilder.targetSize(options.getTargetSize());

                ResumableParameters resumableParams = resumableParamsBuilder.build();

                HeaderParameters headerParams = new HeaderParameters(mUpload.getFilesize(), mUpload.getFilehash(), chunkSize, chunkNumber, chunkHash);
                Result result = mUploadClient.resumable(resumableParams, headerParams, uploadChunk);

                if (CallHelper.resultInvalid(result)) {
                    // TODO handle
                    return;
                }

                Response response = result.getResponse();

                ResumableResponse resumableResponse = CallHelper.getResponseObject(response.getBytes(), ResumableResponse.class);

                if (resumableResponse.hasError()) {
                    // TODO handle
                    return;
                }

                if (resumableResponse.getDoUpload().getResultCode() != ResumableResult.NO_ERROR) {
                    if (resumableResponse.getDoUpload().getResultCode() != ResumableResult.SUCCESS_FILE_MOVED_TO_ROOT) {
                        // TODO handle
                        return;
                    }
                }

                if (resumableResponse.getDoUpload().getPollUploadKey() != null && pollUploadKey == null) {
                    pollUploadKey = resumableResponse.getDoUpload().getPollUploadKey();
                }

                // update the response bitmap
                int count = resumableResponse.getResumableUpload().getBitmap().getCount();
                List<Integer> words = resumableResponse.getResumableUpload().getBitmap().getWords();
                mUpload.getResumableBitmap().update(count, words);

                chunkHash = null;
                uploadChunk = null;
            }

            // notify a % has been completed
            // TODO handle
        } // end loop

        // let the listeners know that upload has attempted to upload all chunks.
        // TODO handle
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

    private ResumableChunkInfo createResumableChunkInfo(int unitSize, int chunkNumber) throws IOException, NoSuchAlgorithmException {
        ResumableChunkInfo resumableChunkInfo;
        // generate the chunk
        FileInputStream fis;
        BufferedInputStream bis;
        String chunkHash;
        byte[] uploadChunk;
        fis = new FileInputStream(mUpload.getFile());
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private byte[] createUploadChunk(long unitSize, int chunkNumber, BufferedInputStream fileStream) throws IOException {
//        byte[] readBytes = new byte[(int) unitSize];
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

    private String getSHA256(byte[] chunkData) throws NoSuchAlgorithmException, IOException {
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
        StringBuilder sb = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String tempString = Integer.toHexString((hashByte & 0xFF) | 0x100).substring(1, 3);
            sb.append(tempString);
        }

        return sb.toString();
    }
}
