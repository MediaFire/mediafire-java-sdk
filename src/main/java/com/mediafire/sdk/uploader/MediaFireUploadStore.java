package com.mediafire.sdk.uploader;

import java.util.Map;

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

    // api error
    String API_ERROR_NUMBER = "api_error_number";
    String API_ERROR_MESSAGE = "api_error_message";

    void insert(MediaFireWebUpload upload);

    void insert(MediaFireFileUpload upload);

    void update(MediaFireFileUpload mediaFireUpload, Map<String, Object> valuesMap);

    void update(MediaFireWebUpload mediaFireUpload, Map<String, Object> valuesMap);

    MediaFireUpload getNextUpload();
    
    enum MediaFireUploadStatus {
        WEB_UPLOAD_POLLING,
        WEB_UPLOAD_FINISHED,
        WEB_UPLOAD_ERROR,
        WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN,
        WEB_UPLOAD_THREAD_INTERRUPTED,
        WEB_UPLOAD_READY_TO_POLL,
        WEB_UPLOAD_NEW,
        WEB_UPLOAD_FINISHED_WITHOUT_VERIFICATION_OF_COMPLETED_UPLOADS,
        FILE_UPLOAD_CHECK_UPLOAD_FINISHED,
        FILE_UPLOAD_CHECK_UPLOAD_MEDIAFIRE_EXCEPTION,
        FILE_UPLOAD_INSTANT_UPLOAD_MEDIAFIRE_EXCEPTION,
        FILE_UPLOAD_FINISHED,
        FILE_UPLOAD_RESUMABLE_UPLOADING,
        FILE_UPLOAD_MEDIAFIRE_EXCEPTION,
        FILE_UPLOAD_RESUMABLE_FINISHED_WITHOUT_ALL_UNITS_READY,
        FILE_UPLOAD_IO_EXCEPTION,
        FILE_UPLOAD_POLLING,
        FILE_UPLOAD_ERROR,
        FILE_UPLOAD_RESULT_CODE_ERROR,
        FILE_UPLOAD_THREAD_INTERRUPTED,
        FILE_UPLOAD_READY_TO_POLL,
        FILE_UPLOAD_NEW,
        FILE_UPLOAD_RESUMABLE_STARTING,
        FILE_UPLOAD_INSTANT_STARTING,
        FILE_UPLOAD_API_ERROR,
        FILE_UPLOAD_POLLING_FINISHED_WITHOUT_ALL_UNITS_READY,
    }
}
