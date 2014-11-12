package com.mediafire.sdk.api.responses.system;

import com.mediafire.sdk.api.responses.ApiResponse;

public class GetInfoResponse extends ApiResponse {

    private TermsOfService terms_of_service;


    public TermsOfService getTermsOfService() {
        return terms_of_service;
    }

    public class TermsOfService {
        private String terms;
        private String date;
        private String revision;

        public String getTerms() {
            return this.terms;
        }

        public String getDate() {
            return this.date;
        }

        public String getRevision() {
            return this.revision;
        }
    }
}
