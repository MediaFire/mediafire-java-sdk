package com.mediafire.sdk.clients.contact;

/**
 * Created by jondh on 11/4/14.
 */
public class AddParameters {
    public String mContactType;
    public String mContactKey;
    public String mDisplayName;
    public String mFirstName;
    public String mLastName;
    public String mAvatar;
    public String mSourceUid;
    public String mEmail;
    public String mPhone;
    public String mBirthday;
    public String mLocation;
    public String mGender;
    public String mWebsite;
    public String mGroupId;

    public AddParameters contactType(String contactType){
        if(contactType == null) {
            return this;
        }

        mContactType = contactType;
        return this;
    }

    public AddParameters contactKey(String contactKey, String sourceUid){
        if(contactKey == null || sourceUid == null) {
            return this;
        }

        mContactKey = contactKey;
        mSourceUid = sourceUid;
        return this;
    }

    public AddParameters displayName(String displayName){
        if(displayName == null) {
            return this;
        }

        mDisplayName = displayName;
        return this;
    }

    public AddParameters firstName(String firstName){
        if(firstName == null) {
            return this;
        }

        mFirstName = firstName;
        return this;
    }

    public AddParameters lastName(String lastName){
        if(lastName == null) {
            return this;
        }

        mLastName = lastName;
        return this;
    }

    public AddParameters avatar(String avatar){
        if(avatar == null) {
            return this;
        }

        mAvatar = avatar;
        return this;
    }

    public AddParameters sourceUid(String sourceUid){
        if(sourceUid == null) {
            return this;
        }

        mSourceUid = sourceUid;
        return this;
    }

    public AddParameters email(String email){
        if(email == null) {
            return this;
        }

        mEmail = email;
        return this;
    }

    public AddParameters phone(String phone){
        if(phone == null) {
            return this;
        }

        mPhone = phone;
        return this;
    }

    public AddParameters birthday(String birthday){
        if(birthday == null) {
            return this;
        }

        mBirthday = birthday;
        return this;
    }

    public AddParameters location(String location){
        if(location == null) {
            return this;
        }

        mLocation = location;
        return this;
    }

    public AddParameters gender(String gender){
        if(gender == null) {
            return this;
        }

        mGender = gender;
        return this;
    }

    public AddParameters website(String website){
        if(website == null) {
            return this;
        }

        mWebsite = website;
        return this;
    }

    public AddParameters groupId(String groupId){
        if(groupId == null) {
            return this;
        }

        mGroupId = groupId;
        return this;
    }
}
