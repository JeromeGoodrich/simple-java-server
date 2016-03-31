package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionsHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new OptionsHandler();
        Request request = new Request.RequestBuilder()
                .method("OPTIONS")
                .version("HTTP/1.1")
                .path("/method_options")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willnotHandleTest() {
        Handler handler = new OptionsHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/hello_world")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void OptionsHandleTest() {
        Handler handler = new OptionsHandler();
        Request request = new Request.RequestBuilder()
                .method("OPTIONS")
                .version("HTTP/1.1")
                .path("/method_options")
                .build();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Allow"), is("GET,HEAD,POST,OPTIONS,PUT"));
    }
}
