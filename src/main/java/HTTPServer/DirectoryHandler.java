package HTTPServer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;

public class DirectoryHandler implements Handler {
    public Response handle(Request request) {
        File dir = new File(request.getPath());
        String[] dirListing = dir.list();
        String stringDirListing = Arrays.toString(dirListing);
        stringDirListing = stringDirListing.substring(1, stringDirListing.length()-1);
        byte[] data = stringDirListing.getBytes();
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }
}
