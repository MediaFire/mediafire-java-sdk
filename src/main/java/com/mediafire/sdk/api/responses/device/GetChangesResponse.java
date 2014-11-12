package com.mediafire.sdk.api.responses.device;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

public class GetChangesResponse extends ApiResponse {
    private ChangedItems updated;
    private ChangedItems deleted;

    private String device_revision;
    private String changes_list_block;


    public int getDeviceRevision() {
        if (device_revision == null) {
            device_revision = "0";
        }
        return Integer.valueOf(device_revision);
    }

    public int getChangesListBlock() {
        if (changes_list_block == null) {
            changes_list_block = "0";
        }
        return Integer.valueOf(changes_list_block);
    }

    public ChangedItems getUpdatedItems() {
        if (updated == null) {
            updated = new ChangedItems();
        }

        return updated;
    }

    public ChangedItems getDeletedItems() {
        if (deleted == null) {
            deleted = new ChangedItems();
        }

        return deleted;
    }

    public class ChangedItems {
        private List<File> files;
        private List<Folder> folders;

        public List<File> getFiles() {
            if (files == null) {
                files = new LinkedList<File>();
            }
            return files;
        }

        public List<Folder> getFolders() {
            if (folders == null) {
                folders = new LinkedList<Folder>();
            }
            return folders;
        }
    }

    public class File {
        private String quickkey;
        private String revision;
        private String created;
        private String filename;
        private String mimetype;
        private String filetype;

        private String parent_folderkey;
        private String parent_foldername;


        public String getFileType() {
            if (filetype == null) {
                this.filetype = "";
            }
            return filetype;
        }

        public String getMimeType() {
            if (mimetype == null) {
                this.mimetype = "";
            }
            return mimetype;
        }

        public String getParentFolderName() {
            if (parent_foldername == null) {
                this.parent_foldername = "";
            }
            return parent_foldername;
        }

        public String getFileName() {
            if (filename == null) {
                this.filename = "";
            }
            return this.filename;
        }

        public String getQuickKey() {
            if (quickkey == null) {
                this.quickkey = "";
            }
            return quickkey;
        }

        public int getRevision() {
            if (revision == null) {
                this.revision = "0";
            }
            return Integer.valueOf(revision);
        }

        public String getCreated() {
            if (created == null) {
                this.created = "";
            }
            return created;
        }

        public String getParentFolderKey() {
            if (parent_folderkey == null || parent_folderkey.isEmpty()) {
                this.parent_folderkey = "myfiles";
            }
            return parent_folderkey;
        }
    }

    public class Folder {
        private String folderkey;
        private String revision;

        private String parent_folderkey;


        public String getFolderKey() {
            if (folderkey == null || folderkey.isEmpty()) {
                this.folderkey = "myfiles";
            }
            return folderkey;
        }

        public int getRevision() {
            if (revision == null) {
                this.revision = "0";
            }
            return Integer.valueOf(revision);
        }

        public String getParentFolderKey() {
            if (parent_folderkey == null || parent_folderkey.isEmpty()) {
                parent_folderkey = "myfiles";
            }
            return parent_folderkey;
        }
    }
}
