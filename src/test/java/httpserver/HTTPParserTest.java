package httpserver;

import httpserver.parser.HTTPParser;
import httpserver.parser.Parser;
import httpserver.request.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

public class HTTPParserTest {

    @Test
    public void testParseRequestLine() throws Exception {
        byte[] data = "GET / HTTP/1.1\r\nHost: www.example.com\r\n\r\n".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("/"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
    }

   @Test
    public void testParseHeaders() throws Exception {
        byte[] data = "GET /src/test/fixtures HTTP/1.1\r\nHost: www.example.com\r\nAccept: */*\r\n\r\n".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("src/test/fixtures"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
        assertThat(request.getHeader("Host"), is("www.example.com"));
        assertThat(request.getHeader("Accept"), is("*/*"));
    }

    @Test
    public void testParseBody() throws IOException {
        byte[] data = "POST /form HTTP/1.1\r\nHost: www.example.com\r\nContent-Length: 31\r\n\r\nfirstname=hello&lastname=jerome".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);
        assertThat(request.getMethod(), is("POST"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
        assertThat(request.getPath(), is("form"));
        assertThat(request.getHeader("Host"), is("www.example.com"));
        assertThat(request.getHeader("Content-Length"), is("31"));
        //assertThat(request.getBody(), is("firstname=hello&lastname=jerome"));
    }

}



