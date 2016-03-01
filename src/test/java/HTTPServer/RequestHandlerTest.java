package HTTPServer;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequestHandlerTest {

    @Test
    public void testHandleRoot() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath(Paths.get("/"));
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("Hello World"));
    }

    @Test
    public void testHandleDir() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath(Paths.get("./src/test/fixtures"));
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("my_file.txt"));
    }

    @Test
    public void testHandleFile() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath(Paths.get("./src/test/fixtures/my_file.txt"));
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getBody()), is("This is a text file.\nThere are many like it, but this one is mine."));
    }

    @Test
    public void testHandleError() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath(Paths.get("./this/is/not/a/path"));
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
    }
}