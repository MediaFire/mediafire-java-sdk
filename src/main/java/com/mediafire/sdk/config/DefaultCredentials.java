package com.mediafire.sdk.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultCredentials implements MFCredentials {

    private final Map<String, String> credentials = new HashMap<String, String>();
    private final Logger logger;
    private boolean valid;

    public DefaultCredentials() {
        this.logger = Logger.getLogger("com.mediafire.sdk.config.DefaultCredentials");
        this.logger.setLevel(Level.ALL);
    }

    @Override
    public void setCredentials(Map<String, String> credentials) {
        logger.info("setCredentials()");
        this.credentials.clear();
        this.credentials.putAll(credentials);
    }

    @Override
    public Map<String, String> getCredentials() {
        logger.info("getCredentials()");
        return credentials;
    }

    @Override
    public void invalidate() {
        logger.info("invalidate()");
        valid = false;
        credentials.clear();
    }

    @Override
    public boolean setValid() {
        logger.info("setValid()");
        if (credentials.isEmpty()) {
            valid = false;
            return false;
        } else {
            valid = true;
            return true;
        }
    }

    @Override
    public boolean isValid() {
        logger.info("isValid()");
        return valid;
    }

    @Override
    public void addLoggerHandler(Handler loggerHandler) {
        logger.addHandler(loggerHandler);
    }
}
