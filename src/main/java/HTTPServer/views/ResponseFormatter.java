package httpserver.views;

import httpserver.response.Response;

public class ResponseFormatter {
    public byte[] format(Response response) {
        String statusLine = response.getVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n";
        String body = "";
        if (response.getBody() != null) {
        body = new String(response.getBody());
        }
        String formattedResponse = statusLine + "\r\n" + body;
        return formattedResponse.getBytes();
    }
}
