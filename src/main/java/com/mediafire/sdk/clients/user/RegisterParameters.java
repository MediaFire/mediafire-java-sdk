package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class RegisterParameters {
    private final String mApplicationId;
    private final String mEmail;
    private final String mPassword;
    private final String mFacebookAccessToken;

    private final String mFirstName;
    private final String mLastName;
    private final String mDisplayName;
    
    private RegisterParameters(Builder builder) {
        mApplicationId = builder.mApplicationId;
        mEmail = builder.mEmail;
        mPassword = builder.mPassword;
        mFacebookAccessToken = builder.mFacebookAccessToken;
        mFirstName = builder.mFirstName;
        mLastName = builder.mLastName;
        mDisplayName = builder.mDisplayName;
    }

    public String getApplicationId() {
        return mApplicationId;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getFacebookAccessToken() {
        return mFacebookAccessToken;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public static class Builder {
        private final String mApplicationId;
        private String mEmail;
        private String mPassword;
        private String mFacebookAccessToken;

        private String mFirstName;
        private String mLastName;
        private String mDisplayName;

        public Builder(String applicationId) {
            mApplicationId = applicationId;
        }

        public Builder mediaFireInfo(String email, String password) {
            if (email == null || email.isEmpty()) {
                return this;
            }

            if (password == null || password.isEmpty()) {
                return this;
            }

            mEmail = email;
            mPassword = password;
            //noinspection AssignmentToNull
            mFacebookAccessToken = null;

            return this;
        }

        public Builder facebookInfo(String facebookAccessToken) {
            if (facebookAccessToken == null || facebookAccessToken.isEmpty()) {
                return this;
            }

            mFacebookAccessToken = facebookAccessToken;
            //noinspection AssignmentToNull
            mEmail = null;
            //noinspection AssignmentToNull
            mPassword = null;

            return this;
        }

        public Builder firstName(String firstName) {
            if (firstName == null || firstName.isEmpty()) {
                return this;
            }

            mFirstName = firstName;

            return this;
        }

        public Builder lastName(String lastName) {
            if (lastName == null || lastName.isEmpty()) {
                return this;
            }

            mLastName = lastName;

            return this;
        }

        public Builder displayName(String displayName) {
            if (displayName == null || displayName.isEmpty()) {
                return this;
            }

            mDisplayName = displayName;

            return this;
        }

        public RegisterParameters build() {
            return new RegisterParameters(this);
        }
    }
}
