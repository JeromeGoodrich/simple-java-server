package HTTPServer;

import org.hamcrest.Matcher;

import java.util.Map;

public class Response {
    private int statusCode;
    private byte[] body;
    private String reasonPhrase;
    private String version;
    private Map<String, String> headers;

    public Response(ResponseBuilder builder) {
        statusCode = builder.statusCode;
        body = builder.body;
        reasonPhrase = builder.reasonPhrase;
        version = builder.version;
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

    public String getVersion () {
        return version;
    }

}
