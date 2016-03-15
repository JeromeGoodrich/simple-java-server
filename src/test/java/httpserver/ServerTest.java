package httpserver;

import httpserver.mocks.MockClientSocket;
import httpserver.mocks.MockServerListener;
import httpserver.mocks.MockService;
import httpserver.mocks.MockServiceFactory;
import httpserver.server.ClientSocketInterface;
import httpserver.server.Server;
import httpserver.server.Service;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testServerLoop () throws Exception {
        ClientSocketInterface mockSocket = new MockClientSocket();
        MockServerListener listener = new MockServerListener(mockSocket);
        MockService service = new MockService();
        MockServiceFactory factory = new MockServiceFactory(service);
        Server server = new Server(listener, factory);
        assertThat(service.running, is(false));
        server.startServer();
        assertThat(service.running, is(true));
        assertThat(server.getSocket(), is(mockSocket));
        //server loop gets called multiple times to complete all calls
        //seems wrong
    }
}
