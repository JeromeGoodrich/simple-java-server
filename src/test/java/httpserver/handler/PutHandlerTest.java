package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PutHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new PutHandler();
        Request request = new Request.RequestBuilder()
                .method("PUT")
                .version("HTTP/1.1")
                .path("/file1")
                .build();

        assertThat(handler.willHandle(request.getMethod(),request.getPath()), is(true));

    }

    @Test
    public void willNotHandleTest() {
        Handler handler = new PutHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/file1")
                .build();
        assertThat(handler.willHandle(request.getMethod(),request.getPath()), is(false));
    }

    @Test
    public void putHandlerTest() {
        Handler handler = new PutHandler();
        Request request = new Request.RequestBuilder()
                .method("PUT")
                .version("HTTP/1.1")
                .path("/file1")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
    }
}
