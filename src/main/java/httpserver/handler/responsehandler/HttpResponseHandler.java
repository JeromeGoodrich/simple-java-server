package httpserver.handler.responsehandler;

import httpserver.response.Response;
import httpserver.server.ClientSocketInterface;

import java.io.*;
import java.util.Map;

public class HttpResponseHandler implements ResponseHandler {

    public InputStream handle(Response response) throws IOException {
        String statusLine = response.getVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n";
        String headers = "";
        for(Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headers += key + ": " + value + "\r\n";
        }
        String formattedResponse = statusLine + headers + "\r\n";
        byte[] bytes = formattedResponse.getBytes();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        out.write(response.getBody());
        byte [] combinedBytes = out.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(combinedBytes);
        return inputStream;
    }

    public void sendToClient(InputStream in, OutputStream out, byte[] buf) throws IOException {
        int numRead;
        while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
        out.flush();
        out.close();
    }
}