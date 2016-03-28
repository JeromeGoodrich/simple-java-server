package httpserver;

import httpserver.handler.requesthandler.BasicAuthHandler;
import httpserver.handler.requesthandler.Handler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthHandlerTest {

    @Test
    public void basicAuthUnauthorizedTest() {
        Handler handler = new BasicAuthHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=0-4")
                .path("logs")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(401));
        assertThat(response.getHeaderValue("WWW-Authenticate"), is("Basic realm=\"Camelot\""));
    }

    @Test
    public void basicAuthAuthorizedTest() {
        Handler handler = new BasicAuthHandler();
        Request request1 = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("log")
                .build();
        Request request2 = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("these")
                .build();
        Request request3 = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("requests")
                .build();
        RequestLogger logger = new RequestLogger();
            logger.log(request1);
            logger.log(request2);
            logger.log(request3);

        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Authorization", "Basic YWRtaW46aHVudGVyMg==")
                .path("logs")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("GET /log HTTP/1.1"));
        assertThat(new String(response.getBody()), containsString("GET /these HTTP/1.1"));
        assertThat(new String(response.getBody()), containsString("GET /requests HTTP/1.1"));
    }

}
