package httpserver.response;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private byte[] body;
    private String reasonPhrase;
    private String version;
    private Map<String, String> headers;

    public static class ResponseBuilder {
        private int statusCode;
        private byte[] body;
        private String reasonPhrase;
        private String version;
        private Map<String, String> headers = new HashMap<String, String>();

        public ResponseBuilder(int statusCode) {
            this.statusCode = statusCode;
        }

        public ResponseBuilder body(byte[] val) {
            body = val;
            return this;
        }

        public ResponseBuilder reasonPhrase() {
            reasonPhrase = Status.statusCodes.get(statusCode);
            return this;
        }

        public ResponseBuilder version(String val) {
            version = val;
            return this;
        }

        public void addHeader(String s, String s1) {
            headers.put(s, s1);
        }

        public String getHeader(String key) {
            return headers.get(key);
        }

        public Response build() {
            return new Response(this);
        }
    }

    private Response(ResponseBuilder builder) {
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
