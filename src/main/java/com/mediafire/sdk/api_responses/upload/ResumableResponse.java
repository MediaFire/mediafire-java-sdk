package com.mediafire.sdk.api_responses.upload;

import com.mediafire.sdk.api_responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;

public class ResumableResponse extends ApiResponse {
    private DoUpload doupload;
    private ResumableUpload resumable_upload;

    public class DoUpload {
        private String result;
        private String key;

        public Result getResultCode() {
            if (result == null || result.isEmpty()) {
                return Result.fromInt(0);
            }
            return Result.fromInt(Integer.parseInt(result));
        }

        public int getResult() {
            if (result == null || result.isEmpty()) {
                return -1;
            }
            return Integer.parseInt(result);
        }

        public String getPollUploadKey() {
            if (key == null) {
                return "";
            }
            return key;
        }
    }

    public class ResumableUpload {
        private String all_units_ready;
        private String number_of_units;
        private String unit_size;
        private Bitmap bitmap;

        public boolean areAllUnitsReady() {
            return "yes".equals(all_units_ready);
        }

        public int getNumberOfUnits() {
            if (number_of_units == null || number_of_units.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(number_of_units);
        }

        public int getUnitSize() {
            if (unit_size == null || unit_size.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(unit_size);
        }

        public Bitmap getBitmap() {
            if (bitmap == null) {
                return new Bitmap();
            }
            return bitmap;
        }

        public class Bitmap {
            private String count;
            private String[] words;

            public int getCount() {
                if (count == null || count.isEmpty()) {
                    return 0;
                }
                return Integer.parseInt(count);
            }

            public List<Integer> getWords() {
                if (words == null || words.length == 0) {
                    return new ArrayList<Integer>();
                }
                return convert(words);
            }

            private List<Integer> convert(String[] words) {
                List<Integer> ret = new ArrayList<Integer>();
                for (String str : words) {
                    ret.add(Integer.parseInt(str));
                }

                if (ret.size() == words.length) {
                    return ret;
                } else {
                    return new ArrayList<Integer>();
                }
            }
        }
    }

    public DoUpload getDoUpload() {
        if (doupload == null) {
            return new DoUpload();
        }
        return doupload;
    }

    public ResumableUpload getResumableUpload() {
        if (resumable_upload == null) {
            return new ResumableUpload();
        }
        return resumable_upload;
    }

    public enum Result {
        NO_ERROR(0),
        SUCCESS_FILE_MOVED_TO_ROOT(14),
        DROPBOX_KEY_INVALID_1(-1),
        DROPBOX_KEY_INVALID_2(-8),
        DROPBOX_KEY_INVALID_3(-11),
        INVALID_DROPBOX_CONFIG_1(-21),
        INVALID_DROPBOX_CONFIG_2(-22),
        UNKOWN_UPLOAD_ERROR_1(-31),
        UNKOWN_UPLOAD_ERROR_2(-40),
        MISSING_FILE_DATA(-32),
        UPLOAD_EXCEEDS_UPLOAD_MAX_FILESIZE(-41),
        UPLOAD_EXCEEDS_MAX_FILE_SIZE_SPECIFIED_IN_HTML_FORM(-42),
        UPLOAD_FILE_ONLY_PARTIALLY_UPLOADED(-43),
        NO_FILE_UPLOADED(-44),
        MISSING_TEMPORARY_FOLDER(-45),
        FAILED_TO_WRITE_FILE_TO_DISK(-46),
        PHP_EXTENSION_STOPPED_UPLOAD(-47),
        INVALID_FILE_SIZE(-48),
        MISSING_FILE_NAME(-49),
        FILE_SIZE_DOES_NOT_MATCH_SIZE_ON_DISK(-51),
        HASH_SENT_MISMATCH_ACTUAL_FILE_HASH(-90),
        MISSING_OR_INVALID_SESSION_TOKEN(-99),
        INVALID_QUICKKEY_OR_FILE_DOES_NOT_BELONG_TO_SESSION_USER(-203),
        USER_DOES_NOT_HAVE_WRITE_PERMISSIONS_FOR_THIS_FILE(-204),
        USER_DOES_NOT_HAVE_WRITE_PERMISSION_FOR_DESTINATION_FOLDER(-205),
        ATTEMPTING_RESUMABLE_UPLOAD_UNIT_UPLOAD_BEFORE_CALLING_PRE_UPLOAD(-302),
        INVALID_UNIT_SIZE(-303),
        INVALID_UNIT_HASH(-304),
        MAXIMUM_FILE_SIZE_FOR_FREE_USERS_EXCEEDED_1(-701),
        MAXIMUM_FILE_SIZE_FOR_FREE_USERS_EXCEEDED_2(-881),
        MAXIMUM_FILE_SIZE_EXCEEDED_1(-700),
        MAXIMUM_FILE_SIZE_EXCEEDED_2(-882),
        INTERNAL_SERVER_ERROR_1(-10),
        INTERNAL_SERVER_ERROR_2(-12),
        INTERNAL_SERVER_ERROR_3(-26),
        INTERNAL_SERVER_ERROR_4(-50),
        INTERNAL_SERVER_ERROR_5(-52),
        INTERNAL_SERVER_ERROR_6(-53),
        INTERNAL_SERVER_ERROR_7(-54),
        INTERNAL_SERVER_ERROR_8(-70),
        INTERNAL_SERVER_ERROR_9(-71),
        INTERNAL_SERVER_ERROR_10(-80),
        INTERNAL_SERVER_ERROR_11(-120),
        INTERNAL_SERVER_ERROR_12(-122),
        INTERNAL_SERVER_ERROR_13(-124),
        INTERNAL_SERVER_ERROR_14(-140),
        INTERNAL_SERVER_ERROR_15(-200),
        INTERNAL_SERVER_ERROR_16(-301),;

        private final int value;

        private Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Result fromInt(int value) {
            for (final Result e : values()) {
                if (e.getValue() == value) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Return code out of range : " + value);
        }
    }

}

