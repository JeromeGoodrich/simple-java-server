package HTTPServer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ResponderTest {

    @Test
    public void testStatusRoot() {
        Request request = new Request();
        Responder responder = new Responder();
        request.setPath("/");
        request.setVersion("1.1");
        responder.createStatusLine(request.getPath(), request.getVersion());
        assertThat(responder.getStatusLine(), is("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void testStatusNotFound() {
        Request request = new Request();
        Responder responder = new Responder();
        request.setPath("Not a Path");
        request.setVersion("1.1");
        responder.createStatusLine(request.getPath(), request.getVersion());
        assertThat(responder.getStatusLine(), is("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void testStatusValidPath() {
        Request request = new Request();
        Responder responder = new Responder();
        request.setPath("/src/test/fixtures");
        request.setVersion("1.1");
        responder.createStatusLine(request.getPath(), request.getVersion());
        assertThat(responder.getStatusLine(), is("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void testResponseBodyRoot() {
        Request request = new Request();
        Responder responder = new Responder();
        request.setPath("/");
        responder.createResponseBody(request.getPath());
        ArrayList body = new ArrayList<String>();
        body.add("Hello World");
        assertThat(responder.getResponseBody(), is(body));
    }

    @Test
    public void testResponseBodyFile() {
        Request request = new Request();
        Responder responder = new Responder();
        request.setPath("/src/test/fixtures/my_file.txt");
        responder.createResponseBody(request.getPath());
        ArrayList body = new ArrayList<String>();
        body.add("This is a text file.");
        body.add("There are many like it, but this one is mine.");
        assertThat(responder.getResponseBody(), is(body));
    }

}

//* create map of statuses and reason_phrases,
//  Other things to test: headers get set up properly
// body set up properly
// sends correct data to socket*//