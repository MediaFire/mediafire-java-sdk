package com.mediafire.sdk.clients.contact;

/**
 * Created by jondh on 11/4/14.
 */
public class FetchParameters {
    public String mMethod;
    public String mContactKey;
    public String mGroupContacts;
    public String mStart;
    public String mLimit;
    public String mRaw;

    public FetchParameters method(Method method){
        if(method == null) {
            return this;
        }

        switch (method) {
            case NORMAL:
                mMethod = "normal";
                break;
            case AUTOCOMPLETE:
                mMethod = "autocomplete";
                break;
        }

        return this;
    }

    public FetchParameters contactKey(String contactKey){
        if(contactKey == null) {
            return this;
        }

        mContactKey = contactKey;
        return this;
    }

    public FetchParameters groupContacts(boolean groupContacts){
        mGroupContacts = groupContacts ? "yes" : "no";
        return this;
    }

    public FetchParameters start(int start){
        mStart = String.valueOf(start);
        return this;
    }

    public FetchParameters limit(String limit){
        if(limit == null) {
            return this;
        }

        mLimit = limit;
        return this;
    }

    public FetchParameters raw(String raw){
        if(raw == null) {
            return this;
        }

        mRaw = raw;
        return this;
    }

    public enum Method {
        NORMAL, AUTOCOMPLETE
    }

}
