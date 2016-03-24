package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;;

public class FileHandler implements Handler {

    private final String rootDir;

    public FileHandler(String rootDir) {
        this.rootDir = rootDir;
    }

    public Response handle(Request request) {
        byte[] bytes = null;
        String mimeType = URLConnection.guessContentTypeFromName(request.getPath());
        if (mimeType == null) mimeType = "text/plain";
        ResponseBuilder builder = new ResponseBuilder(200);
        builder.addHeader("Content-Type", mimeType);
        try {
            bytes = Files.readAllBytes(Paths.get(rootDir +request.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.addHeader("Content-Length", Integer.toString(bytes.length));
        if (builder.headers.get("Content-Type").equals("application/pdf") && Integer.parseInt(builder.headers.get("Content-Length")) > 10485760){
            builder.addHeader("Content-Disposition", "attachment; filename=\"big-pdf.pdf\"");
        }
        return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("GET") && new File(rootDir + path).isFile()) return true;
        return false;
    }
}
