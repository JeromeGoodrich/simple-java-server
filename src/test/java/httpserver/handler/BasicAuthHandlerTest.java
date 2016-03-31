package httpserver.handler;

import httpserver.logger.LogHandlerCreator;
import httpserver.logger.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthHandlerTest {

    private final LogHandlerCreator lhc = new LogHandlerCreator("./test.log");
    private final RequestLogger logger = new RequestLogger(lhc);

    @Test
    public void basicAuthUnauthorizedTest() {
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/logs")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(401));
        assertThat(response.getHeaderValue("WWW-Authenticate"), is("Basic realm=\"Camelot\""));
    }

    @Test
    public void basicAuthWrongCredentialsTest() {
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/logs")
                .headers("Authorization","Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(401));
    }

    @Test
    public void basicAuthAuthorizedTest() {
        Handler handler = new BasicAuthHandler(logger);
        logger.log(Level.INFO, "GET /log HTTP/1.1");
        logger.log(Level.INFO, "GET /these HTTP/1.1");
        logger.log(Level.INFO, "GET /requests HTTP/1.1");

        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Authorization", "Basic YWRtaW46aHVudGVyMg==")
                .path("/logs")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("GET /log HTTP/1.1"));
        assertThat(new String(response.getBody()), containsString("GET /these HTTP/1.1"));
        assertThat(new String(response.getBody()), containsString("GET /requests HTTP/1.1"));
    }

    @Test
    public void willHandleTrueTest() {
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/logs")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willHandleFalseTest() {
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }
}

