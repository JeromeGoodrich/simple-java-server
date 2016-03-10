package httpserver.request;

import java.util.Map;

public class HTTPRequest extends Request {
    private String method;
    private String path;
    private String version;
    private Map<String, String> body;
    private Map<String, String> headers;

    public HTTPRequest(HTTPRequestBuilder builder) {
        method = builder.method;
        path = builder.path;
        version = builder.version;
        headers = builder.headers;
        body = builder.body;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getVersion() {
        return this.version;
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    public String getBodyVal(String key) {
        return this.body.get(key);
    }
}
