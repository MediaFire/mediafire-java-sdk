package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class UploadClient extends PathSpecificApiClient {
    private static final String PARAM_FILENAME = "filename";
    private static final String PARAM_HASH = "hash";
    private static final String PARAM_SIZE = "size";
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FILEDROP_KEY = "filedrop_key";
    private static final String PARAM_PATH = "path";
    private static final String PARAM_RESUMABLE = "resumable";
    private static final String PARAM_QUICK_KEY = "quick_key";
    private static final String PARAM_ACTION_ON_DUPLICATE = "action_on_duplicate";
    private static final String PARAM_MTIME = "mTime";
    private static final String PARAM_VERSION_CONTROL = "version_control";
    private static final String PARAM_PREVIOUS_HASH = "previous_hash";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_SOURCE_HASH = "source_hash";
    private static final String PARAM_TARGET_HASH = "target_hash";
    private static final String PARAM_TARGET_SIZE = "target_size";

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public UploadClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);
    }

    public Result check(CheckParameters checkParameters) {
        ApiObject apiObject = new ApiObject("upload", "check.php");

        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FILENAME, checkParameters.mFilename);
        request.addQueryParameter(PARAM_HASH, checkParameters.mHash);
        request.addQueryParameter(PARAM_SIZE, checkParameters.mSize);
        request.addQueryParameter(PARAM_FOLDER_KEY, checkParameters.mFolderKey);
        request.addQueryParameter(PARAM_FILEDROP_KEY, checkParameters.mFiledropKey);
        request.addQueryParameter(PARAM_PATH, checkParameters.mPath);
        request.addQueryParameter(PARAM_RESUMABLE, checkParameters.mResumable);

        return doRequestJson(request);
    }

    public Result instant(InstantParameters instantParameters) {
        ApiObject apiObject = new ApiObject("upload", "instant.php");

        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_HASH, instantParameters.mHash);
        request.addQueryParameter(PARAM_SIZE, instantParameters.mSize);
        request.addQueryParameter(PARAM_FILENAME, instantParameters.mFilename);
        request.addQueryParameter(PARAM_QUICK_KEY, instantParameters.mQuickKey);
        request.addQueryParameter(PARAM_FOLDER_KEY, instantParameters.mFolderKey);
        request.addQueryParameter(PARAM_FILEDROP_KEY, instantParameters.mFiledropKey);
        request.addQueryParameter(PARAM_PATH, instantParameters.mPath);
        request.addQueryParameter(PARAM_ACTION_ON_DUPLICATE, instantParameters.mActionOnDuplicate);
        request.addQueryParameter(PARAM_MTIME, instantParameters.mMTime);
        request.addQueryParameter(PARAM_VERSION_CONTROL, instantParameters.mVersionControl);
        request.addQueryParameter(PARAM_PREVIOUS_HASH, instantParameters.mPreviousHash);

        return doRequestJson(request);
    }

    public Result pollUpload(String key) {
        ApiObject apiObject = new ApiObject("upload", "poll_upload.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);

        Request request = new Request(mHost, apiObject, instructionsObject, mVersionObject);

        request.addQueryParameter(PARAM_KEY, key);

        return doRequestJson(request);
    }

    public Result resumable(ResumableParameters resumableParameters, String encodedShortFileName, long fileSize, String fileHash, int chunkNumber, String chunkHash, int chunkSize, byte[] payload) {
        ApiObject apiObject = new ApiObject("upload", "resumable.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, false);

        Request request = new Request(mHost, apiObject, instructionsObject, mVersionObject);

        request.addQueryParameter(PARAM_FILEDROP_KEY, resumableParameters.mFiledropKey);
        request.addQueryParameter(PARAM_SOURCE_HASH, resumableParameters.mSourceHash);
        request.addQueryParameter(PARAM_TARGET_HASH, resumableParameters.mTargetHash);
        request.addQueryParameter(PARAM_TARGET_SIZE, resumableParameters.mTargetSize);
        request.addQueryParameter(PARAM_QUICK_KEY, resumableParameters.mQuickKey);
        request.addQueryParameter(PARAM_FOLDER_KEY, resumableParameters.mFolderKey);
        request.addQueryParameter(PARAM_PATH, resumableParameters.mPath);
        request.addQueryParameter(PARAM_ACTION_ON_DUPLICATE, resumableParameters.mActionOnDuplicate);
        request.addQueryParameter(PARAM_MTIME, resumableParameters.mMTime);
        request.addQueryParameter(PARAM_VERSION_CONTROL, resumableParameters.mVersionControl);
        request.addQueryParameter(PARAM_PREVIOUS_HASH, resumableParameters.mPreviousHash);

        request.addPayload(payload);

        request.addHeader("x-filename", encodedShortFileName);
        request.addHeader("x-filesize", String.valueOf(fileSize));
        request.addHeader("x-filehash", fileHash);
        request.addHeader("x-unit-id", Integer.toString(chunkNumber));
        request.addHeader("x-unit-hash", chunkHash);
        request.addHeader("x-unit-size", Integer.toString(chunkSize));

        return doRequestJson(request);
    }
}
