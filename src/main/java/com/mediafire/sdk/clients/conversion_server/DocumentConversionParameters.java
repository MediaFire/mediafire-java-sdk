package com.mediafire.sdk.clients.conversion_server;

/**
 * Created by jondh on 11/5/14.
 */
public class DocumentConversionParameters {
    public String mQuickKey;
    public String mPage;
    public String mDocType;
    public String mSizeId;
    public String mHash;
    public String mToken;
    public String mOutput;
    public String mMetadata;
    public String mRequestConversionOnly;

    public DocumentConversionParameters(String quickKey, String page, String docType, String hash) {
        mQuickKey = quickKey;
        mPage = page;
        mDocType = docType;
        mHash = hash.substring(0, 3);
    }

    public DocumentConversionParameters token(String token) {
        if(token == null) {
            return this;
        }

        mToken = token;
        return this;
    }

    public DocumentConversionParameters output(String output) {
        if(output == null) {
            return this;
        }

        mOutput = output;
        return this;
    }

    public DocumentConversionParameters sizeId(String sizeId) {
        if(sizeId == null) {
            return this;
        }

        mSizeId = sizeId;
        return this;
    }

    public DocumentConversionParameters metadata(boolean metadata) {
        mMetadata = metadata ? "1" : "0";
        return this;
    }

    public DocumentConversionParameters requestConversionOnly(boolean requestConversionOnly) {
        mRequestConversionOnly = requestConversionOnly ? "1" : "0";
        return this;
    }

    public String makeQuery() {
        String query = "?";
        query += mHash;
        query += "&quickkey=" + mQuickKey;
        query += "&doc_type=" + mDocType;
        query += "&page=" + mPage;
        if(mOutput != null) {
            query += "&output=" + mOutput;
        }
        if(mSizeId != null) {
            query += "&size_id=" + mSizeId;
        }
        if(mMetadata != null) {
            query += "&metadata=" + mMetadata;
        }
        if(mRequestConversionOnly != null) {
            query += "&request_conversion_only=" + mRequestConversionOnly;
        }
        if(mToken != null) {
            query += "&session_token=" + mToken;
        }
        return query;
    }
}
