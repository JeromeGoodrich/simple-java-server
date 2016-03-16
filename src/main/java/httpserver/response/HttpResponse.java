package httpserver.response;

import java.io.InputStream;
import java.util.Map;

public class HttpResponse implements Response {
    private int statusCode;
    private byte[] body;
    private String reasonPhrase;
    private String version;
    private Map<String, String> headers;

    public HttpResponse(ResponseBuilder builder) {
        statusCode = builder.statusCode;
        body = builder.body;
        reasonPhrase = builder.reasonPhrase;
        version = builder.version;
        headers = builder.headers;
    }

    public byte[] getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public InputStream getRawRequest() {
        return null;
    }

    public String getVersion () {
        return version;
    }
    public String getHeaderValue(String headerKey) {
        return headers.get(headerKey);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
