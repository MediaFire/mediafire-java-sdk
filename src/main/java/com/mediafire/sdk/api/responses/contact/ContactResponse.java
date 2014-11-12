package com.mediafire.sdk.api.responses.contact;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.Arrays;
import java.util.List;

public class ContactResponse extends ApiResponse {

    private int count;
    private int epoch;
    private int revision;
    private SingleContactResponse[] contacts;

    public int getCount() {
        return count;
    }

    public int getEpoch() {
        return epoch;
    }

    public int getRevision() {
        return revision;
    }

    public List<SingleContactResponse> getContacts() {
        if (contacts == null) {
            contacts = new SingleContactResponse[0];
        }
        return Arrays.asList(contacts);
    }

    public class SingleContactResponse {

        private String display_name;
        private String first_name;
        private String last_name;
        private String contact_key;
        private String means_of_contact;
        private String contact_type;

        private String avatar;
        private String email;
        private String phone;
        private String birthdate;
        private String location;
        private String gender;
        private String website;
        private String created;

        public String displayName() {
            if (display_name == null) {
                display_name = "";
            }
            return display_name;
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

        public String getAvatar() {
            if (avatar == null) {
                avatar = "";
            }
            return avatar;
        }

        public String getEmail() {
            if (email == null) {
                email = "";
            }
            return email;
        }

        public String getPhone() {
            if (phone == null) {
                phone = "";
            }
            return phone;
        }

        public String getMeansOfContact() {
            if (means_of_contact == null) {
                means_of_contact = "";
            }
            return means_of_contact;
        }

        public String getBirthDate() {
            if (birthdate == null) {
                birthdate = "";
            }
            return birthdate;
        }

        public String getLocation() {
            if (location == null) {
                location = "";
            }
            return location;
        }

        public String getGender() {
            if (gender == null) {
                gender = "";
            }
            return gender;
        }

        public String getWebsite() {
            if (website == null) {
                website = "";
            }
            return website;
        }

        public String getCreatedDate() {
            if (created == null) {
                created = "";
            }
            return created;
        }

        public String getContactType() {
            if (contact_type == null) {
                contact_type = "";
            }
            return contact_type;
        }

        public String getContactKey() {
            if (contact_key == null) {
                contact_key = "";
            }
            return contact_key;
        }
    }
}
