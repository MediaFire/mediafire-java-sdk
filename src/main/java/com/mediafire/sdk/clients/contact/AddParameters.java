package com.mediafire.sdk.clients.contact;

/**
 * Created by jondh on 11/4/14.
 */
public class AddParameters {
    private String mContactType;
    private String mContactKey;
    private String mDisplayName;
    private String mFirstName;
    private String mLastName;
    private String mAvatar;
    private String mSourceUid;
    private String mEmail;
    private String mPhone;
    private String mBirthday;
    private String mLocation;
    private String mGender;
    private String mWebsite;
    private String mGroupId;
    
    public AddParameters(Builder builder) {
        mContactType = builder.mContactType;
        mContactKey = builder.mContactKey;
        mDisplayName = builder.mDisplayName;
        mFirstName = builder.mFirstName;
        mLastName = builder.mLastName;
        mAvatar = builder.mAvatar;
        mSourceUid = builder.mSourceUid;
        mEmail = builder.mEmail;
        mPhone = builder.mPhone;
        mBirthday = builder.mBirthday;
        mLocation = builder.mLocation;
        mGender = builder.mGender;
        mWebsite = builder.mWebsite;
        mGroupId = builder.mGroupId;
    }

    public String getContactType() {
        return mContactType;
    }

    public String getContactKey() {
        return mContactKey;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getSourceUid() {
        return mSourceUid;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getGender() {
        return mGender;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public static class Builder {
        private String mContactType;
        private String mContactKey;
        private String mDisplayName;
        private String mFirstName;
        private String mLastName;
        private String mAvatar;
        private String mSourceUid;
        private String mEmail;
        private String mPhone;
        private String mBirthday;
        private String mLocation;
        private String mGender;
        private String mWebsite;
        private String mGroupId;
    
        public Builder contactType(String contactType) {
            if (contactType == null) {
                return this;
            }
    
            mContactType = contactType;
            return this;
        }
    
        public Builder contactKey(String contactKey, String sourceUid) {
            if (contactKey == null || sourceUid == null) {
                return this;
            }
    
            mContactKey = contactKey;
            mSourceUid = sourceUid;
            return this;
        }
    
        public Builder displayName(String displayName) {
            if (displayName == null) {
                return this;
            }
    
            mDisplayName = displayName;
            return this;
        }
    
        public Builder firstName(String firstName) {
            if (firstName == null) {
                return this;
            }
    
            mFirstName = firstName;
            return this;
        }
    
        public Builder lastName(String lastName) {
            if (lastName == null) {
                return this;
            }
    
            mLastName = lastName;
            return this;
        }
    
        public Builder avatar(String avatar) {
            if (avatar == null) {
                return this;
            }
    
            mAvatar = avatar;
            return this;
        }
    
        public Builder sourceUid(String sourceUid) {
            if (sourceUid == null) {
                return this;
            }
    
            mSourceUid = sourceUid;
            return this;
        }
    
        public Builder email(String email) {
            if (email == null) {
                return this;
            }
    
            mEmail = email;
            return this;
        }
    
        public Builder phone(String phone) {
            if (phone == null) {
                return this;
            }
    
            mPhone = phone;
            return this;
        }
    
        public Builder birthday(String birthday) {
            if (birthday == null) {
                return this;
            }
    
            mBirthday = birthday;
            return this;
        }
    
        public Builder location(String location) {
            if (location == null) {
                return this;
            }
    
            mLocation = location;
            return this;
        }
    
        public Builder gender(String gender) {
            if (gender == null) {
                return this;
            }
    
            mGender = gender;
            return this;
        }
    
        public Builder website(String website) {
            if (website == null) {
                return this;
            }
    
            mWebsite = website;
            return this;
        }
    
        public Builder groupId(String groupId) {
            if (groupId == null) {
                return this;
            }
    
            mGroupId = groupId;
            return this;
        }
        
        public AddParameters build() {
            return new AddParameters(this);
        }
    }
}
