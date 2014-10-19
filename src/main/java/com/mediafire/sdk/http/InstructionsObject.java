package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class InstructionsObject {

    private final TokenType mBorrowToken;
    private final SignatureType mSignatureType;
    private final TokenType mReturnToken;
    private final boolean mPostQuery;

    public InstructionsObject(TokenType borrowToken, SignatureType signatureType, TokenType returnToken, boolean postQuery) {
        mBorrowToken = borrowToken;
        mSignatureType = signatureType;
        mReturnToken = returnToken;
        mPostQuery = postQuery;
    }

    public TokenType getBorrowTokenType() {
        return mBorrowToken;
    }

    public SignatureType getSignatureType() {
        return mSignatureType;
    }

    public TokenType getReturnTokenType() {
        return mReturnToken;
    }

    public boolean postQuery() {
        return mPostQuery;
    }
}
