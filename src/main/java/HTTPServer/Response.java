package HTTPServer;

import org.hamcrest.Matcher;

/**
 * Created by admin on 2/29/16.
 */
public class Response {
    private int statusCode;
    private String reasonStatement;
    private byte[] body;

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
