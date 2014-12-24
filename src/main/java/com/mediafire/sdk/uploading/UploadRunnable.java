package com.mediafire.sdk.uploading;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api.clients.UploadClient;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
abstract class UploadRunnable implements Runnable {

    private final UploadClient mUploadClient;
    private boolean mPaused;

    public UploadRunnable(IHttp http, ITokenManager tokenManager) {
        mUploadClient = new UploadClient(http, tokenManager);
    }

    public final UploadClient getUploadClient() {
        return mUploadClient;
    }

    public final String getResponseStringForGson(String response) {
        if (response == null || response.isEmpty()) {
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        if (element.isJsonObject()) {
            JsonObject jsonResponse = element.getAsJsonObject().get("response").getAsJsonObject();
            return jsonResponse.toString();
        } else {
            return null;
        }
    }

    public final boolean resultValid(Result result) {
        if (result == null){
            return false;
        }

        if (result.getResponse() == null) {
            return false;
        }

        if (result.getResponse().getBytes() == null) {
            return false;
        }

        if (result.getResponse().getHeaderFields() == null) {
            return false;
        }

        if (!result.getResponse().getHeaderFields().containsKey("Content-Type")) {
            return false;
        }

        List<String> contentTypeHeaders = result.getResponse().getHeaderFields().get("Content-Type");

        if (!contentTypeHeaders.contains("application/json")) {
            return false;
        }

        return true;
    }

    abstract Map<String, Object> makeQueryParams() throws Exception;

    @Override
    public abstract void run();

    public final void pause() {
        mPaused = true;
    }

    public final void resume() {
        mPaused = false;
    }

    public final boolean isPaused() {
        return mPaused;
    }

    public final void yieldIfPaused() throws InterruptedException {
        while (isPaused()) {
            Thread.currentThread().yield();
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Runnable interrupted while in pause state");
            }
        }
    }
}
