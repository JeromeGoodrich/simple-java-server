package HTTPServer;

import org.hamcrest.Matcher;

public class Response {
    private int statusCode;
    private byte[] body;
    private String reasonPhrase;

    public Response(ResponseBuilder builder) {
        statusCode = builder.statusCode;
        body = builder.body;
        reasonPhrase = builder.reasonPhrase;
    }

    byte[] getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
