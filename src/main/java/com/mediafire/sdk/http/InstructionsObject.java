package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class InstructionsObject {

    private final BorrowTokenType mBorrowToken;
    private final SignatureType mSignatureType;
    private final ReturnTokenType mReturnToken;
    private final boolean mPostQuery;

    public InstructionsObject(BorrowTokenType borrowToken, SignatureType signatureType, ReturnTokenType returnToken, boolean postQuery) {
        mBorrowToken = borrowToken;
        mSignatureType = signatureType;
        mReturnToken = returnToken;
        mPostQuery = postQuery;
    }

    public BorrowTokenType getBorrowTokenType() {
        return mBorrowToken;
    }

    public SignatureType getSignatureType() {
        return mSignatureType;
    }

    public ReturnTokenType getReturnTokenType() {
        return mReturnToken;
    }

    public boolean postQuery() {
        return mPostQuery;
    }
}
