package com.mediafire.sdk;

public interface MediaFireCredentialsStore {

    int TYPE_NONE = 0;
    int TYPE_EMAIL = 1;
    int TYPE_EKEY = 2;
    int TYPE_FACEBOOK = 3;
    int TYPE_TWITTER = 4;

    /**
     * clears credentials
     */
    void clear();

    /**
     * sets credentials to email
     * @param credentials
     */
    void setEmail(EmailCredentials credentials);

    /**
     * sets credentials to ekey
     * @param credentials
     */
    void setEkey(EkeyCredentials credentials);

    /**
     * sets credentials to facebook
     * @param credentials
     */
    void setFacebook(FacebookCredentials credentials);

    /**
     * sets credentials to twitter
     * @param credentials
     */
    void setTwitter(TwitterCredentials credentials);

    /**
     * gets type stored
     * @return
     */
    int getTypeStored();

    /**
     * gets email credentials
     * @return null if no email credentials are stored
     */
    EmailCredentials getEmailCredentials();

    /**
     * gets ekey credentials
     * @return null if no ekey credentials are stored
     */
    EkeyCredentials getEkeyCredentials();

    /**
     * gets facebook credentials
     * @return null if no facebook credentials are stored
     */
    FacebookCredentials getFacebookCredentials();

    /**
     * gets twitter credentials
     * @return null if no twitter credentials are stored
     */
    TwitterCredentials getTwitterCredentials();

    interface EmailCredentials {
        String getEmail();
        String getPassword();
    }

    interface EkeyCredentials {
        String getEkey();
        String getPassword();
    }

    interface FacebookCredentials {
        String getFacebookAccessToken();
    }

    interface TwitterCredentials {
        String getTwitterOauthToken();
        String getTwitterOauthTokenSecret();
    }
}
