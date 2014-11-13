package com.mediafire.sdk.conversion.clients;

import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.client_helpers.ClientHelperActionToken;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;


/**
 * Created by jondh on 11/5/14.
 */
public class ConversionServerClient {

    private static final String PARAM_DOC_TYPE = "doc_type";
    private static final String PARAM_SIZE_ID = "size_id";
    private static final String PARAM_QUICK_KEY = "quickkey";
    private static final String PARAM_CONVERSION_ONLY = "request_conversion_only";
    private static final String PARAM_BAD_HASH = "0000";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_OUTPUT = "output";
    private static final String PARAM_METADATA = "metadata";

    private final ApiClient imageClient;

    public ConversionServerClient(HttpWorkerInterface httpWorkerInterface, ActionTokenManagerInterface actionTokenManagerInterface) {
        ClientHelperActionToken clientHelperUploadActionToken = new ClientHelperActionToken("image", actionTokenManagerInterface);
        imageClient = new ApiClient(clientHelperUploadActionToken, httpWorkerInterface);
    }

    public Result imageConversion(ImageParams params) {
        String quickKey = params.getQuickkey();
        String hash = params.getHash();
        Request request = makeBaseRequest(quickKey, hash);

        String sizeId = params.getSizeId();
        String conversionOnly = params.getRequestConversionOnly();
        request.addQueryParameter(PARAM_DOC_TYPE, "i");
        request.addQueryParameter(PARAM_SIZE_ID, sizeId);
        request.addQueryParameter(PARAM_CONVERSION_ONLY, conversionOnly);
        return imageClient.doRequest(request);
    }

    public Result documentConversion(DocumentParams params) {
        String quickKey = params.getQuickkey();
        String hash = params.getHash();
        
        Request request = makeBaseRequest(quickKey, hash);

        String page = params.getPage();
        String output = params.getOutput();
        String sizeId = params.getSizeId();
        String metadata = params.getMetaData();
        String conversionOnly = params.getRequestConversionOnly();
        request.addQueryParameter(PARAM_DOC_TYPE, "d");
        request.addQueryParameter(PARAM_PAGE, page);
        request.addQueryParameter(PARAM_OUTPUT, output);
        request.addQueryParameter(PARAM_SIZE_ID, sizeId);
        request.addQueryParameter(PARAM_METADATA, metadata);
        request.addQueryParameter(PARAM_CONVERSION_ONLY, conversionOnly);

        return imageClient.doRequest(request);
    }

    private Request makeBaseRequest(String quickKey, String hash) {
        Request.Builder builder = new Request.Builder();
        builder.scheme("https").fullDomain("www.mediafire.com").path("conversion_server.php").httpMethod("get").postQuery(false);

        Request request = builder.build();
        String shortHashParam = makeShortHash(hash);
        request.addQueryParameter(shortHashParam);
        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        return request;
    }

    private String makeShortHash(String hash) {
        if (hash == null || hash.isEmpty()) {
            return PARAM_BAD_HASH;
        }

        if (hash.length() < 4) {
            return PARAM_BAD_HASH;
        }

        return hash.substring(0, 4);
    }

    public static class DocumentParams extends BaseParams {
        private String mPage;
        private String mOutput;
        private String mSizeId;
        private String mMetaData;

        public DocumentParams(String quickKey, String hash, String page) {
            super(quickKey, hash);
            mPage = page;
        }

        public DocumentParams(String quickKey, String hash) {
            this(quickKey, hash, "initial");
        }

        public DocumentParams output(Output output) {
            if (output == null) {
                return this;
            }

            mOutput = output.getValue();
            return this;
        }

        public DocumentParams sizeId(int sizeId) {
            if (sizeId < 0 || sizeId > 2) {
                return this;
            }
            
            mSizeId = String.valueOf(sizeId);
            return this;
        }
        
        public DocumentParams metaData(boolean wantMetaData) {
            if (wantMetaData) {
                mMetaData = "1";
            } 
            
            return this;
        }

        public String getPage() {
            return mPage;
        }

        public String getOutput() {
            return mOutput;
        }

        public String getSizeId() {
            return mSizeId;
        }

        public String getMetaData() {
            return mMetaData;
        }

        public enum Output {
            PDF("pdf"),
            IMAGE("img"),
            FLASH("swf");

            private String mValue;

            private Output(String value) {
                mValue = value;
            }

            public String getValue() {
                return mValue;
            }
        }
    }

    public static class ImageParams extends BaseParams {

        private String mSizeId;

        public ImageParams(String quickkey, String hash, String sizeId) {
            super(quickkey, hash);
            mSizeId = sizeId;
        }

        public ImageParams(String quickKey, String hash) {
            this(quickKey, hash, "6");
        }

        public String getSizeId() {
            return mSizeId;
        }
    }

    private static class BaseParams {
        private final String mQuickkey;
        private final String mHash;
        private String mRequestConversionOnly;

        public BaseParams(String quickkey, String hash) {
            mQuickkey = quickkey;
            mHash = hash;
        }

        public BaseParams requestConversionOnly(boolean value) {
            mRequestConversionOnly = value ? "1" : null;
            return this;
        }

        public final String getQuickkey() {
            return mQuickkey;
        }

        public String getHash() {
            return mHash;
        }

        public String getRequestConversionOnly() {
            return mRequestConversionOnly;
        }
    }
}
