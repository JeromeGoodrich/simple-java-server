package HTTPServer;

import java.io.File;
import java.nio.file.Paths;

public class RequestHandler {
    Response handle(Request request) {
        if (request.getPath().equals("/")) {
            String greeting = "Hello World";
            ResponseBuilder builder = new ResponseBuilder(200);
                return builder.reasonPhrase().version(request.getVersion()).body(greeting.getBytes()).build();
        } else if (new File(request.getPath()).isDirectory()) {
                Handler handler = new DirectoryHandler();
                    return handler.handle(request);
        } else if (new File(request.getPath()).isFile()) {
                    Handler handler = new FileHandler();
                    return handler.handle(request);
        } else {
            System.out.println(request.getPath());
            ResponseBuilder builder = new ResponseBuilder(404);
            return builder.reasonPhrase().version(request.getVersion()).build();
        }
    }
}

