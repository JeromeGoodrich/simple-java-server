package httpserver.handler;

import httpserver.handler.requesthandler.DirHandler;
import httpserver.handler.requesthandler.Handler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DirHandlerTest {

    private final String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";

    @Test
    public void willHandleTrueTest() {
        Handler handler = new DirHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willHandleFalseTest() {
        Handler handler = new DirHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("image.jpeg")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void DirHandleHTMLRequestTest() {
        Handler handler = new DirHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("<li><a href=\"/file1\">file1</a></li>\n"));
    }

    @Test
    public void DirHandleJSONRequestTest() {
        Handler handler = new DirHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Accept", "application/json")
                .path("/")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("{ contents: [big-pdf.pdf, file1, "));
    }
}

