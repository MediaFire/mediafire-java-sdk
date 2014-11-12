package com.mediafire.sdk.api.responses.meta;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris Najar on 10/2/2014.
 */
public class QueryResponse extends ApiResponse {
    private Items items;

    public Items getItems() {
        if (items == null) {
            items = new Items();
        }
        return items;
    }

    public class Items {
        List<Item> item;

        public List<Item> getItemsList() {
            if (item == null) {
                item = new LinkedList<Item>();
            }
            return item;
        }

        public class Item {
            private String quickkey;
            private String filename;
            private String created;
            private String size;
            private String mimetype;
            private String media_type;
            private String manufacturer;
            private String model;
            private String orientation;
            private String software;
            private String date_and_time;
            private String compression;
            private String x_resolution;
            private String y_resolution;
            private String width;
            private String height;

            public String getQuickkey() {
                return quickkey;
            }

            public String getFileName() {
                return filename;
            }

            public String getCreated() {
                return created;
            }

            public long getSize() {
                if (size == null) {
                    size = "0";
                }
                return Long.parseLong(size);
            }

            public String getMimeType() {
                return mimetype;
            }

            public String getMediaType() {
                return media_type;
            }

            public String getManufacturer() {
                return manufacturer;
            }

            public String getModel() {
                return model;
            }

            public String getOrientation() {
                return orientation;
            }

            public String getSoftware() {
                return software;
            }

            public String getDateAndTime() {
                return date_and_time;
            }

            public String getCompression() {
                return compression;
            }

            public int getXResolution() {
                if (x_resolution == null) {
                    x_resolution = "0";
                }
                return Integer.parseInt(x_resolution);
            }

            public int getYResolution() {
                if (y_resolution == null) {
                    y_resolution = "0";
                }
                return Integer.parseInt(y_resolution);
            }

            public int getWidth() {
                if (width == null) {
                    width = "0";
                }
                return Integer.parseInt(width);
            }

            public int getHeight() {
                if (height == null) {
                    height = "0";
                }
                return Integer.parseInt(height);
            }
        }
    }
}
