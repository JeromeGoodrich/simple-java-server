package httpserver.handler;

import httpserver.logger.LogHandlerCreator;
import httpserver.logger.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileHandlerTest {

    private final String rootDir = System.getProperty("user.dir") + "/src/test/fixtures/";
    private final LogHandlerCreator lhc = new LogHandlerCreator("test.log");
    private final RequestLogger logger = new RequestLogger(lhc);

    @Test
    public void willHandleTrueTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/file1")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willHandleFalseTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/not-a-file")
                .build();

        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void handleFileTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/file1")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("text/plain"));
        assertThat(response.getHeaderValue("Content-Length"), is("14"));
        assertThat(new String(response.getBody()), is("file1 contents"));
    }

    @Test
    public void handleImageTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/image.jpeg")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("image/jpeg"));
        assertThat(response.getHeaderValue("Content-Length"), is("157751"));
        assertThat(new String(response.getBody()), is(notNullValue()));
    }

    @Test
    public void handleSmallPDFTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/pdf-sample.pdf")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getHeaderValue("Content-Type"), is("application/pdf"));
        assertThat(response.getHeaderValue("Content-Length"), is("7945"));
        assertThat(new String(response.getBody()), is(notNullValue()));
    }

    @Test
    public void handleBigPDFTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/big-pdf.pdf")
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
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("HEAD")
                .version("HTTP/1.1")
                .path("/partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(405));
    }

    @Test
    public void handlePartialContentTest() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=0-4")
                .path("/partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is("This "));
    }

    @Test
    public void handlePartialContentTestNoRangeStart() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=-6")
                .path("/partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is(" 206.\n"));
    }

    @Test
    public void handlePartialContentTestNoRangeEnd() {
        Handler handler = new FileHandler(rootDir, logger);
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .headers("Range", "bytes=4-")
                .path("/partial_content.txt")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(206));
        assertThat(new String(response.getBody()), is(" is a file that contains text to read part of in order to fulfill a 206.\n"));
    }

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void testFileNotFoundException() {
//        thrown.expect(NullPointerException.class);
//        Handler handler = new FileHandler(rootDir, logger);
//        Request request = new Request.RequestBuilder()
//                .method("GET")
//                .version("HTTP/1.1")
//                .path("/")
//                .build();
//        Response response = handler.handle(request);
//
//        assertThat(new String(logger.accessLogs()), containsString("FileNotFoundException"));
//    }
}
