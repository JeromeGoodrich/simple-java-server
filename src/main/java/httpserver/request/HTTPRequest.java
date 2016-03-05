package httpserver.request;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequest extends Request {
    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;

    public HTTPRequest(HTTPRequestBuilder builder) {
        method = builder.method;
        path = builder.path;
        version = builder.version;
        headers = builder.headers;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHeaders(HashMap headers) {
        this.headers = headers;
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
}
