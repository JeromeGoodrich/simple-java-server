package httpserver;

import httpserver.handler.responsehandler.HttpResponseHandler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.response.Response;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseHandlerTest {


    @Test
    public void responseGettersTest() {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        builder.addHeader("Location","http://localhost:5000/");
        Response response = builder.reasonPhrase().body("hello world".getBytes()).version("HTTP/1.1").build();

        assertThat(response.getVersion(), is("HTTP/1.1"));
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), is("hello world"));
        assertThat(response.getHeaderValue("Location"), is("http://localhost:5000/"));
    }




    @Test
    public void testResponseHandle() throws IOException {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
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
