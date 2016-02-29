package HTTPServer;

import org.hamcrest.Matcher;

public class Response {
    private int statusCode;
    private byte[] body;

    public Response(ResponseBuilder builder) {
        statusCode = builder.statusCode;
        body = builder.body;

    }

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body.getBytes();
    }

    byte[] getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
