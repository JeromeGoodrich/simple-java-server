package HTTPServer;

public class ResponseBuilder implements Builder<Response> {
    public int statusCode;
    public byte[] body;

    public ResponseBuilder (int statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseBuilder body(byte[] val) {
        body = val;
        return this;
    }
    public Response build() {
        return new Response(this);
    }

}
