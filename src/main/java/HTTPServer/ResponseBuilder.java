package HTTPServer;

public class ResponseBuilder implements Builder<Response> {
    public int statusCode;
    public byte[] body;
    public String reasonPhrase;

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

    public Response build() {
        return new Response(this);
    }

}
