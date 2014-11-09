package com.mediafire.sdk.clients.meta;

import com.mediafire.sdk.clients.ClientHelper;
import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.BorrowTokenType;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.InstructionsObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.http.ReturnTokenType;
import com.mediafire.sdk.http.SignatureType;

import java.util.Map;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class MetaClient extends PathSpecificApiClient {
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

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public MetaClient(ClientHelper clientHelper, HttpWorkerInterface httpWorkerInterface, String apiVersion) {
        super(clientHelper, httpWorkerInterface, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result addToList(String listKey, String quickKey) {
        ApiObject apiObject = new ApiObject("meta", "add_to_list.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);
        return doRequestJson(request);
    }

    public Result removeFromList(String listKey, String quickKey) {
        ApiObject apiObject = new ApiObject("meta", "remove_from_list.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);
        return doRequestJson(request);
    }

    public Result delete(String quickKey) {
        ApiObject apiObject = new ApiObject("meta", "delete.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        return doRequestJson(request);
    }

    public Result get(String quickKey) {
        ApiObject apiObject = new ApiObject("meta", "get.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        // add comma separated key list query param
        request.addQueryParameter(PARAM_QUICK_KEYS, quickKey);
        return doRequestJson(request);
    }

    public Result getLinks(String quickKey, String linkType) {
        ApiObject apiObject = new ApiObject("meta", "get_links.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        // add link type query param
        request.addQueryParameter(PARAM_LINK_TYPE, linkType);
        // add quick key param
        request.addQueryParameter(PARAM_QUICK_KEY_GET_LINKS, quickKey);

        return doRequestJson(request);
    }

    public Result getLinks(String quickKey) {
        return getLinks(quickKey, null);
    }

    public Result query(QueryBuilder queryBuilder) {
        ApiObject apiObject = new ApiObject("meta", "query.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_CHUNK, queryBuilder.mChunk);
        request.addQueryParameter(PARAM_ORDER_DIRECTION, queryBuilder.mOrderDirection);
        request.addQueryParameter(PARAM_ORDER_BY, queryBuilder.mOrderBy);
        request.addQueryParameter(PARAM_RETURN_DATA, queryBuilder.mReturnData);

        for (String key : queryBuilder.mMetaDataFilters.keySet()) {
            request.addQueryParameter(PARAM_META_PREFIX + key, queryBuilder.mMetaDataFilters.get(key));
        }

        return doRequestJson(request);
    }

    public Result set(String quickKey, Map<String, String> metaKeyValuePairs) {
        ApiObject apiObject = new ApiObject("meta", "set.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        
        // add meta K,V pairs
        for (String key : metaKeyValuePairs.keySet()) {
            request.addQueryParameter(PARAM_META_PREFIX + key, metaKeyValuePairs.get(key));
        }

        // add quickkey param to query parameters
        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        
        return doRequestJson(request);
    }
}
