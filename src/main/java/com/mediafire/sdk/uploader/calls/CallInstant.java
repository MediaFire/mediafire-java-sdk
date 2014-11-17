package com.mediafire.sdk.uploader.calls;

import com.mediafire.sdk.api.clients.upload.InstantParameters;
import com.mediafire.sdk.api.clients.upload.UploadClient;
import com.mediafire.sdk.api.responses.ResponseCode;
import com.mediafire.sdk.api.responses.upload.InstantResponse;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.uploader.upload_items.Options;
import com.mediafire.sdk.uploader.upload_items.Upload;

/**
 * Created by Chris on 11/13/2014.
 */
public class CallInstant extends BaseCall {

    private final UploadClient mUploadClient;
    private final Upload mUpload;

    public CallInstant(UploadClient uploadClient, Upload upload) {
        mUploadClient = uploadClient;
        mUpload = upload;
    }

    public void instant() {
        String hash = mUpload.getFilehash();
        long size = mUpload.getFilesize();

        Options options = mUpload.getOptions();

        InstantParameters.Builder builder = new InstantParameters.Builder(hash, size);
        builder.path(options.getUploadPath());
        builder.filedropKey(options.getFiledropKey());
        builder.folderKey(options.getUploadFolderKey());
        builder.actionOnDuplicate(options.getActionOnDuplicate());
        builder.filename(options.getCustomFileName() == null ? mUpload.getFilename() : options.getCustomFileName());
        builder.mTime(options.getModificationTime());
        builder.previousHash(options.getPreviousHash());
        builder.quickKey(options.getQuickKey());
        builder.versionControl(options.getVersionControl());

        InstantParameters instantParameters = builder.build();
        Result result = mUploadClient.instant(instantParameters);

        if (CallHelper.resultInvalid(result)) {
            // TODO handle
            return;
        }

        Response response = result.getResponse();

        byte[] responseBytes = response.getBytes();

        InstantResponse instantResponse = CallHelper.getResponseObject(responseBytes, InstantResponse.class);


        if (instantResponse == null) {
            // TODO handle
            return;
        }

        if (instantResponse.hasError()) {
            // TODO handle
            return;
        }

        // if there is an error code, cancel the upload
        if (instantResponse.getErrorCode() != ResponseCode.NO_ERROR) {
            // TODO handle
            return;
        }

        if (instantResponse == null) {
            // TODO handle
            return;
        }

        if (instantResponse.getErrorCode() != ResponseCode.NO_ERROR) {
            // TODO handle
            return;
        }

        if (instantResponse.getQuickkey() == null || instantResponse.getQuickkey().isEmpty()) {
            // notify listeners that check has completed
            // TODO handle (return quick key)
            return;
        }

        // TODO handle completed upload
    }
}
