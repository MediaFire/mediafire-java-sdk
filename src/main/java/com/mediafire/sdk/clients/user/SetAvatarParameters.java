package com.mediafire.sdk.clients.user;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetAvatarParameters {
    private final String mAction;
    private final String mQuickKey;
    private final String mUrl;

    public SetAvatarParameters(Builder builder) {
        mAction = builder.mAction;
        mQuickKey = builder.mQuickKey;
        mUrl = builder.mUrl;
    }

    public String getAction() {
        return mAction;
    }

    public String getQuickKey() {
        return mQuickKey;
    }

    public String getUrl() {
        return mUrl;
    }
    
    public static class Builder {
        private String mAction;
        private String mQuickKey;
        private String mUrl;
        
        public Builder(String resource) {
            try {
                new URL(resource);
                mUrl = resource;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                mQuickKey = resource;
            }
        }
        
        public Builder action(boolean remove) {
            if (remove) {
                mAction = "remove";
            } else {
                mAction = "set_default";
            }

            return this;
        }

        public SetAvatarParameters build() {
            return new SetAvatarParameters(this);
        }
    }
}
