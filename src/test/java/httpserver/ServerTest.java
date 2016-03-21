package httpserver;

import httpserver.mocks.MockClientSocket;
import httpserver.mocks.MockServerListener;
import httpserver.mocks.MockService;
import httpserver.mocks.MockServiceFactory;
import httpserver.server.ClientConnection;
import httpserver.server.Server;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testServerLoop () throws Exception {
        byte[] bytes = new byte[1024];
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ClientConnection mockSocket = new MockClientSocket(inputStream);
        MockServerListener listener = new MockServerListener(mockSocket);;
        MockService service = new MockService();
        MockServiceFactory factory = new MockServiceFactory(service, mockSocket);
        Server server = new Server(listener, factory);
        assertThat(service.running, is(false));
        server.startServer();
        Thread.currentThread().sleep(20);
        assertThat(service.running, is(true));
        assertThat(service.getSocket(), is(mockSocket));
    }
}