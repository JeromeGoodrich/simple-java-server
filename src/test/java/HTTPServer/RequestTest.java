package httpserver;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import httpserver.request.Request;

public class RequestTest {

    private Map testHeaders;

    public RequestTest() {
    }

    @Test
    public void testParseRequestLine() throws Exception {
        String rawRequest = "GET /src/test/fixtures HTTP/1.1\r\n";
        Request request = new Request();
        request.parse(rawRequest);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("src/test/fixtures"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
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



