package com.mediafire.sdk;

public interface MediaFireSessionStore {
    /**
     * gets a MediaFireSessionTokenV2.
     * @return null if unavailable
     */
    MediaFireSessionTokenV2 getMediaFireSessionTokenV2();

    /**
     * puts a MediaFireSessionTokenV2 in the store
     * @param mediaFireSessionTokenV2 true if stored
     */
    boolean putMediaFireSessionTokenV2(MediaFireSessionTokenV2 mediaFireSessionTokenV2);

    /**
     * gets the count of MediaFireSessionTokenV2 available
     * @return
     */
    int getMediaFireSessionTokenV2Count();

    /**
     * gets whether or not any MediaFireSessionTokenV2 are available
     * @return
     */
    boolean isMediaFireSessionTokenV2Available();

    /**
     * gets a MediaFireActionToken.
     * @param type
     * @return null if unavailable
     */
    MediaFireActionToken getMediaFireActionToken(int type);

    /**
     * puts a MediaFireActionToken in the store
     * @param mediaFireActionToken true if stored
     */
    boolean putMediaFireActionToken(MediaFireActionToken mediaFireActionToken);

    /**
     * gets whether or not a MediaFireActionToken of the type is available
     * @param type
     * @return
     */
    boolean isMediaFireActionTokenAvailable(int type);

    /**
     * clears the tokens in the store
     */
    void clear();
}
