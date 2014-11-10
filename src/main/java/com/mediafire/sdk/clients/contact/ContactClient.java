package com.mediafire.sdk.clients.contact;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperApi;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class ContactClient {
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

    private final HttpWorkerInterface mHttpWorker;
    private final SessionTokenManagerInterface mSessionTokenManager;
    private ApiRequestGenerator mApiRequestGenerator;


    public ContactClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface, String apiVersion) {
        mHttpWorker = httpWorkerInterface;
        mSessionTokenManager = sessionTokenManagerInterface;
        mApiRequestGenerator = new ApiRequestGenerator(apiVersion);
    }

    public ContactClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
        this(httpWorkerInterface, sessionTokenManagerInterface, ApiVersion.VERSION_CURRENT);
    }

    public Result add(AddParameters addParameters){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/add.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

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

        return apiClient.doRequest(request);
    }

    public Result delete(String contactKey){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/delete.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_CONTACT_KEY, contactKey);

        return apiClient.doRequest(request);
    }

    public Result fetch(FetchParameters fetchParameters){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/fetch.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_METHOD, fetchParameters.mMethod);
        request.addQueryParameter(PARAM_CONTACT_KEY, fetchParameters.mContactKey);
        request.addQueryParameter(PARAM_GROUP_CONTACTS, fetchParameters.mGroupContacts);
        request.addQueryParameter(PARAM_START, fetchParameters.mStart);
        request.addQueryParameter(PARAM_LIMIT, fetchParameters.mLimit);
        request.addQueryParameter(PARAM_RAW, fetchParameters.mRaw);

        return apiClient.doRequest(request);
    }

}
