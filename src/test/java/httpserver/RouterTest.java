package httpserver;

import httpserver.handler.FakeHandler;
import httpserver.handler.Handler;
import httpserver.handler.Router;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RouterTest {

    @Test
    public void testSelectHandler() {
        List<Handler> handlers = new ArrayList<>();
        handlers.add(new FakeHandler("/foo", 1000));
        handlers.add(new FakeHandler("/bar", 500));
        Router router = new Router(handlers);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/foo")
                .build();
        Response response = router.handle(request);

        assertThat(response.getStatusCode(), is(1000));
    }
}
