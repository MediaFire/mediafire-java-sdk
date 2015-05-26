MediaFire Java SDK
==================

Quickstart
----------

### Start session
```
// create mediafire object
com.mediafire.sdk.MediaFire mf = new...
// start session
mf.startSessionWithEmail("email@domain.com", "password", null);
```

### Get Folder Content (using FolderApi class)
```
// use convenience class from com.mediafire.sdk.api to make call to api/folder/get_content.php
LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
query.put("response_format", "json");
query.put("content_type", "files");
query.put("chunk_size", 150);
FolderGetContentResponse response = FolderApi.getContent(mf, query, "1.3", FolderGetContentResponse.class);
// do something with response e.g. store info from response to a db
```

### Create API Response Class
Gson is used to parse responses in json. 
Different API versions might have different json responses (different json objects, etc.)
You can create your own response class to use when making an API call
```
public class MyFolderContentResponse extends ApiResponse {
  // your fields
  
  // your getters
}
// use convenience class from com.mediafire.sdk.api to make call to api/folder/get_content.php
LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
query.put("response_format", "json");
query.put("content_type", "files");
query.put("chunk_size", 150);
MyFolderContentResponse response = FolderApi.getContent(mf, query, "1.3", MyFolderContentResponse.class);
// do something with response e.g. store info from response to a db
```

### Get Folder Content (using MediaFire class)
```
LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
query.put("response_format", "json");
query.put("content_type", "files");
query.put("chunk_size", 150);
ApiPostRequest apiPostRequest = new ApiPostRequest("/api/folder/get_content.php", query);
FolderGetContentResponse response = mediaFire.doApiRequest(apiRequest, FolderGetContentResponse.class);
```

Uploading
----------

### Using Uploader
```
MediaFireUploader mfu = new MediaFireUploader(3); // pass the number of concurrent uploads
mfu.schedule(upload1);
mfu.schedule(...);
mfu.resume();
```
