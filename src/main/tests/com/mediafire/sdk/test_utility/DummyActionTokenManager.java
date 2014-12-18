package com.mediafire.sdk.test_utility;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
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

    private ImageActionToken createImageActionToken() {
        String token = "0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";
        ImageActionToken imageActionToken = new ImageActionToken(token, 1440);
        return imageActionToken;
    }

    private UploadActionToken createUploadActionToken() {
        String token = "0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";
        UploadActionToken uploadActionToken = new UploadActionToken(token, 1440);
        return uploadActionToken;
    }
}
