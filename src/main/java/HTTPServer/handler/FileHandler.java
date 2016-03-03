package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileHandler implements Handler {

    public Response handle(Request request) {
            byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(request.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseBuilder builder = new ResponseBuilder(200);
        System.out.println(Paths.get(request.getPath()));
        return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
    }
}
