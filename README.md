# Mallard

![alt text](https://github.com/TealDrones/Mallard/blob/master/mallard.png?raw=true)

Mallard is a WebDav library aimed at being simple to use.
Mallard currently supports these WebDAV request methods:
* `PROPFIND` - use `Mallard.propfind()` is used to retrieve a list of files and directories 
               from a WebDAV server.
* `DELETE` - use `Mallard.delete()` is used to delete a resource from a WebDAV server.

## Example Usage
```java
public class MallardExample {
    public void makePropfindRequest() {
        Mallard.propfind("http://webdavserver.com/folder", Depth.ONE, new OnPropfindRequestCompleteListener() {
            @Override
            public void onSuccess(List<RemoteFile> remoteFiles) {
                Log.d("test", "Success with " + remoteFiles.size() + " files");
            }
    
            @Override
            public void onError(RequestError error) {
                Log.d("test", "Failed with error type of " + error.getErrorType());
    
                if (error.getErrorException() != null) {
                    error.getErrorException().printStackTrace();
                }
            }
        });
    }
    
    public void makeDeleteRequest() {
        Mallard.delete("http://webdavserver.com/folder/filename.tfl", new OnDeleteRequestCompleteListener() {
            @Override
            public void onSuccess() {
                Log.d("test", "Successfully deleted file");
            }
    
            @Override
            public void onError(RequestError error) {
                Log.d("test", "Failed to delete file with error type of " + error.getErrorType());
    
                if (error.getErrorException() != null) {
                    error.getErrorException().printStackTrace();
                }
            }
        });
    }
}
```