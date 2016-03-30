package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatchHandlerTest {

    @Test
    public void willHandleTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new PatchHandler(rootDir) ;
        Request request = new Request.RequestBuilder()
                .method("PATCH")
                .version("HTTP/1.1")
                .path("patch-content.txt")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willnotHandleTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
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
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new PatchHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("PATCH")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .body("patched-content")
                .path("patch-content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(204));
        assertThat(response.getHeaderValue("ETag"), is("1"));

        Handler fileHandler = new FileHandler(rootDir);
        Request getRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("If-Match","1")
                .path("patch-content.txt")
                .build();
        Response getResponse = fileHandler.handle(getRequest);

        assertThat(getResponse.getStatusCode(), is(200));
        assertThat(new String(getResponse.getBody()), containsString("patched-content"));
    }
}
