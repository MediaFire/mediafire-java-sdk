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

    private ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;

    public ContactClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
        mApiRequestGenerator = new ApiRequestGenerator(ApiVersion.VERSION_1_2);

        ClientHelperApi clientHelper = new ClientHelperApi(sessionTokenManagerInterface);
        apiClient = new ApiClient(clientHelper, httpWorkerInterface);
    }

    public Result add(AddParameters addParameters){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/add.php");

        request.addQueryParameter(PARAM_CONTACT_TYPE, addParameters.getContactType());
        request.addQueryParameter(PARAM_CONTACT_KEY, addParameters.getContactKey());
        request.addQueryParameter(PARAM_DISPLAY_NAME, addParameters.getDisplayName());
        request.addQueryParameter(PARAM_FIRST_NAME, addParameters.getFirstName());
        request.addQueryParameter(PARAM_LAST_NAME, addParameters.getLastName());
        request.addQueryParameter(PARAM_AVATAR, addParameters.getAvatar());
        request.addQueryParameter(PARAM_SOURCE_UID, addParameters.getSourceUid());
        request.addQueryParameter(PARAM_EMAIL, addParameters.getEmail());
        request.addQueryParameter(PARAM_PHONE, addParameters.getPhone());
        request.addQueryParameter(PARAM_BIRTHDAY, addParameters.getBirthday());
        request.addQueryParameter(PARAM_LOCATION, addParameters.getLocation());
        request.addQueryParameter(PARAM_GENDER, addParameters.getGender());
        request.addQueryParameter(PARAM_WEBSITE, addParameters.getWebsite());
        request.addQueryParameter(PARAM_GROUP_ID, addParameters.getGroupId());

        return apiClient.doRequest(request);
    }

    public Result delete(String contactKey){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/delete.php");

        request.addQueryParameter(PARAM_CONTACT_KEY, contactKey);

        return apiClient.doRequest(request);
    }

    public Result fetch(FetchParameters fetchParameters){
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/fetch.php");

        request.addQueryParameter(PARAM_METHOD, fetchParameters.getMethod());
        request.addQueryParameter(PARAM_CONTACT_KEY, fetchParameters.getContactKey());
        request.addQueryParameter(PARAM_GROUP_CONTACTS, fetchParameters.getGroupContacts());
        request.addQueryParameter(PARAM_START, fetchParameters.getStart());
        request.addQueryParameter(PARAM_LIMIT, fetchParameters.getLimit());
        request.addQueryParameter(PARAM_RAW, fetchParameters.getRaw());

        return apiClient.doRequest(request);
    }

}
