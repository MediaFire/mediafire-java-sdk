package com.mediafire.sdk.test_utility;

import com.mediafire.sdk.http.Request;

/**
 * Created by Chris on 11/10/2014.
 */
public class RequestObjectsForTesting {

    public static Request getRequestSchemePath(boolean postQuery, String httpMethod, int numParams) {
        Request.Builder builder = new Request.Builder();
        builder.scheme("scheme").path("some/path/to/a/resource.php").postQuery(postQuery).httpMethod(httpMethod);

        Request request = builder.build();

        for (int i = 0; i < numParams; i++) {
            request.addQueryParameter("key" + i, "value" + i);
        }

        return request;
    }

    public static Request getRequestSchemeDomainPath(boolean postQuery, String httpMethod, int numParams) {
        Request.Builder builder = new Request.Builder();
        builder.scheme("scheme").fullDomain("subdomain.domain.top-level-domain").path("some/path/to/a/resource.php").postQuery(postQuery).httpMethod(httpMethod);

        Request request = builder.build();

        for (int i = 0; i < numParams; i++) {
            request.addQueryParameter("key" + i, "value" + i);
        }

        return request;
    }

    public static Request getRequestSchemeDomain(boolean postQuery, String httpMethod, int numParams) {
        Request.Builder builder = new Request.Builder();
        builder.scheme("scheme").fullDomain("subdomain.domain.top-level-domain").postQuery(postQuery).httpMethod(httpMethod);

        Request request = builder.build();

        for (int i = 0; i < numParams; i++) {
            request.addQueryParameter("key" + i, "value" + i);
        }

        return request;
    }

    public static Request getRequestScheme(boolean postQuery, String httpMethod, int numParams) {
        Request.Builder builder = new Request.Builder();
        builder.scheme("scheme").postQuery(postQuery).httpMethod(httpMethod);

        Request request = builder.build();

        for (int i = 0; i < numParams; i++) {
            request.addQueryParameter("key" + i, "value" + i);
        }

        return request;
    }

    public static Request getRequestNoSchemeNoDomainNoPath(boolean postQuery, String httpMethod, int numParams) {
        Request.Builder builder = new Request.Builder();
        builder.postQuery(postQuery).httpMethod(httpMethod);

        Request request = builder.build();

        for (int i = 0; i < numParams; i++) {
            request.addQueryParameter("key" + i, "value" + i);
        }

        return request;
    }
}
