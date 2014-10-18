package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 */
public class ApiObject {
    private final String mFile;
    private final String mPath;
    private final TokenType mBorrowToken;
    private final TokenType mSignatureType;
    private final TokenType mReturnToken;
    private final boolean mQueryPostable;

    public ApiObject(String path, String file, TokenType borrowToken, TokenType signatureType, TokenType returnToken, boolean queryPostable) {
        mPath = path;
        mBorrowToken = borrowToken;
        mSignatureType = signatureType;
        mReturnToken = returnToken;
        mQueryPostable = queryPostable;
        mFile = file;
    }

    public String getPath() {
        return mPath;
    }

    public TokenType getTypeOfTokenToBorrow() {
        return mBorrowToken;
    }

    public TokenType getTypeOfSignatureToAdd() {
        return mSignatureType;
    }

    public TokenType getTypeOfTokenToReturn() {
        return mReturnToken;
    }

    public boolean isQueryPostable() {
        return mQueryPostable;
    }

    public boolean isTokenRequired() {
        return mBorrowToken != TokenType.NONE;
    }

    public String getFile() {
        return mFile;
    }
}
