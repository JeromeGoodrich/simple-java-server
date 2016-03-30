package httpserver.response;

import httpserver.response.Response;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseTest {


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
    public void testResponsesendToClient() throws IOException {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        builder.addHeader("Location", "http://localhost:5000/");
        Response response = builder.version("HTTP/1.1").reasonPhrase().body("Hello World".getBytes()).build();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.sendToClient(out);

        assertThat(out.toString(), containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(out.toString(), containsString("Location: http://localhost:5000/\r\n\r\nHello World"));
    }
}
