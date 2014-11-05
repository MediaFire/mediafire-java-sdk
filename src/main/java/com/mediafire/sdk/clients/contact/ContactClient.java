package com.mediafire.sdk.clients.contact;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class ContactClient extends PathSpecificApiClient {
    private static final String PARAM_CONTACT_TYPE = "contact_type";
    private static final String PARAM_CONTACT_KEY = "contact_key";
    private static final String PARAM_DISPLAY_NAME = "display_name";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private static final String PARAM_AVATAR = "avatar";
    private static final String PARAM_SOURCE_UID = "source_uid";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PHONE = "phone";
    private static final String PARAM_BIRTHDAY = "birthday";
    private static final String PARAM_LOCATION = "location";
    private static final String PARAM_GENDER = "gender";
    private static final String PARAM_WEBSITE = "website";
    private static final String PARAM_GROUP_ID = "group_id";
    private static final String PARAM_METHOD = "method";
    private static final String PARAM_GROUP_CONTACTS = "group_contacts";
    private static final String PARAM_START = "start";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_RAW = "raw";

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public ContactClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result add(AddParameters addParameters){
        ApiObject apiObject = new ApiObject("contact", "add.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_CONTACT_TYPE, addParameters.mContactType);
        request.addQueryParameter(PARAM_CONTACT_KEY, addParameters.mContactKey);
        request.addQueryParameter(PARAM_DISPLAY_NAME, addParameters.mDisplayName);
        request.addQueryParameter(PARAM_FIRST_NAME, addParameters.mFirstName);
        request.addQueryParameter(PARAM_LAST_NAME, addParameters.mLastName);
        request.addQueryParameter(PARAM_AVATAR, addParameters.mAvatar);
        request.addQueryParameter(PARAM_SOURCE_UID, addParameters.mSourceUid);
        request.addQueryParameter(PARAM_EMAIL, addParameters.mEmail);
        request.addQueryParameter(PARAM_PHONE, addParameters.mPhone);
        request.addQueryParameter(PARAM_BIRTHDAY, addParameters.mBirthday);
        request.addQueryParameter(PARAM_LOCATION, addParameters.mLocation);
        request.addQueryParameter(PARAM_GENDER, addParameters.mGender);
        request.addQueryParameter(PARAM_WEBSITE, addParameters.mWebsite);
        request.addQueryParameter(PARAM_GROUP_ID, addParameters.mGroupId);

        return doRequestJson(request);
    }

    public Result delete(String contactKey){
        ApiObject apiObject = new ApiObject("contact", "delete.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_CONTACT_KEY, contactKey);

        return doRequestJson(request);
    }

    public Result fetch(FetchParameters fetchParameters){
        ApiObject apiObject = new ApiObject("contact", "fetch.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_METHOD, fetchParameters.mMethod);
        request.addQueryParameter(PARAM_CONTACT_KEY, fetchParameters.mContactKey);
        request.addQueryParameter(PARAM_GROUP_CONTACTS, fetchParameters.mGroupContacts);
        request.addQueryParameter(PARAM_START, fetchParameters.mStart);
        request.addQueryParameter(PARAM_LIMIT, fetchParameters.mLimit);
        request.addQueryParameter(PARAM_RAW, fetchParameters.mRaw);

        return doRequestJson(request);
    }

}
