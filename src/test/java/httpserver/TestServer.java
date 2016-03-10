package httpserver;

import httpserver.mocks.MockServerListener;
import httpserver.mocks.MockService;
import httpserver.server.Server;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestServer {

    @Test
    public void testServerLoop () throws Exception {
        MockServerListener listener = new MockServerListener();
        MockService service = new MockService();
        Server server = new Server(listener, service);
        assertThat(service.running, is(nullValue()));
        server.startServer();
        assertThat(service.running, is(true));
        assertThat(service.output, is("data"));
        //server loop gets called multiple times to complete all calls
        //seems wrong
    }
}
