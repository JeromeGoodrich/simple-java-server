package HTTPServer;

import org.junit.Test;

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
        request.setPath("/my_files");
        request.setVersion("1.1");
        responder.createStatusLine(request.getPath(), request.getVersion());
        assertThat(responder.getStatusLine(), is("HTTP/1.1 200 OK\r\n"));
    }
}
