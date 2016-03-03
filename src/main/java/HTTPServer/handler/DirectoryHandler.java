package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import httpserver.views.DirectoryFormatter;

import java.io.File;

public class DirectoryHandler implements Handler {
    public Response handle(Request request) {
        File dir = new File(request.getPath());
        String[] dirListing = dir.list();
        DirectoryFormatter formatter = new DirectoryFormatter();
        String formattedDirListing = formatter.format(dirListing, request);
        byte[] data = formattedDirListing.getBytes();
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }
}
