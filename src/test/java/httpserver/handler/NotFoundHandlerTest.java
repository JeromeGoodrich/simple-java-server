package httpserver.handler;


import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new NotFoundHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/notapath")
                .build();
        Response response = handler.handle(request);

        assertThat(handler.willHandle(request.getMethod(),request.getPath()), is(true));
        assertThat(response.getStatusCode(), is(404));

    }
}
