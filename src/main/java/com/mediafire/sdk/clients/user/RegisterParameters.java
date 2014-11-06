package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class RegisterParameters {
    final String mApplicationId;
    String mEmail;
    String mPassword;
    String mFacebookAccessToken;

    String mFirstName;
    String mLastName;
    String mDisplayName;

    public RegisterParameters(String applicationId) {
        mApplicationId = applicationId;
    }

    public RegisterParameters mediaFireInfo(String email, String password) {
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

    public RegisterParameters facebookInfo(String facebookAccessToken) {
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

    public RegisterParameters firstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return this;
        }

        mFirstName = firstName;

        return this;
    }

    public RegisterParameters lastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return this;
        }

        mLastName = lastName;

        return this;
    }

    public RegisterParameters displayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return this;
        }

        mDisplayName = displayName;

        return this;
    }
}
