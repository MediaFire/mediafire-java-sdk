package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class GetContentParameters {
    public String mFolderKey;
    public String mContentType;
    public String mFilter;
    public String mDeviceId;
    public String mOrderBy;
    public String mOrderDirection;
    public String mChunk;
    public String mChunkSize;
    public String mDetails;

    public GetContentParameters() { }

    public GetContentParameters folderKey(String folderKey) {
        if (folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }

    public GetContentParameters contentType(ContentType contentType) {
        if (contentType == null) {
            return this;
        }

        switch(contentType) {
            case FILES:
                mContentType = "files";
                break;
            case FOLDERS:
                mContentType = "folders";
                break;
        }

        return this;
    }

    public GetContentParameters filter(String filter) {
        if (filter == null) {
            return this;
        }

        mFilter = filter;
        return this;
    }

    public GetContentParameters deviceId(String deviceId) {
        if (deviceId == null) {
            return this;
        }

        mDeviceId = deviceId;
        return this;
    }

    public GetContentParameters orderBy(OrderBy orderBy) {
        if (orderBy == null) {
            return this;
        }

        switch(orderBy) {
            case NAME:
                mOrderBy = "name";
                break;
            case CREATED:
                mOrderBy = "created";
                break;
            case SIZE:
                mOrderBy = "size";
                break;
            case DOWNLOADS:
                mOrderBy = "downloads";
                break;
        }

        return this;
    }

    public GetContentParameters orderDirection(OrderDirection orderDirection) {
        if (orderDirection == null) {
            return this;
        }

        switch(orderDirection) {
            case ASCENDING:
                mOrderDirection = "asc";
                break;
            case DESCENDING:
                mOrderDirection = "desc";
                break;
        }

        return this;
    }

    public GetContentParameters chunk(int chunk) {
        mChunk = String.valueOf(chunk);
        return this;
    }

    public GetContentParameters chunkSize(int chunkSize) {
        mChunkSize = String.valueOf(chunkSize);
        return this;
    }

    public GetContentParameters details(String details) {
        if (details == null) {
            return this;
        }

        mDetails = details;
        return this;
    }
    
    public enum OrderBy {
        NAME, CREATED, SIZE, DOWNLOADS
    }
    
    public enum ContentType {
        FILES, FOLDERS,
    }
    
    public enum OrderDirection {
        ASCENDING, DESCENDING
    }
}
