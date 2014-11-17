package com.mediafire.sdk.uploader.calls;

import com.mediafire.sdk.api.clients.upload.CheckParameters;
import com.mediafire.sdk.api.clients.upload.UploadClient;
import com.mediafire.sdk.api.responses.ResponseCode;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.uploader.upload_items.ResumableUpload;
import com.mediafire.sdk.uploader.upload_items.Upload;
import com.mediafire.sdk.uploader.uploaditem.ResumableBitmap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Chris on 11/13/2014.
 */
public class CallCheck extends BaseCall {

    private UploadClient mUploadClient;
    private Upload mUpload;

    public CallCheck(UploadClient uploadClient, Upload upload) {
        mUploadClient = uploadClient;
        mUpload = upload;
    }

    public void check() throws IOException, NoSuchAlgorithmException {
        CheckParameters.Builder builder = new CheckParameters.Builder(mUpload.getFilename());
        builder.hash(mUpload.getFilehash());
        builder.size(mUpload.getFilesize());
        builder.folderKey(mUpload.getOptions().getUploadFolderKey());
        builder.filedropKey(mUpload.getOptions().getFiledropKey());
        builder.path(mUpload.getOptions().getUploadPath());
        builder.resumable(true);

        CheckParameters checkParameters = builder.build();
        Result result = mUploadClient.check(checkParameters);

        if (CallHelper.resultInvalid(result)) {
            // TODO handle
            return;
        }

        Response response = result.getResponse();

        byte[] responseBytes = response.getBytes();

        CheckResponse checkResponse = CallHelper.getResponseObject(responseBytes, CheckResponse.class);

        if (checkResponse == null) {
            // TODO handle
            return;
        }

        if (checkResponse.hasError()) {
            // TODO handle
            return;
        }

        // if there is an error code, cancel the upload
        if (checkResponse.getErrorCode() != ResponseCode.NO_ERROR) {
            // TODO handle
            return;
        }

        // TODO build resumable upload object -OR- create instant upload object
        int numUnits = checkResponse.getResumableUpload().getNumberOfUnits();
        int unitSize = checkResponse.getResumableUpload().getUnitSize();

        int count = checkResponse.getResumableUpload().getBitmap().getCount();
        List<Integer> words = checkResponse.getResumableUpload().getBitmap().getWords();
        ResumableBitmap bitmap = new ResumableBitmap(count, words);

        ResumableUpload resumableUpload = new ResumableUpload(mUpload, numUnits, unitSize, bitmap);
        // TODO handle
    }
}
