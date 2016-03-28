package httpserver;

import httpserver.handler.requesthandler.FileHandler;
import httpserver.handler.requesthandler.Handler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileHandlerTest {

    @Test
    public void handlePartialContentTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=0-4")
                .path("partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is("This "));
    }

    @Test
    public void handlePartialContentTestNoRangeStart() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=-6")
                .path("partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is(" 206.\n"));
    }

    @Test
    public void handlePartialContentTestNoRangeEnd() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=4-")
                .path("partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is(" is a file that contains text to read part of in order to fulfill a 206.\n"));
    }
}
