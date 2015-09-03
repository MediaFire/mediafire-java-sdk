package com.mediafire.sdk.uploader;

import java.util.Map;

/**
 * Created by christophernajar on 9/2/15.
 */
public interface MediaFireUploadStore {
    // result info
    String QUICK_KEY = "quick_key";
    String FILE_NAME = "file_name";

    // status codes
    String RESULT_CODE = "result_code";
    String FILE_ERROR_CODE = "file_error_code";
    String STATUS_CODE = "status_code";
    String ERROR_CODE = "error_code";
    String POLL_STATUS_DESCRIPTION = "status_description";

    // exceptions
    String EXCEPTION = "exception";
    String EXCEPTION_MESSAGE = "exception_message";
    String EXCEPTION_LOCALIZED_MESSAGE = "exception_localized_message";

    // upload info
    String UPLOAD_POLL_KEY = "upload_poll_key";
    String PERCENT_UPLOADED = "percent_uploaded";

    // status'
    String UPLOAD_STATUS = "upload_status";
    String WEB_UPLOAD_POLLING = "";
    Object WEB_UPLOAD_FINISHED = "";
    Object WEB_UPLOAD_ERROR = "";
    Object WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN = "";
    Object WEB_UPLOAD_THREAD_INTERRUPTED = "";
    Object WEB_UPLOAD_READY_TO_POLL = "";
    Object FILE_UPLOAD_CHECK_UPLOAD_FINISHED = "";
    Object FILE_UPLOAD_CHECK_UPLOAD_MEDIAFIRE_EXCEPTION = "";
    Object FILE_UPLOAD_INSTANT_UPLOAD_MEDIAFIRE_EXCEPTION = "";
    Object FILE_UPLOAD_FINISHED = "";
    Object FILE_UPLOAD_RESUMABLE_UPLOADING = "";
    Object FILE_UPLOAD_MEDIAFIRE_EXCEPTION = "";
    Object FILE_UPLOAD_RESUMABLE_FINISHED_WITHOUT_ALL_UNITS_READY = "";
    Object FILE_UPLOAD_IO_EXCEPTION = "";
    Object FILE_UPLOAD_POLLING = "";
    Object FILE_UPLOAD_ERROR = "";
    Object FILE_UPLOAD_RESULT_CODE_ERROR = "";
    Object FILE_UPLOAD_THREAD_INTERRUPTED = "";
    Object FILE_UPLOAD_READY_TO_POLL = "";
    Object GET_WEB_UPLOAD_FINISHED_WITHOUT_VERIFICATION_OF_COMPLETED_UPLOADS = "";
    Object WEB_UPLOAD_NEW = "";
    Object FILE_UPLOAD_NEW = "";
    Object FILE_UPLOAD_RESUMABLE_STARTING = "";
    Object FILE_UPLOAD_INSTANT_STARTING = "";

    void insert(MediaFireWebUpload upload);

    void insert(MediaFireFileUpload upload);

    void update(MediaFireFileUpload mediaFireUpload, Map<String, Object> valuesMap);

    void update(MediaFireWebUpload mediaFireUpload, Map<String, Object> valuesMap);
}
