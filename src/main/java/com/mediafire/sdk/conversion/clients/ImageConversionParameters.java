package com.mediafire.sdk.conversion.clients;

/**
 * Created by jondh on 11/5/14.
 */
public class ImageConversionParameters {
    public final String mQuickKey;
    public final String mDocType = "i";
    public final String mSizeId;
    public final String mHash;
    public String mToken;
    public String mOutput;
    public String mRequestConversionOnly;

    public ImageConversionParameters(String quickKey, String sizeId, String hash) {
        mQuickKey = quickKey;
        mSizeId = sizeId;
        mHash = hash.substring(0, 3);
    }

    public ImageConversionParameters token(String token) {
        if(token == null) {
            return this;
        }

        mToken = token;
        return this;
    }

    public ImageConversionParameters output(String output) {
        if(output == null) {
            return this;
        }

        mOutput = output;
        return this;
    }

    public ImageConversionParameters requestConversionOnly(boolean requestConversionOnly) {
        mRequestConversionOnly = requestConversionOnly ? "1" : "0";
        return this;
    }

    public String makeQuery() {
        String query = "?";
        query += mHash;
        query += "&quickkey=" + mQuickKey;
        query += "&doc_type=" + mDocType;
        query += "&size_id=" + mSizeId;
        if(mOutput != null) {
            query += "&output=" + mOutput;
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
