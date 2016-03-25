package httpserver.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder implements Builder<Response> {
    public int statusCode;
    public byte[] body;
    public String reasonPhrase;
    public String version;
    public Map<String, String> headers = new HashMap<String, String>();

    public ResponseBuilder (int statusCode) {
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

    public Response build() {
        return new HttpResponse(this);
    }

    public void addHeader(String s, String s1) {
        headers.put(s, s1);
    }
}
