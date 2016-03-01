package HTTPServer;

import java.io.File;
import java.nio.file.Paths;

public class RequestHandler {
    Response handle(Request request) {
        if (request.getPath().equals(Paths.get("/"))) {
            String greeting = "Hello World";
            ResponseBuilder builder = new ResponseBuilder(200);
                return builder.body(greeting.getBytes()).build();
        } else {
            if (request.getPath().toFile().isDirectory()) {
                Handler handler = new DirectoryHandler();
                    return handler.handle(request);
            } else {
                System.out.println(request.getPath());
                System.out.println(Paths.get("src/test/fixtures"));
                return null;
            }
        }
    }
}