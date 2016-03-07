package httpserver;

import httpserver.request.*;
import org.junit.Test;
import org.junit.validator.ValidateWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestTest {

    @Test
    public void testParseRequestLine() throws Exception {
        byte[] data = "GET / HTTP/1.1".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("/"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
    }

   @Test
    public void testParseHeaders() throws Exception {
        byte[] data = "GET /src/test/fixtures HTTP/1.1\r\nHost: www.example.com\r\nAccept: */*\r\n".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("src/test/fixtures"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
        assertThat(request.getHeader("Host"), is("www.example.com"));
        assertThat(request.getHeader("Accept"), is("*/*"));
    }


}



