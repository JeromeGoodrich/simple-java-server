package httpserver;

import httpserver.handler.requesthandler.HttpRequestHandler;
import httpserver.request.HTTPRequestBuilder;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class HttpRequestHandlerTest {

    @Test
    public void testHandleRoot() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("/").version("HTTP/1.1").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getVersion(), is("HTTP/1.1"));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("Hello World"));
    }

    @Test
    public void testHandleDir() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("src/test/fixtures").headers("Accept","*/*").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), containsString("a href="));
        }

    @Test
    public void testHandleFile() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("src/test/fixtures/my_file.txt").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getHeaderValue("Content-Type"), is("text/plain"));
        assertThat(response.getHeaderValue("Content-Length"), is("66"));
        assertThat(new String(response.getBody()), is("This is a text file.\nThere are many like it, but this one is mine."));
    }

    @Test
    public void testGetError() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("this/is/not/a/path").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
    }

    @Test
    public void testHandleForm() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("GET").path("form").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("</form>"));
    }

    @Test
    public void testHandlePost() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("POST").path("form").body("firstname", "Jerome").body("lastname", "Goodrich").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("Jerome"));
        assertThat(new String(response.getBody()), containsString("Goodrich"));
    }

    @Test
    public void testPostError() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("POST").path("this/is/not/a/path").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));

    }
    @Test
    public void testUnsupportedMethod() {
        HTTPRequestBuilder builder = new HTTPRequestBuilder();
        Request request = builder.method("CONNECT").path("/").build();
        HttpRequestHandler handler = new HttpRequestHandler();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));

    }
}