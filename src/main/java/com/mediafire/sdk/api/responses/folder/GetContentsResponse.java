package com.mediafire.sdk.api.responses.folder;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

public class GetContentsResponse extends ApiResponse {
    public FolderContents folder_content;

    public FolderContents getFolderContents() {
        if (this.folder_content == null) {
            this.folder_content = new FolderContents();
        }
        return this.folder_content;
    }

    public class FolderContents {
        public String chunk_size;
        public String content_type;
        public String chunk_number;
        public List<Folder> folders;
        public List<File> files;

        public List<Folder> getFolders() {
            if (this.folders == null) {
                this.folders = new LinkedList<Folder>();
            }
            return this.folders;
        }

        public List<File> getFiles() {
            if (this.files == null) {
                this.files = new LinkedList<File>();
            }
            return this.files;
        }

        public int getChunkSize() {
            if (this.chunk_size == null) {
                this.chunk_size = "0";
            }
            return Integer.valueOf(chunk_size);
        }

        public String getContentType() {
            if (this.content_type == null) {
                this.content_type = "";
            }
            return content_type;
        }

        public int getChunkNumber() {
            if (this.chunk_number == null) {
                this.chunk_number = "0";
            }
            return Integer.valueOf(this.chunk_number);
        }
    }

    public class Folder {
        private String folderkey;
        private String name;
        private String description;
        private String tags;
        private String privacy;
        private String created;
        private String flag;
        private String permissions;
        private String size;
        private String revision;
        private String shared;
        private String dropbox_enabled;
        private String folder_count;
        private String file_count;
        private String shared_by_user;

        public int getRevision() {
            if (this.revision == null) {
                this.revision = "0";
            }
            return Integer.valueOf(this.revision);
        }

        public boolean isPublic() {
            if (this.privacy == null) {
                this.privacy = "public";
            }
            return "public".equalsIgnoreCase(this.privacy);
        }

        public boolean isSharedFromOther() {
            if (this.shared == null) {
                this.shared = "yes";
            }
            return "yes".equalsIgnoreCase(this.shared);
        }

        public boolean isDropboxEnabled() {
            if (this.dropbox_enabled == null) {
                this.dropbox_enabled = "no";
            }
            return "yes".equalsIgnoreCase(this.dropbox_enabled);
        }

        public String getDescription() {
            if (this.description == null) {
                this.description = "";
            }
            return this.description;
        }

        public String getTags() {
            if (this.tags == null) {
                this.tags = "";
            }
            return this.tags;
        }

        public int getFolderCount() {
            if (this.folder_count == null) {
                this.folder_count = "0";
            }
            return Integer.valueOf(this.folder_count);
        }

        public int getFileCount() {
            if (this.file_count == null) {
                this.file_count = "0";
            }
            return Integer.valueOf(this.file_count);
        }

        public int getPermissions() {
            if (this.permissions == null) {
                this.permissions = "0";
            }
            return Integer.valueOf(this.permissions);
        }

        public boolean isSharedByUser() {
            if (this.shared_by_user == null) {
                this.shared_by_user = "0";
            }
            return !"0".equalsIgnoreCase(this.shared_by_user);
        }

        public String getFolderkey() {
            if (this.folderkey == null) {
                this.folderkey = "";
            }
            return this.folderkey;
        }

        public String getFolderName() {
            if (this.name == null) {
                this.name = "";
            }
            return this.name;
        }

        public String getCreated() {
            if (this.created == null) {
                this.created = "";
            }
            return this.created;
        }

        public long getSize() {
            if (this.size == null) {
                this.size = "0";
            }
            return Integer.valueOf(this.size);
        }

        public int getFlag() {
            if (this.flag == null) {
                this.flag = "0";
            }
            return Integer.valueOf(this.flag);
        }
    }

    public class File {
        private String quickkey;
        private String filename;
        private String description;
        private String size;
        private String privacy;
        private String created;
        private String filetype;
        private String mimetype;
        private String flag;
        private String permissions;
        private String hash;
        private String downloads;
        private String views;
        private String shared_by_user;
        private String password_protected;

        public int getDownloads() {
            if (this.downloads == null) {
                this.downloads = "0";
            }
            return Integer.valueOf(this.downloads);
        }

        public int getViews() {
            if (this.views == null) {
                this.views = "0";
            }
            return Integer.valueOf(this.views);
        }

        public String getFileType() {
            if (this.filetype == null) {
                this.filetype = "";
            }
            return this.filetype;
        }

        public String getMimeType() {
            if (this.mimetype == null) {
                this.mimetype = "";
            }
            return this.mimetype;
        }

        public String getHash() {
            if (this.hash == null) {
                this.hash = "";
            }
            return this.hash;
        }

        public boolean isPasswordProtected() {
            return "yes".equalsIgnoreCase(this.privacy);
        }

        public boolean isPublic() {
            return "public".equalsIgnoreCase(this.privacy);
        }

        public String getDescription() {
            if (this.description == null) {
                this.description = "";
            }
            return this.description;
        }

        public int getPermissions() {
            if (this.permissions == null) {
                this.permissions = "0";
            }
            return Integer.valueOf(this.permissions);
        }

        public boolean isSharedByUser() {
            return "1".equalsIgnoreCase(this.shared_by_user);
        }

        public String getQuickKey() {
            if (this.quickkey == null) {
                this.quickkey = "";
            }
            return this.quickkey;
        }

        public String getFilename() {
            if (this.filename == null) {
                this.filename = "";
            }
            return this.filename;
        }

        public String getCreated() {
            if (this.created == null) {
                this.created = "";
            }
            return this.created;
        }

        public long getSize() {
            if (this.size == null) {
                this.size = "0";
            }
            return Long.valueOf(this.size);
        }

        public int getFlag() {
            if (this.flag == null) {
                this.flag = "0";
            }
            return Integer.valueOf(this.flag);
        }
    }
}
