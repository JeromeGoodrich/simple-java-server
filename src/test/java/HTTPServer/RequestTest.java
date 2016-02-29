package HTTPServer;

import org.junit.Test;
import java.util.Map;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import HTTPServer.Request;

public class RequestTest {

    private Map testHeaders;

    public RequestTest() {
    }

    @Test
    public void testParseRequestLine() throws Exception {
        String rawRequest = "GET / HTTP/1.1\n";
        Request request = new Request();
        request.parse(rawRequest);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("/"));
        assertThat(request.getVersion(), is("1.1"));
    }

    @Test
    public void testParseHeaders() throws Exception {
        String rawRequest = "GET / HTTP/1.1\nHost: www.example.com\nAccept: */*";
        Request request = new Request();
        request.parse(rawRequest);

        assertThat(request.getHeader("Host"), is(" www.example.com"));
        assertThat(request.getHeader("Accept"), is(" */*"));
    }
}



