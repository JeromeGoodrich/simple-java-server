package httpserver;

import httpserver.handler.requesthandler.FileHandler;
import httpserver.handler.requesthandler.Handler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileHandlerTest {

    @Test
    public void willHandleTrueTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("file1")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willHandleFalseTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("not-a-file")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void handleFileTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("file1")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("text/plain"));
        assertThat(response.getHeaderValue("Content-Length"), is("14"));
        assertThat(new String(response.getBody()), is("file1 contents"));
    }

    @Test
    public void handleImageTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("image.jpeg")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("image/jpeg"));
        assertThat(response.getHeaderValue("Content-Length"), is("157751"));
        assertThat(new String(response.getBody()), is(notNullValue()));
    }

    @Test
    public void handleSmallPDFTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("pdf-sample.pdf")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("application/pdf"));
        assertThat(response.getHeaderValue("Content-Length"), is("7945"));
        assertThat(new String(response.getBody()), is(notNullValue()));
    }

    @Test
    public void handleBigPDFTest() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("big-pdf.pdf")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("application/pdf"));
        assertThat(response.getHeaderValue("Content-Length"), is("10762150"));
        assertThat(response.getHeaderValue("Content-Disposition"), containsString("attachment;"));
        assertThat(new String(response.getBody()), is(notNullValue()));

    }

    @Test
    public void handleMethodNotAllowed() {
        String rootDir = "/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/";
        Handler handler = new FileHandler(rootDir);
        Request request = new Request.RequestBuilder()
                .method("HEAD")
                .version("HTTP/1.1")
                .path("partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(405));
    }

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
