package httpserver.handler;

import httpserver.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAuthHandlerTest {

    @Test
    public void basicAuthUnauthorizedTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("logs")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(401));
        assertThat(response.getHeaderValue("WWW-Authenticate"), is("Basic realm=\"Camelot\""));
    }

    @Test
    public void basicAuthWrongCredentialsTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("logs")
                .headers("Authorization","Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(401));
    }

    @Test
    public void basicAuthAuthorizedTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        Handler handler = new BasicAuthHandler(logger);
        logger.log(Level.INFO, "GET /log HTTP/1.1");
        logger.log(Level.INFO, "GET /these HTTP/1.1");
        logger.log(Level.INFO, "GET /requests HTTP/1.1");

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

    @Test
    public void willHandleTrueTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("logs")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willHandleFalseTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        Handler handler = new BasicAuthHandler(logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }
}

