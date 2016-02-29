package HTTPServer;

/**
 * Created by admin on 2/29/16.
 */
public class DirectoryHandler implements Handler {
    public Response handle(Request request) {
        Builder<Response> builder = new ResponseBuilder(200);
        return builder.build();

    }
}
