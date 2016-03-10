package httpserver;

import httpserver.handler.responsehandler.HttpResponseHandler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseHandlerTest {

    @Test
    public void testResponseHandle() throws IOException {
        ResponseBuilder builder = new ResponseBuilder(200);
        Response response = builder.version("HTTP/1.1").reasonPhrase().body("Hello World".getBytes()).build();
        ResponseHandler handler = new HttpResponseHandler();
        InputStream in = handler.handle(response);
        InputStreamReader reader = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        int charRead;
        while ((charRead = reader.read()) >= 0) {
            sb.append((char) charRead);
        }
        assertThat(sb.toString(), is("HTTP/1.1 200 OK\r\n\r\nHello World"));
    }

    @Test
    public void testSendResponse() throws IOException {
        String input = "Testing, testing 1 2\n Hello there.";
        byte[] bytes = input.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        ResponseHandler handler = new HttpResponseHandler();
        handler.sendToClient(inputStream, outputStream, buf);
        assertThat(outputStream.toString(), is(input));
    }
}
