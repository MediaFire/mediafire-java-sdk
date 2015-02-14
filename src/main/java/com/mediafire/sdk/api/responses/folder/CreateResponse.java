package com.mediafire.sdk.api.responses.folder;

import com.mediafire.sdk.api.responses.ApiResponse;

public class CreateResponse extends ApiResponse {
    private String folder_key;
    private String upload_key;
    private String device_revision;
    private String folderkey;
    private String name;
    private String description;
    private String tags;
    private String created;
    private String privacy;
    private String file_count;
    private String folder_count;
    private String revision;
    private String dropbox_enabled;
    private String flag;

    public String getFolderKey() {
        if (this.folder_key == null || folder_key.isEmpty()) {
            this.folder_key = "myfiles";
        }
        return this.folder_key;
    }

    public String getUploadKey() {
        if (this.upload_key == null) {
            this.upload_key = "";
        }
        return this.upload_key;
    }

    public int getDeviceRevision() {
        if (this.device_revision == null) {
            this.device_revision = "0";
        }
        return Integer.valueOf(this.device_revision);
    }

    public String getFolderkey() {
        if (folderkey == null || folderkey.isEmpty()) {
            folderkey = "myfiles";
        }
        return folderkey;
    }

    public String getName() {
        if (name == null || name.isEmpty()) {
            name = "";
        }
        return name;
    }

    public String getDescription() {
        if (description == null || description.isEmpty()) {
            description = "";
        }
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getCreated() {
        if (created == null || created.isEmpty()) {
            created = "";
        }
        return created;
    }

    public boolean isPrivate() {
        if (privacy == null) {
            return false;
        }

        if ("private".equals(privacy)) {
            return true;
        }

        return false;
    }

    public int getFileCount() {
        if (file_count == null || file_count.isEmpty()) {
            file_count = "0";
        }
        return Integer.valueOf(file_count);
    }

    public int getFolderCount() {
        if (folder_count == null || folder_count.isEmpty()) {
            folder_count = "0";
        }
        return Integer.valueOf(folder_count);
    }

    public int getRevision() {
        if (revision == null || revision.isEmpty()) {
            revision = "0";
        }
        return Integer.valueOf(revision);
    }

    public boolean isDropboxEnabled() {
        return dropbox_enabled == null || "yes".equalsIgnoreCase(dropbox_enabled);
    }

    public String getFlag() {
        return flag;
    }

}
