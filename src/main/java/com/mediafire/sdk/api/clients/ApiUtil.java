package com.mediafire.sdk.api.clients;

import java.util.LinkedList;
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
    public static String getCommaSeparatedString(List<String> values) {
        String commaSeparatedValues = "";

        for (String value : values) {
            commaSeparatedValues += value;
            commaSeparatedValues += ",";
        }

        commaSeparatedValues = commaSeparatedValues.substring(0, commaSeparatedValues.length() - 1);


        return commaSeparatedValues;
    }

    public static String getCommaSeparatedString(String... values) {
        List<String> stringList = new LinkedList<String>();

        for (String value : values) {
            stringList.add(value);
        }

        return getCommaSeparatedString(stringList);
    }
}
