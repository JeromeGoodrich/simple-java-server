package HTTPServer;

/**
 * Created by admin on 2/29/16.
 */
public class RequestHandler {
    Response handle(Request request) {
        if (request.getPath().equals("/")) {
            return new Response (200, "Hello World");
        }
        return null;
    }
}
