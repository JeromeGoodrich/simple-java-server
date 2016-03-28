package httpserver.handler.requesthandler;

import httpserver.Base64Decoder;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.*;

public class BasicAuthHandler implements Handler {
    public Response handle(Request request) {
        byte[] bytes = null;
        if (request.getHeader("Authorization") != null) {
            String encodedCredentials = request.getHeader("Authorization").split(" ")[1];
            Base64Decoder decoder = new Base64Decoder();
            String decodedCredentials = decoder.decode(encodedCredentials);
            System.out.println(decodedCredentials);
            String username = decodedCredentials.split(":")[0];
            String password = decodedCredentials.split(":")[1];
            if (username.equals("admin") && password.equals("hunter2")) {
                try {
                    File file = new File("logs.txt");
                    FileInputStream inputStream = new FileInputStream(file);
                    int fileLength = (int) file.length();
                    bytes = new byte[fileLength];
                    inputStream.read(bytes, 0, fileLength);
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new ResponseBuilder(200).reasonPhrase().body(bytes).version(request.getVersion()).build();
            } else {
                ResponseBuilder builder = new ResponseBuilder(401);
                builder.addHeader("WWW-Authenticate", "Camelot");
                return builder.version(request.getVersion()).reasonPhrase().build();
            }
        } else {
            ResponseBuilder builder = new ResponseBuilder(401);
            builder.addHeader("WWW-Authenticate", "Basic realm=\"Camelot\"");
            return builder.version(request.getVersion()).reasonPhrase().build();
        }
    }

    public boolean willHandle(String method, String path) {
        return (path.equals("logs"));
    }
}
