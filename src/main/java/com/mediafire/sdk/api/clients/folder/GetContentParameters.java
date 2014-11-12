package com.mediafire.sdk.api.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class GetContentParameters {
    private String mFolderKey;
    private String mContentType;
    private String mFilter;
    private String mDeviceId;
    private String mOrderBy;
    private String mOrderDirection;
    private String mChunk;
    private String mChunkSize;
    private String mDetails;
    
    private GetContentParameters(Builder builder) {
        mFolderKey = builder.mFolderKey;
        mContentType = builder.mContentType;
        mFilter = builder.mFilter;
        mDeviceId = builder.mDeviceId;
        mOrderBy = builder.mOrderBy;
        mOrderDirection = builder.mOrderDirection;
        mChunk = builder.mChunk;
        mChunkSize = builder.mChunkSize;
        mDetails = builder.mDetails;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getContentType() {
        return mContentType;
    }

    public String getFilter() {
        return mFilter;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getOrderBy() {
        return mOrderBy;
    }

    public String getOrderDirection() {
        return mOrderDirection;
    }

    public String getChunk() {
        return mChunk;
    }

    public String getChunkSize() {
        return mChunkSize;
    }

    public String getFolderKey() {
        return mFolderKey;
    }

    public static class Builder {
        private String mFolderKey;
        private String mContentType;
        private String mFilter;
        private String mDeviceId;
        private String mOrderBy;
        private String mOrderDirection;
        private String mChunk;
        private String mChunkSize;
        private String mDetails;
    
        public Builder() { }
    
        public Builder folderKey(String folderKey) {
            if (folderKey == null) {
                return this;
            }
    
            mFolderKey = folderKey;
            return this;
        }
    
        public Builder contentType(ContentType contentType) {
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
    
        public Builder filter(String filter) {
            if (filter == null) {
                return this;
            }
    
            mFilter = filter;
            return this;
        }
    
        public Builder deviceId(String deviceId) {
            if (deviceId == null) {
                return this;
            }
    
            mDeviceId = deviceId;
            return this;
        }
    
        public Builder orderBy(OrderBy orderBy) {
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
    
        public Builder orderDirection(OrderDirection orderDirection) {
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
    
        public Builder chunk(int chunk) {
            mChunk = String.valueOf(chunk);
            return this;
        }
    
        public Builder chunkSize(int chunkSize) {
            mChunkSize = String.valueOf(chunkSize);
            return this;
        }
    
        public Builder details(String details) {
            if (details == null) {
                return this;
            }
    
            mDetails = details;
            return this;
        }
        
        public GetContentParameters build() {
            return new GetContentParameters(this);
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
}
