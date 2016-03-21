package httpserver;

import httpserver.mocks.MockClientSocket;
import httpserver.mocks.MockRequestHandler;
import httpserver.mocks.MockResponseHandler;
import httpserver.mocks.MockParser;
import httpserver.request.Request;
import httpserver.server.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpServiceTest {

    @Test
    public void testServiceInputOutput() {
        byte[] bytes = new byte [1024];
        InputStream inputStream = new ByteArrayInputStream(bytes);
        MockClientSocket socket = new MockClientSocket(inputStream);
        MockRequestHandler requestHandler = new MockRequestHandler();
        MockParser parser = new MockParser();
        MockResponseHandler responseHandler = new MockResponseHandler();
        HttpServiceFactory factory = new HttpServiceFactory(requestHandler, parser, responseHandler);
        Runnable service = factory.createService(socket);
        service.run();
        assertThat(parser.getCallsToParse(), is(1));
        assertThat(socket.getInputStream(), is(inputStream));
        assertThat(parser.parse(socket.getInputStream()), instanceOf(Request.class));
        assertThat(requestHandler.getCallsToHandle(), is(1));
        assertThat(responseHandler.getCallsToHandle(), is(1));
        //assert on argument passed to handle, and parse
        // mockSocket.getInputStream for handle for instance
        //add exception case
    }
}
