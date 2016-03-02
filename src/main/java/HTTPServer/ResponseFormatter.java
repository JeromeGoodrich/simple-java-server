package HTTPServer;

public class ResponseFormatter {
    public byte[] format(Response response) {
        String statusLine = "HTTP/" + response.getVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n";
        String body = new String(response.getBody());
        String formattedResponse = statusLine + "\r\n" + body;
        return formattedResponse.getBytes();
    }
}
