package com.mediafire.sdk.clients.meta;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperApi;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class MetaClient {
    private static final String TAG = MetaClient.class.getCanonicalName();

    private static final String PARAM_LIST_KEY = "list_key";
    private static final String PARAM_QUICK_KEYS = "quickkeys";
    private static final String PARAM_QUICK_KEY = "quickkey";
    private static final String PARAM_QUICK_KEY_GET_LINKS = "quick_key";
    private static final String PARAM_LINK_TYPE = "link_type";
    private static final String PARAM_META_PREFIX = "meta_";
    private static final String PARAM_RETURN_DATA = "return_data";
    private static final String PARAM_ORDER_BY = "order_by";
    private static final String PARAM_ORDER_DIRECTION = "order_direction";
    private static final String PARAM_CHUNK = "chunk";

    private final SessionTokenManagerInterface mSessionTokenManager;
    private final HttpWorkerInterface mHttpWorker;
    private final ApiRequestGenerator mApiRequestGenerator;

    public MetaClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManager) {
        // init host object
        mHttpWorker = httpWorkerInterface;
        mSessionTokenManager = sessionTokenManager;
        mApiRequestGenerator = new ApiRequestGenerator(ApiVersion.VERSION_1_2);
    }


    public Result addToList(String listKey, String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/add_to_list.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);

        return apiClient.doRequest(request);
    }

    public Result removeFromList(String listKey, String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/remove_from_list.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);

        return apiClient.doRequest(request);
    }

    public Result delete(String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/delete.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result get(String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result getLinks(String quickKey, String linkType) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get_links.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_LINK_TYPE, linkType);
        request.addQueryParameter(PARAM_QUICK_KEY_GET_LINKS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result getLinks(String quickKey) {
        return getLinks(quickKey, null);
    }

    public Result query(QueryBuilder queryBuilder) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/query.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_CHUNK, queryBuilder.getChunk());
        request.addQueryParameter(PARAM_ORDER_DIRECTION, queryBuilder.getOrderDirection());
        request.addQueryParameter(PARAM_ORDER_BY, queryBuilder.getOrderBy());
        request.addQueryParameter(PARAM_RETURN_DATA, queryBuilder.getReturnData());

        for (String key : queryBuilder.getMetaDataFilters().keySet()) {
            request.addQueryParameter(PARAM_META_PREFIX + key, queryBuilder.getMetaDataFilters().get(key));
        }

        return apiClient.doRequest(request);
    }

    public Result set(String quickKey, Map<String, String> metaKeyValuePairs) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/set.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return apiClient.doRequest(request);
    }
}
