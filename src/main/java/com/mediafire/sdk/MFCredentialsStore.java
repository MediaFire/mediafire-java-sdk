package com.mediafire.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MFCredentialsStore implements MediaFireCredentialsStore {
    private final Logger logger = LoggerFactory.getLogger(MFCredentialsStore.class);

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EKEY = "ekey";
    private static final String KEY_FACEBOOK_ACCESS_TOKEN = "facebook_access_token";
    private static final String KEY_TWITTER_OAUTH_TOKEN = "twitter_oauth_token";
    private static final String KEY_TWITTER_OAUTH_TOKEN_SECRET = "twitter_oauth_token_secret";
    private final Map<String, String> store = new HashMap<String, String>();
    private int storedType = TYPE_NONE;

    public MFCredentialsStore() {
    }

    @Override
    public void clear() {
        logger.info("clearing credentials");
        store.clear();
        storedType = TYPE_NONE;
    }

    @Override
    public void setEmail(EmailCredentials credentials) {
        clear();
        logger.info("storing email credentials: " + credentials);
        this.storedType = TYPE_EMAIL;
        store.put(KEY_EMAIL, credentials.getEmail());
        store.put(KEY_PASSWORD, credentials.getPassword());
    }

    @Override
    public void setEkey(EkeyCredentials credentials) {
        clear();
        logger.info("storing ekey credentials: " + credentials);
        this.storedType = TYPE_EKEY;
        store.put(KEY_EKEY, credentials.getEkey());
        store.put(KEY_PASSWORD, credentials.getPassword());
    }

    @Override
    public void setFacebook(FacebookCredentials credentials) {
        clear();
        logger.info("storing facebook credentials: " + credentials);
        this.storedType = TYPE_FACEBOOK;
        store.put(KEY_FACEBOOK_ACCESS_TOKEN, credentials.getFacebookAccessToken());
    }

    @Override
    public void setTwitter(TwitterCredentials credentials) {
        clear();
        logger.info("storing twitter credentials: " + credentials);
        this.storedType = TYPE_TWITTER;
        store.put(KEY_TWITTER_OAUTH_TOKEN, credentials.getTwitterOauthToken());
        store.put(KEY_TWITTER_OAUTH_TOKEN_SECRET, credentials.getTwitterOauthTokenSecret());
    }

    @Override
    public int getTypeStored() {
        return storedType;
    }

    @Override
    public EmailCredentials getEmailCredentials() {
        if (storedType != TYPE_EMAIL) {
            return null;
        }
        return new EmailCredentials(store.get(KEY_EMAIL), store.get(KEY_PASSWORD));
    }

    @Override
    public EkeyCredentials getEkeyCredentials() {
        if (storedType != TYPE_EKEY) {
            return null;
        }
        return new EkeyCredentials(store.get(KEY_EKEY), store.get(KEY_PASSWORD));
    }

    @Override
    public FacebookCredentials getFacebookCredentials() {
        if (storedType != TYPE_FACEBOOK) {
            return null;
        }
        return new FacebookCredentials(store.get(KEY_FACEBOOK_ACCESS_TOKEN));
    }

    @Override
    public TwitterCredentials getTwitterCredentials() {
        if (storedType != TYPE_TWITTER) {
            return null;
        }
        return new TwitterCredentials(store.get(KEY_TWITTER_OAUTH_TOKEN), store.get(KEY_TWITTER_OAUTH_TOKEN_SECRET));
    }
}
