package com.mediafire.sdk.config;

/**
 * CredentialsInterface stores a set of credentials
 */
public interface IUserCredentials {

    public void setCredentials(Email email);

    public void setCredentials(Ekey ekey);

    public void setCredentials(Facebook facebook);

    public void setCredentials(Twitter twitter);

    public String getCredentialsString();

    public Credentials getCredentials();

    public void clearCredentials();

    abstract static class Credentials {
        public abstract String getCredentialsString();
    }

    public static class Twitter extends Credentials {
        private String mOauthToken;
        private String mTokenSecret;

        public Twitter(String oauthToken, String tokenSecret) {
            mOauthToken = oauthToken;
            mTokenSecret = tokenSecret;
        }

        public String getOauthToken() {
            return mOauthToken;
        }

        public String getTokenSecret() {
            return mTokenSecret;
        }

        @Override
        public String getCredentialsString() {
            return mOauthToken + mTokenSecret;
        }
    }

    public static class Facebook extends Credentials {
        private String mFacebookAccessToken;

        public Facebook(String facebookToken) {
            mFacebookAccessToken = facebookToken;
        }

        public String getFacebookAccessToken() {
            return mFacebookAccessToken;
        }

        @Override
        public String getCredentialsString() {
            return mFacebookAccessToken;
        }
    }

    public static class Email extends Credentials {
        private String mEmail;
        private String mPassword;

        public Email(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        public String getEmail() {
            return mEmail;
        }

        public String getPassword() {
            return mPassword;
        }

        @Override
        public String getCredentialsString() {
            return mEmail + mPassword;
        }
    }

    public static class Ekey extends Credentials {
        private String mEkey;
        private String mPassword;

        public Ekey(String ekey, String password) {
            mEkey = ekey;
            mPassword = password;
        }

        public String getEkey() {
            return mEkey;
        }

        public String getPassword() {
            return mPassword;
        }

        @Override
        public String getCredentialsString() {
            return mEkey + mPassword;
        }
    }

}
