package httpserver.handler.requesthandler;

import httpserver.Base64Decoder;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.*;

public class BasicAuthHandler implements Handler {

    public Response handle(Request request) {
        if (request.getHeader("Authorization") != null) {
            Base64Decoder decoder = new Base64Decoder();
            String decodedCredentials = decoder.decode(request.getHeader("Authorization"));
            String username = decodedCredentials.split(":")[0];
            String password = decodedCredentials.split(":")[1];
            if (username.equals("admin") && password.equals("hunter2")) {
                return accessGranted(request);
            } else {
                return accessDenied(request);
            }
        } else {
            return accessDenied(request);
        }
    }

    private Response accessDenied(Request request) {
        ResponseBuilder builder = new ResponseBuilder(401);
        builder.addHeader("WWW-Authenticate", "Basic realm=\"Camelot\"");
        return builder.version(request.getVersion()).reasonPhrase().build();
    }

    private Response accessGranted(Request request) {
        byte[] bytes = null;
        File file = new File("logs.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int fileLength = (int) file.length();
            bytes = new byte[fileLength];
            inputStream.read(bytes, 0, fileLength);
            inputStream.close();
            new FileWriter(file).write("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseBuilder(200).reasonPhrase().body(bytes).version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (path.equals("logs"));
    }
}
