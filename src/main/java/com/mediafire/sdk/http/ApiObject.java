package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 */
public class ApiObject {
    private final String mFile;
    private final String mApiVersion;
    private final String mPath;
    private final TokenType mBorrowToken;
    private final TokenType mSignatureType;
    private final TokenType mReturnToken;
    private final boolean mQueryPostable;

    private ApiObject(String apiVersion, String path, String file, TokenType borrowToken, TokenType signatureType, TokenType returnToken, boolean queryPostable) {
        mApiVersion = apiVersion;
        mPath = path;
        mBorrowToken = borrowToken;
        mSignatureType = signatureType;
        mReturnToken = returnToken;
        mQueryPostable = queryPostable;
        mFile = file;
    }

    public String getApiVersion() {
        return mApiVersion;
    }
    /**
     * gets the uri for this enum that should be used for a request.
     * @return uri as a String that should be used for a request.
     */
    public String getUri() {
        return mPath;
    }

    /**
     * gets the type of Token to borrow for a request.
     * @return a TokenType to borrow for a request.
     */
    public TokenType getTypeOfTokenToBorrow() {
        return mBorrowToken;
    }

    /**
     * gets the TokenType of signature to add for a request.
     * @return a TokenType representing the type of signature to use.
     */
    public TokenType getTypeOfSignatureToAdd() {
        return mSignatureType;
    }

    /**
     * gets the type of Token to return after the requset is made.
     * @return a TokenType to return for a request.
     */
    public TokenType getTypeOfTokenToReturn() {
        return mReturnToken;
    }

    /**
     * gets whether or not the query can be sent via POST.
     * @return true if query can be sent via POST, false if the query must be sent via GET.
     */
    public boolean isQueryPostable() {
        return mQueryPostable;
    }

    /**
     * gets whether or not a Token must be used.
     * @return true if a Token must be used, false if a Token does not need to be used.
     */
    public boolean isTokenRequired() {
        return mBorrowToken != TokenType.NONE;
    }

    public String getFile() {
        return mFile;
    }

    public enum TokenType {
        NEW, V2, UPLOAD, IMAGE, NONE,
    }
}
