package httpserver.handler;

import httpserver.LogHandlerCreator;
import httpserver.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatchHandlerTest {

    private final String rootDir = System.getProperty("user.dir") + "/src/test/fixtures/";

    @Test
    public void willHandleTest() {
        Handler handler = new PatchHandler(rootDir) ;
        Request request = new Request.RequestBuilder()
                .method("PATCH")
                .version("HTTP/1.1")
                .path("/patch-content.txt")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willnotHandleTest() {
        Handler handler = new PatchHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void PatchHandlerTest() {
        Handler handler = new PatchHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("PATCH")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .body("patched content")
                .path("/patch-content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(204));
        assertThat(response.getHeaderValue("ETag"), is("1"));

        LogHandlerCreator lhc = new LogHandlerCreator("test.log");
        RequestLogger logger = new RequestLogger(lhc);
        Handler fileHandler = new FileHandler(rootDir, logger);
        Request getRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .path("/patch-content.txt")
                .build();
        Response getResponse = fileHandler.handle(getRequest);

        assertThat(getResponse.getStatusCode(), is(200));
        assertThat(new String(getResponse.getBody()), containsString("patched content"));
        Request resetRequest = new Request.RequestBuilder()
                .method("PATCH")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .body("default content")
                .path("/patch-content.txt")
                .build();
        handler.handle(resetRequest);
        Request checkDefaultRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .path("/patch-content.txt")
                .build();
        Response defaultResponse = fileHandler.handle(checkDefaultRequest);
        assertThat(new String(defaultResponse.getBody()), containsString("default content"));

    }
}
