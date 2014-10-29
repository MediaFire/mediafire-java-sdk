package com.mediafire.sdk.clients;

import java.util.List;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class ApiUtil {
    /**
     * accepts a List of keys and creates a comma separated list
     * @param values - the list of keys
     * @return a comma separated list
     */
    public static String getCommaSeparatedStringFromList(List<String> values) {
        String commaSeparatedValues = "";

        for (String value : values) {
            commaSeparatedValues += value;
            commaSeparatedValues += ",";
        }

        commaSeparatedValues = commaSeparatedValues.substring(0, commaSeparatedValues.length() - 1);


        return commaSeparatedValues;
    }
}
