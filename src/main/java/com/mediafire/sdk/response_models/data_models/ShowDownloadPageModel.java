package com.mediafire.sdk.response_models.data_models;

public class ShowDownloadPageModel {
    private boolean me_from_me;
    private boolean me_from_others;
    private boolean others_from_me;

    public boolean getMeFromMe() {
        return me_from_me;
    }

    public boolean getMeFromOthers() {
        return me_from_others;
    }

    public boolean getOthersFromMe() {
        return others_from_me;
    }
}
