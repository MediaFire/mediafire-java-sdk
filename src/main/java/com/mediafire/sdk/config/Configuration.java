package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 5/14/2015.
 */
public class Configuration {
    private static final long TWO_MINUTES = 1000 * 60 * 2;
    private static final long TEN_MINUTES = 1000 * 60 * 10;

    private MFCredentials credentials;
    private MFHttpRequester httpRequester;
    private MFSessionRequester sessionRequester;
    private MFActionRequester actionRequester;
    private String alternateDomain;

    public Configuration() { }

    public MFCredentials getCredentials() {
        return credentials;
    }

    public MFHttpRequester getHttpRequester() {
        return httpRequester;
    }

    public MFSessionRequester getSessionRequester() {
        return sessionRequester;
    }

    public MFActionRequester getActionRequester() {
        return actionRequester;
    }

    public String getAlternateDomain() {
        return alternateDomain;
    }

    public void setCredentials(MFCredentials mediaFireCredentials) {
        this.credentials = mediaFireCredentials;
    }

    public void setHttpRequester(MFHttpRequester mediaFireHttpRequester) {
        this.httpRequester = mediaFireHttpRequester;
    }

    public void setSessionRequester(MFSessionRequester mediaFireSessionRequester) {
        this.sessionRequester = mediaFireSessionRequester;
    }

    public void setActionRequester(MFActionRequester mediaFireActionRequester) {
        this.actionRequester = mediaFireActionRequester;
    }

    public void setAlternateDomain(String alternateDomain) {
        this.alternateDomain = alternateDomain;
    }

    public static Configuration getDefault() {
        // store for session tokens
        MFStore<SessionToken> sessionStore = new DefaultSessionStore();
        MFStore<ActionToken> imageStore = new DefaultActionStore(TWO_MINUTES);
        MFStore<ActionToken> uploadStore = new DefaultActionStore(TEN_MINUTES);

        MFCredentials credentials = new DefaultCredentials();
        MFHttpRequester httpRequester = new DefaultHttpRequester(5000, 45000);
        MFSessionRequester sessionRequester = new DefaultSessionRequester(credentials, httpRequester, sessionStore);
        MFActionRequester actionRequester = new DefaultActionRequester(httpRequester, sessionRequester, imageStore, uploadStore);

        Configuration configuration = new Configuration();
        configuration.setCredentials(credentials);
        configuration.setHttpRequester(httpRequester);
        configuration.setSessionRequester(sessionRequester);
        configuration.setActionRequester(actionRequester);
        return configuration;
    }
}
