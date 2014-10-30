package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 * InstructionsObject contains the instructions to perform an api request
 */
public class InstructionsObject {

    private final BorrowTokenType mBorrowToken;
    private final SignatureType mSignatureType;
    private final ReturnTokenType mReturnToken;
    private final boolean mPostQuery;

    /**
     * InstructionsObject Constructor
     * @param borrowToken BorrowTokenType is the type of token to borrow
     * @param signatureType SignatureType is the type of signature to add
     * @param returnToken ReturnTokenType is the type of token to return
     * @param postQuery boolean on whether to post the query or not
     */
    public InstructionsObject(BorrowTokenType borrowToken, SignatureType signatureType, ReturnTokenType returnToken, boolean postQuery) {
        mBorrowToken = borrowToken;
        mSignatureType = signatureType;
        mReturnToken = returnToken;
        mPostQuery = postQuery;
    }

    /**
     * Gets the borrow token type
     * @return BorrowTokenType
     */
    public BorrowTokenType getBorrowTokenType() {
        return mBorrowToken;
    }

    /**
     * Gets the signature type
     * @return SignatureType
     */
    public SignatureType getSignatureType() {
        return mSignatureType;
    }

    /**
     * Gets the return token type
     * @return ReturnTokenType
     */
    public ReturnTokenType getReturnTokenType() {
        return mReturnToken;
    }

    /**
     * Is the query postable?
     * @return boolean
     */
    public boolean postQuery() {
        return mPostQuery;
    }
}
