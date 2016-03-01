package HTTPServer;

import java.io.*;
import java.nio.file.Files;


public class FileHandler implements Handler {

    public Response handle(Request request) {
            byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(request.getPath().toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(bytes).reasonPhrase().build();
    }
}
