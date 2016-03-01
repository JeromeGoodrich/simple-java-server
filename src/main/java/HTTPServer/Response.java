package HTTPServer;

import org.hamcrest.Matcher;

public class Response {
    private int statusCode;
    private byte[] body;

    public Response(ResponseBuilder builder) {
        statusCode = builder.statusCode;
        body = builder.body;
    }

    byte[] getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
