package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 5/14/2015.
 */
public class Configuration {

    private MFCredentials credentials;
    private MFHttpRequester httpRequester;
    private MFSessionRequester sessionRequester;
    private MFActionRequester actionRequester;
    private String alternateDomain;
    private String apiKey;
    private String appId;

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

    public String getApiKey() {
        return apiKey;
    }

    public String getAppId() {
        return appId;
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

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public static Configuration getDefault(String appId, String apiKey) {
        // store for session tokens
        MFStore<SessionToken> sessionStore = new DefaultSessionStore();
        MFStore<ActionToken> imageStore = new DefaultActionStore(2);
        MFStore<ActionToken> uploadStore = new DefaultActionStore(60);

        MFCredentials credentials = new DefaultCredentials();
        MFHttpRequester httpRequester = new DefaultHttpRequester(5000, 45000);
        MFSessionRequester sessionRequester = new DefaultSessionRequester(credentials, apiKey, appId, httpRequester, sessionStore);
        MFActionRequester actionRequester = new DefaultActionRequester(httpRequester, sessionRequester, imageStore, uploadStore);

        Configuration configuration = new Configuration();
        configuration.setApiKey(apiKey);
        configuration.setAppId(appId);
        configuration.setCredentials(credentials);
        configuration.setHttpRequester(httpRequester);
        configuration.setSessionRequester(sessionRequester);
        configuration.setActionRequester(actionRequester);
        return configuration;
    }

    public static Configuration getDefault(String appId) {
        return getDefault(appId, null);
    }
}
