package httpserver;

import httpserver.handler.requesthandler.Handler;
import httpserver.handler.requesthandler.OldFormHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OldFormHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new OldFormHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("oldform")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willNotHandleTest() {
        Handler handler = new OldFormHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("form")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void oldformHandleGetTest() {
        Handler handler = new OldFormHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("oldform")
                .build();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("<form method=\"post\">"));
    }
    @Test
    public void oldformHandlePostTest() {
        Handler handler = new OldFormHandler();
        Request request = new Request.RequestBuilder()
                .method("POST")
                .version("HTTP/1.1")
                .body("firstname=jerome&lastname=goodrich")
                .path("oldform")
                .build();
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("jerome goodrich"));
    }
}
