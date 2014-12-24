package com.mediafire.sdk.api.responses.upload;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Chris on 12/23/2014.
*/
public class ResumableBitmap {
    private String count;
    private List<String> words;

    public int getCount() {
        if (count == null || count.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(count);
    }

    public List<Integer> getWords() {
        if (words == null || words.size() == 0) {
            return new ArrayList<Integer>();
        }
        return convert(words);
    }

    private List<Integer> convert(List<String> words) {
        List<Integer> ret = new ArrayList<Integer>();
        for (String str : words) {
            ret.add(Integer.parseInt(str));
        }

        if (ret.size() == words.size()) {
            return ret;
        } else {
            return new ArrayList<Integer>();
        }
    }
}
