package com.mediafire.sdk.api.responses.contact;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.Arrays;
import java.util.List;

public class FetchResponse extends ApiResponse {

    private int count;
    private int revision;
    private int epoch;
    private List<Contact> contacts;

    public int getCount() {
        return count;
    }

    public int getEpoch() {
        return epoch;
    }

    public int getRevision() {
        return revision;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
