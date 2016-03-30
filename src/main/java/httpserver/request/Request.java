package httpserver.request;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String method;
    private final String path;
    private final String version;
    private final String body;
    private final String logInfo;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    public static class RequestBuilder {
        private String method;
        private String path;
        private String version;
        private String body;
        private String logInfo;
        private Map<String, String> headers = new HashMap<String, String>();
        private Map<String, String> params = new HashMap<String, String>();


        public RequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        public RequestBuilder path(String path) {
            this.path = path;
            return this;
        }

        public RequestBuilder version(String version) {
            this.version = version;
            return this;
        }

        public RequestBuilder headers(String headerFieldName, String headerValue) {
            headers.put(headerFieldName, headerValue);
            return this;
        }

        public RequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public RequestBuilder params(String name, String value) {
            params.put(name, value);
            return this;
        }

        public RequestBuilder logInfo(String requestContents) {
            this.logInfo = requestContents;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }

    private Request(RequestBuilder builder) {
        method = builder.method;
        path = builder.path;
        version = builder.version;
        headers = builder.headers;
        body = builder.body;
        params = builder.params;
        logInfo = builder.logInfo;
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

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public String getLogInfo() {
        return this.logInfo;
    }
}
