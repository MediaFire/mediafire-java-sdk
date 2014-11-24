package com.mediafire.sdk.api.clients.meta;

import com.mediafire.sdk.api.clients.ApiUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by Chris Najar on 10/29/2014.
*/
public class QueryBuilder {
    private final int mChunk;
    private final String mOrderDirection;
    private final String mOrderBy;
    private final String mReturnData;
    private final Map<String, String> mMetaDataFilters;
    
    public QueryBuilder(Builder builder) {
        mChunk = builder.mChunk;
        mOrderDirection = builder.mOrderDirection;
        mOrderBy = builder.mOrderBy;
        mReturnData = builder.mReturnData;
        mMetaDataFilters = builder.mMetaDataFilters;
    }

    public int getChunk() {
        return mChunk;
    }

    public String getOrderDirection() {
        return mOrderDirection;
    }

    public String getOrderBy() {
        return mOrderBy;
    }

    public String getReturnData() {
        return mReturnData;
    }

    public Map<String, String> getMetaDataFilters() {
        return mMetaDataFilters;
    }

    public static class Builder {
        private static final int DEFAULT_CHUNK = 1;
        private static final String DEFAULT_ORDER_DIRECTION = "asc";
        private static final String DEFAULT_ORDER_BY = "created";
        private static final String DEFAULT_RETURN_DATA = "all";
        private static final Map<String, String> DEFAULT_META_FILTER = new HashMap<String, String>();

        private int mChunk = DEFAULT_CHUNK;
        private String mOrderDirection = DEFAULT_ORDER_DIRECTION;
        private String mOrderBy = DEFAULT_ORDER_BY;
        private String mReturnData = DEFAULT_RETURN_DATA;
        private final Map<String, String> mMetaDataFilters = DEFAULT_META_FILTER;

        public Builder() {}

        /**
         * A user-defined query used to filter meta-data with.
         * The parameter name should be a valid meta-data element name.
         * The filter argument should specify on or more conditions to match.
         * Multiple instances of this parameter can be passed in a single API call.
         * If not passed all files are returned.
         *
         * [xxx] = [condition 1], [condition 2], ..., [condition n]
         *
         * [condition] = [value](text or numeric)
         * or ! [value] (text or numeric)
         * or > [value] (numeric)
         * or < [value] (numeric)
         * or [value_from] ~ [value_from] (numeric)
         *
         * Examples of individual filters:
         * genre = pop, rock, jazz (Returns media items with genre equals to pop, rock or jazz)
         * manufacturer = ! Sony (Returns media items with any manufacturer but Sony)
         * x_resolution = < 144, > 288 (Returns media items with x_resolution less than 144 or greater than 288)
         * y_resolution = 36 ~ 288 (Returns media items with x_resolution ranging from 36 to 288)
         * release_date = 1396569600, > 1388534400 (Returns media items with release_date equals to 2010-04-04 or release_date greater than 2014-01-01. Notice that release_date is a timestamp in this case)
         *
         * Example of a full query:
         * meta_media_type=image
         * &meta_date_and_time=2014-01-01
         * &meta_gps_location_x=-90.021654~-91.316542
         * &meta_gps_location_y=30.0005~30.00056
         * &meta_shared_with=John%20Smith,Bob,Jenifer
         * &meta_album_name=!Paris Vacation
         * @param metaName the name of the meta tag (do not add "meta_" prefix)
         * @param metaValue the value of the meta tag
         */
        public Builder addMetaFilter(String metaName, String metaValue) {
            if (metaName == null || metaName.isEmpty()) {
                return this;
            }

            if (metaValue == null || metaValue.isEmpty()) {
                return this;
            }

            mMetaDataFilters.put(metaName, metaValue);
            return this;
        }

        public Builder setReturnData(List<String> elementsToReturn) {
            if (elementsToReturn == null || elementsToReturn.isEmpty()) {
                return this;
            }

            mReturnData = ApiUtil.getCommaSeparatedString(elementsToReturn);
            return this;
        }

        public Builder setOrderBy(List<String> metaDataList) {
            if (metaDataList == null || metaDataList.isEmpty()) {
                return this;
            }

            mOrderBy = ApiUtil.getCommaSeparatedString(metaDataList);
            return this;
        }

        public Builder setOrderBy(String metaData) {
            if (metaData == null) {
                return this;
            }
            List<String> metaDataList = new ArrayList<String>();
            metaDataList.add(metaData);
            setOrderBy(metaDataList);
            return this;
        }

        public Builder setOrderDirection(boolean ascending) {
            if (ascending) {
                mOrderDirection = "asc";
            } else {
                mOrderDirection = "desc";
            }
            return this;
        }

        public Builder setChunk(int chunk) {
            if (chunk < 1) {
                return this;
            }

            mChunk = chunk;
            return this;
        }
        
        public QueryBuilder build() {
            return new QueryBuilder(this);
        }
    }
}
