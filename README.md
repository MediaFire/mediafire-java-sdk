MediaFire Java SDK
==================

Quickstart
----------

### Create MediaFire Client
```
// create mediafire client
MFClient.Builder builder = new MFClient.Builder(APPLICATION_ID, API_KEY);
builder.apiVersion("1.4"); // current api version, all api calls will use this api version
MediaFireClient client = builder.build();
```

### Set User Credentials
```
client.getCredentialStore().setEmail(...);
or
client.getCredentialStore().setEkey(...);
or
client.getCredentialStore().setFacebook(...);
or
client.getCredentialStore().setTwitter(...);
```

### Get Links for a file
```
LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
query.put("response_format", "json");
query.put("link_type", "direct_download");
query.put("quick_key", QUICK_KEY);
MediaFireApiRequest request = new MFApiRequest("/file/get_links.php", query, null, null);
// since file/get_links requires a session token...
FileGetLinksResponse response = client.sessionRequest(request, FileGetLinksResponse.class);
```

### Create API Response Class
Gson is used to parse responses in json (using the default response parser). 
Different API versions might have different json responses (different json objects, etc.)
You can create your own response class to use when making an API call by extending the ApiResponse class or implementing MediaFireApiResponse
```
public class MyFolderContentResponse extends ApiResponse {
  // your fields
  
  // your getters
}
```

### Gradle
repository:
```
repositories {
  maven {
    url "https://dl.bintray.com/mediafire/maven/"
  }
```
dependencies:
```
dependencies {
    compile 'com.github.mediafire:mediafire-java-sdk:5.0.2'
}
```
