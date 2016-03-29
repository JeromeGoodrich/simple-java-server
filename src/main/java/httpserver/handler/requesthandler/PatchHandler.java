package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class PatchHandler implements Handler {

    private final String rootDir;

    public PatchHandler (String rootDir){
        this.rootDir = rootDir;
    }

    public Response handle(Request request) {
        String requestEtag = request.getHeader("If-Match");
        Response.ResponseBuilder builder = new Response.ResponseBuilder(204);
        String fileName = rootDir + request.getPath();
        String patchContent = request.getBody();
        writeToFile(fileName, patchContent);
        builder.addHeader("ETag", requestEtag);
        return builder.reasonPhrase().version(request.getVersion()).build();
    }

    private void writeToFile(String fileName, String content) {
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean willHandle(String method, String path) {
        return method.equals("PATCH");
    }
}
