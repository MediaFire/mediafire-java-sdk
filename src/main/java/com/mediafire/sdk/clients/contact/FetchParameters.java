package com.mediafire.sdk.clients.contact;

/**
 * Created by jondh on 11/4/14.
 */
public class FetchParameters {
    private String mMethod;
    private String mContactKey;
    private String mGroupContacts;
    private String mStart;
    private String mLimit;
    private String mRaw;

    private FetchParameters(Builder builder) {
        mMethod = builder.mMethod;
        mContactKey = builder.mContactKey;
        mGroupContacts = builder.mGroupContacts;
        mStart = builder.mStart;
        mLimit = builder.mLimit;
        mRaw = builder.mRaw;
    }

    public String getMethod() {
        return mMethod;
    }

    public String getContactKey() {
        return mContactKey;
    }

    public String getGroupContacts() {
        return mGroupContacts;
    }

    public String getStart() {
        return mStart;
    }

    public String getLimit() {
        return mLimit;
    }

    public String getRaw() {
        return mRaw;
    }

    public static class Builder {
        private String mMethod;
        private String mContactKey;
        private String mGroupContacts;
        private String mStart;
        private String mLimit;
        private String mRaw;

        public Builder() { }
        
        public Builder method(Method method) {
            if (method == null) {
                return this;
            }

            switch (method) {
                case NORMAL:
                    mMethod = "normal";
                    break;
                case AUTOCOMPLETE:
                    mMethod = "autocomplete";
                    break;
            }

            return this;
        }

        public Builder contactKey(String contactKey) {
            if (contactKey == null) {
                return this;
            }

            mContactKey = contactKey;
            return this;
        }

        public Builder groupContacts(boolean groupContacts) {
            mGroupContacts = groupContacts ? "yes" : "no";
            return this;
        }

        public Builder start(int start) {
            mStart = String.valueOf(start);
            return this;
        }

        public Builder limit(String limit) {
            if (limit == null) {
                return this;
            }

            mLimit = limit;
            return this;
        }

        public Builder raw(String raw) {
            if (raw == null) {
                return this;
            }

            mRaw = raw;
            return this;
        }

        public FetchParameters build() {
            return new FetchParameters(this);
        }

        public enum Method {
            NORMAL, AUTOCOMPLETE
        }
    }
}
