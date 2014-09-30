package com.mediafire.sdk.defaults;

import com.mediafire.sdk.config.MFCredentialsInterface;
import com.mediafire.sdk.config.MFCredentialsType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO: doc
 */
public final class MFDefaultCredentials implements MFCredentialsInterface {
    public static final String MEDIAFIRE_PARAMETER_EMAIL = "email";
    public static final String MEDIAFIRE_PARAMETER_PASSWORD = "password";
    public static final String TWITTER_PARAMETER_TW_OAUTH_TOKEN = "tw_oauth_token";
    public static final String TWITTER_PARAMETER_TW_OAUTH_TOKEN_SECRET = "tw_oauth_token_secret";
    public static final String FACEBOOK_PARAMETER_FB_ACCESS_TOKEN = "fb_access_token";

    private Map<String, String> userCredentials = new HashMap<String, String>();
    private MFCredentialsType mfCredentialsType;

    /**
     * A default MFCredentialsInterface implementation.
     * This constructor sets the MFCredentialsType to UNSET.
     */
    public MFDefaultCredentials() {
        mfCredentialsType = MFCredentialsType.UNSET;
    }

    @Override
    public boolean setCredentials(Map<String, String> userCredentials) {
        if (isFacebookCredentials(userCredentials)) {
            this.userCredentials = userCredentials;
            mfCredentialsType = MFCredentialsType.FACEBOOK;
            return true;
        }

        if (isTwitterCredentials(userCredentials)) {
            LinkedHashMap<String, String> credentialsMap = new LinkedHashMap<String, String>(2);
            credentialsMap.put(TWITTER_PARAMETER_TW_OAUTH_TOKEN, userCredentials.get(TWITTER_PARAMETER_TW_OAUTH_TOKEN));
            credentialsMap.put(TWITTER_PARAMETER_TW_OAUTH_TOKEN_SECRET, userCredentials.get(TWITTER_PARAMETER_TW_OAUTH_TOKEN_SECRET));
            this.userCredentials = credentialsMap;
            mfCredentialsType = MFCredentialsType.TWITTER;
            return true;
        }

        if (isMediaFireCredentials(userCredentials)) {
            LinkedHashMap<String, String> credentialsMap = new LinkedHashMap<String, String>(2);
            credentialsMap.put(MEDIAFIRE_PARAMETER_EMAIL, userCredentials.get(MEDIAFIRE_PARAMETER_EMAIL));
            credentialsMap.put(MEDIAFIRE_PARAMETER_PASSWORD, userCredentials.get(MEDIAFIRE_PARAMETER_PASSWORD));
            this.userCredentials = credentialsMap;
            mfCredentialsType = MFCredentialsType.MEDIAFIRE;
            return true;
        }

        return false;
    }

    @Override
    public Map<String, String> getCredentials() {
        return userCredentials;
    }

    @Override
    public void clearCredentials() {
        userCredentials.clear();
        mfCredentialsType = MFCredentialsType.UNSET;
    }

    @Override
    public MFCredentialsType getUserCredentialsType() {
        return mfCredentialsType;
    }

    private boolean isFacebookCredentials(Map<String, String> credentials) {
        return credentials != null && credentials.size() == 1 && credentials.containsKey(FACEBOOK_PARAMETER_FB_ACCESS_TOKEN);
    }

    private boolean isTwitterCredentials(Map<String, String> credentials) {
        return credentials != null && credentials.size() == 2 && credentials.containsKey(TWITTER_PARAMETER_TW_OAUTH_TOKEN) && credentials.containsKey(TWITTER_PARAMETER_TW_OAUTH_TOKEN_SECRET);
    }

    private boolean isMediaFireCredentials(Map<String, String> credentials) {
        return credentials != null && credentials.size() == 2 && credentials.containsKey(MEDIAFIRE_PARAMETER_EMAIL) && credentials.containsKey(MEDIAFIRE_PARAMETER_PASSWORD);
    }
}
