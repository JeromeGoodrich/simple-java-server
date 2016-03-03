package httpserver;

import httpserver.handler.RequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequestHandlerTest {

    @Test
    public void testHandleRoot() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setVersion("HTTP/1.1");
        request.setPath("/");
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getVersion(), is("HTTP/1.1"));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("Hello World"));
    }

    @Test
    public void testHandleDir() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath("src/test/fixtures");
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("<!DOCTYPE html>\n<html>\n<body>\n<ol>\n<li><a href=\"/src/test/fixtures/my_file.txt\">my_file.txt</a></li>\n</ol>\n<body>"));
    }

    @Test
    public void testHandleFile() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath("src/test/fixtures/my_file.txt");
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("This is a text file.\nThere are many like it, but this one is mine."));
    }

    @Test
    public void testHandleError() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath("this/is/not/a/path");
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
    }
}