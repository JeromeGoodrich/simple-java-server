package HTTPServer;

public class ResponseFormatter {
    public byte[] format(Response response) {
        String statusLine = "HTTP/" + response.getVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n";

        return statusLine.getBytes();
    }
}
