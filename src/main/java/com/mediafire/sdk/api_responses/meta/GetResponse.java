package com.mediafire.sdk.api_responses.meta;

import com.mediafire.sdk.api_responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris Najar on 10/2/2014.
 */
public class GetResponse extends ApiResponse {

    private List<Item> items;

    public List<Item> getItems() {
        if (items == null) {
            items = new LinkedList<Item>();
        }
        return items;
    }

    public class Item {
        private String quickkey;
        private String filename;
        private String created;
        private String size;
        private String mimetype;
        private String media_type;
        private List<MetaList> lists;

        public String getQuickKey() {
            return quickkey;
        }

        public String getFileName() {
            return filename;
        }

        public String getCreated() {
            return created;
        }

        public String getSize() {
            return size;
        }

        public String getMimeType() {
            return mimetype;
        }

        public String getMediaType() {
            return media_type;
        }

        public List<MetaList> getLists() {
            if (lists == null) {
                lists = new LinkedList<MetaList>();
            }
            return lists;
        }

        public class MetaList {
            List<MetaSubList> list;

            public List<MetaSubList> getMetaSubList() {
                if (list == null) {
                    list = new LinkedList<MetaSubList>();
                }

                return list;
            }

            public class MetaSubList {
                private String list_name;
                private String list_key;
                private String list_type;

                public String getListName() {
                    return list_name;
                }

                public String getListKey() {
                    return list_key;
                }

                public String getListType() {
                    return list_type;
                }
            }
        }
    }
}
