package com.mediafire.sdk.clients.meta;

import com.mediafire.sdk.client_core.ApiClient;
import com.mediafire.sdk.client_helpers.ClientHelperApi;
import com.mediafire.sdk.http.ApiRequestGenerator;
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

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;

    public MetaClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManager) {
        // init host object
        mApiRequestGenerator = new ApiRequestGenerator(ApiVersion.VERSION_1_2);

        ClientHelperApi clientHelper = new ClientHelperApi(sessionTokenManager);
        apiClient = new ApiClient(clientHelper, httpWorkerInterface);
    }


    public Result addToList(String listKey, String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/add_to_list.php");

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);

        return apiClient.doRequest(request);
    }

    public Result removeFromList(String listKey, String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/remove_from_list.php");

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);

        return apiClient.doRequest(request);
    }

    public Result delete(String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/delete.php");

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result get(String quickKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get.php");

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result getLinks(String quickKey, String linkType) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get_links.php");

        request.addQueryParameter(PARAM_LINK_TYPE, linkType);
        request.addQueryParameter(PARAM_QUICK_KEY_GET_LINKS, quickKey);

        return apiClient.doRequest(request);
    }

    public Result getLinks(String quickKey) {
        return getLinks(quickKey, null);
    }

    public Result query(QueryBuilder queryBuilder) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/query.php");

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

        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return apiClient.doRequest(request);
    }
}
