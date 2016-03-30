package httpserver.parser;

import httpserver.parser.HTTPParser;
import httpserver.parser.Parser;
import httpserver.request.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.containsString;
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
        assertThat(request.getBody(), containsString("firstname=hello&lastname=jerome"));
    }

    @Test
    public void testParseURLEncoding() throws IOException {
        byte[] data = ("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\nHost: www.example.com\r\n\r\n").getBytes();
        InputStream input = new ByteArrayInputStream(data);
        Parser parser = new HTTPParser();
        Request request = parser.parse(input);

        assertThat(request.getParams().get("variable_1"), is("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
        assertThat(request.getParams().get("variable_2"), is("stuff"));
    }

}



