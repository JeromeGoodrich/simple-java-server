package httpserver.request;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestTest {

    @Test
    public void testRequestGetters() {
        Request.RequestBuilder builder = new Request.RequestBuilder();
        builder.headers("Host", "www.example.com");
        builder.params("var1", "this");
        Request request = builder.method("GET").path("/").version("HTTP/1.1").body("hello world").build();

        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getVersion(), is("HTTP/1.1"));
        assertThat(request.getBody(), is("hello world"));
        assertThat(request.getHeader("Host"), is("www.example.com"));
        assertThat(request.getParams().get("var1"), is("this"));
    }
}
