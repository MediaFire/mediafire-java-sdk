package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris on 11/10/2014.
 */
public class DummyActionTokenManager implements ActionTokenManagerInterface {
    private ImageActionToken imageActionToken;
    private UploadActionToken uploadActionToken;

    @Override
    public void receiveImageActionToken(ImageActionToken token) {
        imageActionToken = token;
    }

    @Override
    public void receiveUploadActionToken(UploadActionToken token) {
        uploadActionToken = token;
    }

    @Override
    public UploadActionToken borrowUploadActionToken() {
        if (uploadActionToken == null) {
            uploadActionToken = createUploadActionToken();
        }
        return uploadActionToken;
    }

    @Override
    public ImageActionToken borrowImageActionToken() {
        if (imageActionToken == null) {
            imageActionToken = createImageActionToken();
        }
        return imageActionToken;
    }

    @Override
    public void tokensFailed() {
        imageActionToken = null;
        uploadActionToken = null;
    }

    @Override
    public void initialize(Configuration configuration) {

    }

    @Override
    public void shutdown() {

    }

    private ImageActionToken createImageActionToken() {
        String token = "verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz";
        ImageActionToken imageActionToken = new ImageActionToken(token, 1440);
        return imageActionToken;
    }

    private UploadActionToken createUploadActionToken() {
        String token = "verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz";
        UploadActionToken uploadActionToken = new UploadActionToken(token, 1440);
        return uploadActionToken;
    }
}
