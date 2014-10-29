package com.mediafire.sdk.clients.meta;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ApiUtil;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.BorrowTokenType;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.InstructionsObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.http.ReturnTokenType;
import com.mediafire.sdk.http.SignatureType;
import com.mediafire.sdk.http.VersionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class MetaClient extends ApiClient {
    private static final String TAG = MetaClient.class.getCanonicalName();

    private static final String PARAM_LIST_KEY = "list_key";
    private static final String PARAM_QUICK_KEYS = "quickkeys";
    private static final String PARAM_QUICK_KEY = "quickkey";
    private static final String PARAM_RESPONSE_FORMAT = "response_format";
    private static final String PARAM_LINK_TYPE = "link_type";
    private static final String PARAM_META_PREFIX = "meta_";
    private static final String PARAM_RETURN_DATA = "return_data";
    private static final String PARAM_ORDER_BY = "order_by";
    private static final String PARAM_ORDER_DIRECTION = "order_direction";
    private static final String PARAM_CHUNK = "chunk";


    private HostObject mHostObject;
    private VersionObject mVersionObject;
    InstructionsObject mInstructionsObject;

    public MetaClient(Configuration configuration, String apiVersion) {
        super(configuration);
        // init version object
        if (apiVersion == null || apiVersion.isEmpty()) {
            mVersionObject = new VersionObject(null);
        } else {
            mVersionObject = new VersionObject(apiVersion);
        }

        // init host object
        mHostObject = new HostObject("https", "www", "mediafire.com", "post");

        // init instructions object
        mInstructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public MetaClient(Configuration configuration) {
        this(configuration, null);
    }

    public Result addToList(String listKey, List<String> quickKeys) {
        ApiObject apiObject = new ApiObject("meta", "add_to_list.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        // add comma separated key list query param
        String keys = ApiUtil.getCommaSeparatedStringFromList(quickKeys);
        request.addQueryParameter(PARAM_QUICK_KEYS, keys);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);
        return doRequestJson(request);
    }

    public Result addToList(String listKey, String quickKey) {
        List<String> keys = new ArrayList<String>();
        keys.add(quickKey);
        return addToList(listKey, keys);
    }

    public Result removeFromList(String listKey, List<String> quickKeys) {
        ApiObject apiObject = new ApiObject("meta", "remove_from_list.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        // add comma separated key list query param
        String keys = ApiUtil.getCommaSeparatedStringFromList(quickKeys);
        request.addQueryParameter(PARAM_QUICK_KEYS, keys);
        // add list key query param
        request.addQueryParameter(PARAM_LIST_KEY, listKey);
        return doRequestJson(request);
    }

    public Result removeFromList(String listKey, String quickKey) {
        List<String> keys = new ArrayList<String>();
        keys.add(quickKey);
        return removeFromList(listKey, keys);
    }

    public Result delete(List<String> quickKeys) {
        ApiObject apiObject = new ApiObject("meta", "delete.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        // add comma separated key list query param
        String keys = ApiUtil.getCommaSeparatedStringFromList(quickKeys);
        request.addQueryParameter(PARAM_QUICK_KEYS, keys);
        return doRequestJson(request);
    }

    public Result delete(String quickKey) {
        List<String> keys = new ArrayList<String>();
        keys.add(quickKey);
        return delete(keys);
    }

    public Result get(List<String> quickKeys) {
        ApiObject apiObject = new ApiObject("meta", "get.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        // add comma separated key list query param
        String keys = ApiUtil.getCommaSeparatedStringFromList(quickKeys);
        request.addQueryParameter(PARAM_QUICK_KEYS, keys);
        return doRequestJson(request);
    }

    public Result get(String quickKey) {
        List<String> keys = new ArrayList<String>();
        keys.add(quickKey);
        return get(keys);
    }

    public Result getLinks(List<String> quickKeys, String linkType) {
        ApiObject apiObject = new ApiObject("meta", "get_links.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        // add link type query param
        request.addQueryParameter(PARAM_LINK_TYPE, linkType);
        return doRequestJson(request);
    }

    public Result getLinks(List<String> quickKeys) {
        return getLinks(quickKeys, null);
    }

    public Result getLinks(String quickKey, String linkType) {
        List<String> keys = new ArrayList<String>();
        keys.add(quickKey);
        return getLinks(keys, linkType);
    }

    public Result getLinks(String quickKey) {
        return getLinks(quickKey, null);
    }

    public Result query(QueryBuilder queryBuilder) {
        ApiObject apiObject = new ApiObject("meta", "query.php");
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);

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
        Request request = new Request(mHostObject, apiObject, mInstructionsObject, mVersionObject);
        
        // add meta K,V pairs
        for (String key : metaKeyValuePairs.keySet()) {
            request.addQueryParameter(PARAM_META_PREFIX + key, metaKeyValuePairs.get(key));
        }

        // add quickkey param to query parameters
        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        
        return doRequestJson(request);
    }

    /**
     * calls do request from super after adding query parameter response_format=json
     * @param request - the Request to use in the api call
     * @return a Result
     */
    private Result doRequestJson(Request request) {
        request.addQueryParameter(PARAM_RESPONSE_FORMAT, "json");
        return doRequest(request);
    }

}
