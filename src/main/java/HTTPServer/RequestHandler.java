package HTTPServer;

import java.io.File;
import java.nio.file.Paths;

public class RequestHandler {
    Response handle(Request request) {
        if (request.getPath().equals(Paths.get("/"))) {
            String greeting = "Hello World";
            ResponseBuilder builder = new ResponseBuilder(200);
                return builder.reasonPhrase().body(greeting.getBytes()).build();
        } else if (request.getPath().toAbsolutePath().toFile().isDirectory()) {
                Handler handler = new DirectoryHandler();
                    return handler.handle(request);
        } else if (request.getPath().toAbsolutePath().toFile().isFile()) {
                    Handler handler = new FileHandler();
                    return handler.handle(request);
        } else {
            ResponseBuilder builder = new ResponseBuilder(404);
            return builder.reasonPhrase().build();
        }
    }
}

