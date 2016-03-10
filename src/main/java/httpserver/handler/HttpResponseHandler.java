package httpserver.handler;

import httpserver.handler.ResponseHandler;
import httpserver.response.Response;
import httpserver.server.ClientSocketInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpResponseHandler implements ResponseHandler {

    public InputStream handle(Response response, ClientSocketInterface socket) {
        String statusLine = response.getVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase() + "\r\n";
        String body = "";
        if (response.getBody() != null) {
            body = new String(response.getBody());
        }
        String formattedResponse = statusLine + "\r\n" + body;
        byte[] bytes = formattedResponse.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
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