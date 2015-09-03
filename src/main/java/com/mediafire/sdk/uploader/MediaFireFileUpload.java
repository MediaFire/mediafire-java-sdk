package com.mediafire.sdk.uploader;

import java.io.File;

public interface MediaFireFileUpload extends MediaFireUpload {
    /**
     * The SHA256 hash of the file being uploaded. If not passed, no content checks can be made. Required if isResumable() or isPreemptive()
     * @return
     */
    String getSha256Hash();

    /**
     * The size, in bytes, of the file being uploaded. Required if hash is passed or isResumable()
     * @return
     */
    long getFileSize();

    /**
     * A JSON formatted array of parameters for batch requests(see Batching below). Required if filename is not passed. Takes precedence over other parameters.
     * <p>
     *     [
     *          {
     *              "filename":"Microsoft-Windows-Common-Modem-Drivers-Package~31bf3856ad364e35~amd64~~6.1.7601.17514.mum",
     *              "hash":"5a6228aecc1eea6c3463f3fba1c3cdaca4b7b4d7c3d583c9955ebe7558fc298d",
     *              "size":"64690"
     *          },
     *          {
     *              "filename":"Win8IP-Microsoft-Windows-Multimedia-Package~31bf3856ad364e35~amd64~bg-BG~7.1.7601.16492.cat",
     *              "hash":"3ee9258151c834bba9d499dea3026122a10432e3dfe3a5d989d68978a85e3237",
     *              "size":"8718"
     *          }
     *     ]
     * </p>
     * @return
     */
    String getJsonFormattedUploadsArray();

    /**
     * The ID of the calling device. Required if preemptive=yes. Cannot be 0(cloud). 255 reserved for unpaired devices.
     * @return
     */
    String getDeviceId();

    /**
     * Specifies whether to generate a preemptive quickkey or not for use in a future upload. Preemptive keys are not created when an instant upload is available. false(default)
     * @return
     */
    boolean isPreemptive();

    /**
     * The destination filedrop folder key. Required if session_token is not passed. Ignored if session_token or folder_key is passed.
     * @return
     */
    String getFileDropKey();

    /**
     * The destination absolute or relative path. Absolute paths begin with a slash; relative paths do not and are in relation to the specified folder_key or filedrop_key. Non-existent folders will not be created by this call.
     * @return
     */
    String getMediaFirePath();

    /**
     * Specifies whether to make this upload resumable or not. true(default)
     * @return
     */
    boolean isResumable();

    /**
     * The File for this object.
     * @return
     */
    File getFile();

    ActionOnInAccount getActionOnInAccount();

    enum ActionOnInAccount {
        UPLOAD_IF_NOT_IN_FOLDER, DO_NOT_UPLOAD, UPLOAD_ALWAYS,
    }
}
