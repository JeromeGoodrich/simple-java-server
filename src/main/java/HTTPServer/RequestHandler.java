package HTTPServer;

import java.io.File;
import java.nio.file.Paths;

public class RequestHandler {
    Response handle(Request request) {
        if (request.getPath().equals(Paths.get("/"))) {
            return new Response(200, "Hello World");
        } else {
            if (request.getPath().toFile().isDirectory()) {
                Handler handler = new DirectoryHandler();
                    return new Response(200, "hellow");
            }
        }
        return null;
    }
}