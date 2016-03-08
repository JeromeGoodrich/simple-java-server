package httpserver;

import httpserver.handler.RequestHandler;
import httpserver.request.HTTPRequest;
import httpserver.request.HTTPRequestBuilder;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequestHandlerTest {

    @Test
    public void testHandleRoot() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("/").version("HTTP/1.1").build();
        RequestHandler handler = new RequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getVersion(), is("HTTP/1.1"));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("Hello World"));
    }

    @Test
    public void testHandleDir() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("src/test/fixtures").build();
        RequestHandler handler = new RequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("<!DOCTYPE html>\n<html>\n<body>\n<ol>\n<li><a href=\"/src/test/fixtures/form.html\">form.html</a></li>\n<li><a href=\"/src/test/fixtures/my_file.txt\">my_file.txt</a></li>\n</ol>\n<body>"));
        }

    @Test
    public void testHandleFile() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("src/test/fixtures/my_file.txt").build();
        RequestHandler handler = new RequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("This is a text file.\nThere are many like it, but this one is mine."));
    }

    @Test
    public void testHandleError() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("this/is/not/a/path").build();
        RequestHandler handler = new RequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
    }
}