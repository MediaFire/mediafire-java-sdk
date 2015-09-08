MediaFire Java SDK
==================
Updated 9/8/2015

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
query.put("link_type", "direct_download");
query.put("quick_key", QUICK_KEY);
MediaFireApiRequest request = new MFApiRequest("/file/get_links.php", query, null, null);
// since file/get_links requires a session token...
FileGetLinksResponse response = client.sessionRequest(request, FileGetLinksResponse.class);
```

### Override default implementations
There might be situations where you want to override default implementations in the SDK.
A good example is the MediaFireCredentialsStore interface (you probably want to store these securely).
When building the MediaFireClient using the MFClient.Builder you can override several interfaces used:
```
MFClient.Builder builder = new MFClient.Builder(APPLICATION_ID, API_KEY);
builder.apiVersion(API_VERSION); // set api version, all api calls will use this api version
builder.parser(PARSER); // given a byte[] response you can parse this however you want.
builder.hasher(HASHER); // md5, sha1, and sha256 hashing
builder.credentialStore(CREDENTIAL_STORE); // store credentials remotely, in a local db, encrypted, etc.
builder.sessionStore(SESSION_STORE); // store session and action tokens remotely, in a local db, in memory, etc.
builder.httpRequester(HTTP_REQUESTER); // https calls for get/post
MediaFireClient client = builder.build();
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
