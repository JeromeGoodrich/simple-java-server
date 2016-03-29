package httpserver.handler;

import httpserver.handler.requesthandler.Handler;
import httpserver.handler.requesthandler.RedirectHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new RedirectHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("redirect")
                .build();

        assertThat(handler.willHandle(request.getMethod(),request.getPath()), is(true));
    }

    @Test
    public void willNotHandleTest() {
        Handler handler = new RedirectHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("file1")
                .build();
        assertThat(handler.willHandle(request.getMethod(),request.getPath()), is(false));
    }

    @Test
    public void RedirectHandlerTest() {
        Handler handler = new RedirectHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("redirect")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(302));
        assertThat(response.getHeaderValue("Location"), is("http://localhost:5000/"));
    }

}
