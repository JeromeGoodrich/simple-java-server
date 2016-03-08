package httpserver.request;

import httpserver.response.Builder;

import java.util.HashMap;
import java.util.Map;

public class HTTPRequestBuilder implements Builder<HTTPRequest> {
    public String method;
    public String path;
    public String version;
    public Map<String, String> body = new HashMap<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();


    public HTTPRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    public HTTPRequestBuilder path(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequestBuilder version(String version) {
        this.version = version;
        return this;

    }

    public HTTPRequestBuilder headers(String headerFieldName, String headerValue) {
        headers.put(headerFieldName, headerValue);
        return this;
    }

    public HTTPRequestBuilder body(String key, String value) {
        body.put(key, value);
        return this;
    }

    public HTTPRequest build() {
        return new HTTPRequest(this);
    }

}
