package com.mediafire.sdk.api.responses.user;


import com.mediafire.sdk.api.responses.ApiResponse;

public class GetInfoResponse extends ApiResponse {

    private UserInfo user_info;

    public UserInfo getUserInfo() {
        if (user_info == null) {
            user_info = new UserInfo();
        }
        return user_info;
    }

    public class UserInfo {
        private String first_name;
        private String last_name;
        private String display_name;
        private String birth_date;
        private String max_instant_upload_size;
        private String storage_limit;
        private String storage_limit_exceeded;
        private String used_storage_size;
        private String email;
        private String gender;
        private String location;
        private String website;
        private String premium;
        private String options;
        private String ekey;
        private FacebookInfo facebook;
        private TwitterInfo twitter;


        public FacebookInfo getFacebookInfo() {
            if (facebook == null) {
                facebook = new FacebookInfo();
            }
            return facebook;
        }

        public TwitterInfo getTwitterInfo() {
            if (twitter == null) {
                twitter = new TwitterInfo();
            }
            return twitter;
        }

        public String getEKey() {
            if (ekey == null) {
                ekey = "";
            }
            return ekey;
        }

        public String getFirstName() {
            if (first_name == null) {
                first_name = "";
            }
            return first_name;
        }

        public String getLastName() {
            if (last_name == null) {
                last_name = "";
            }
            return last_name;
        }

        public String getDisplayName() {
            if (display_name == null) {
                display_name = "";
            }
            return display_name;
        }

        public String getBirthDate() {
            if (birth_date == null) {
                birth_date = "";
            }
            return birth_date;
        }

        public String getMaxInstantUploadSize() {
            if (max_instant_upload_size == null) {
                max_instant_upload_size = "";
            }
            return max_instant_upload_size;
        }

        public String getStorageLimit() {
            if (storage_limit == null) {
                storage_limit = "";
            }
            return storage_limit;
        }

        public String getUsedStorageSize() {
            if (used_storage_size == null) {
                used_storage_size = "";
            }
            return used_storage_size;
        }

        public String getStorageLimitExceeded() {
            if (storage_limit_exceeded == null) {
                storage_limit_exceeded = "";
            }
            return storage_limit_exceeded;
        }

        public String getEmail() {
            if (email == null) {
                email = "";
            }
            return email;
        }

        public String getGender() {
            if (gender == null) {
                gender = "";
            }
            return gender;
        }

        public String getLocation() {
            if (location == null) {
                location = "";
            }
            return location;
        }

        public String getWebsite() {
            if (website == null) {
                website = "";
            }
            return website;
        }

        public String getPremium() {
            if (premium == null) {
                premium = "";
            }
            return premium;
        }

        public String getOptions() {
            if (options == null) {
                options = "";
            }
            return options;
        }
    }

    public class FacebookInfo {
        private String facebook_id;
        private String date_created;
        private String facebook_url;
        private String email;
        private String firstname;
        private String lastname;
        private String hometown;
        private String location;
        private String i18n;
        private String timezone;
        private String linked;

        public String getFacebookId() {
            if (facebook_id == null) {
                facebook_id = "";
            }
            return facebook_id;
        }

        public String getDateCreated() {
            if (date_created == null) {
                date_created = "";
            }
            return date_created;
        }

        public String getFacebookUrl() {
            if (facebook_url == null) {
                facebook_url = "";
            }
            return facebook_url;
        }

        public String getEmail() {
            if (email == null) {
                email = "";
            }
            return email;
        }

        public String getFirstName() {
            if (firstname == null) {
                firstname = "";
            }
            return firstname;
        }

        public String getLastName() {
            if (lastname == null) {
                lastname = "";
            }
            return lastname;
        }

        public String getLocation() {
            if (location == null) {
                location = "";
            }
            return location;
        }

        public String getHometown() {
            if (hometown == null) {
                hometown = "";
            }
            return hometown;
        }

        public String geti18n() {
            if (i18n == null) {
                i18n = "";
            }
            return i18n;
        }

        public String getTimeZone() {
            if (timezone == null) {
                timezone = "";
            }
            return timezone;
        }

        public boolean isLinked() {
            if (linked == null) {
                linked = "no";
            }
            return "yes".equals(linked);
        }
    }

    public class TwitterInfo {
        private String twitter_id;
        private String date_created;
        private String name;
        private String i18n;
        private String linked;

        public String getTwitterId() {
            if (twitter_id == null) {
                twitter_id = "";
            }
            return twitter_id;
        }

        public String getDateCreated() {
            if (date_created == null) {
                date_created = "";
            }
            return date_created;
        }

        public String getName() {
            if (name == null) {
                name = "";
            }
            return name;
        }

        public String geti18n() {
            if (i18n == null) {
                i18n = "";
            }
            return i18n;
        }

        public boolean isLinked() {
            return "yes".equals(linked);
        }
    }
}
