package com.mediafire.sdk.uploader.calls;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.uploader.call_manager.CallUploadObserver;
import com.mediafire.sdk.uploader.upload_items.Upload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 11/13/2014.
 */
public class ObservableUploadCall {
    
    List<CallUploadObserver> observers = new ArrayList<CallUploadObserver>();
    
    public void notifyObservers(Upload upload, ApiResponse apiResponse) {
        for (CallUploadObserver observer : observers) {
            observer.stateChange(upload, apiResponse);
        }
    }
        
    public void addObserver(CallUploadObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(CallUploadObserver observer) {
        observers.remove(observer);
    }
    
    public void clearObservers() {
        observers.clear();
    }

    public enum State {
        AWAITING_NETWORK_CONNECTION,

    }
}
