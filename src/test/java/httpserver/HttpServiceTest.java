package httpserver;

import httpserver.mocks.MockClientSocket;
import httpserver.mocks.MockRequestHandler;
import httpserver.mocks.MockResponseHandler;
import httpserver.mocks.MockParser;
import httpserver.server.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpServiceTest {

    @Test
    public void testServiceInputOutput() {
        MockClientSocket socket = new MockClientSocket();
        MockRequestHandler requestHandler = new MockRequestHandler();
        MockParser parser = new MockParser();
        MockResponseHandler responseHandler = new MockResponseHandler();
        HttpServiceFactory factory = new HttpServiceFactory(requestHandler, parser, responseHandler);
        Service service = factory.createService(socket);
        service.run();
        assertThat(parser.getCallsToParse(), is(1));
        assertThat(requestHandler.getCallsToHandle(), is(1));
        assertThat(responseHandler.getCallsToHandle(), is(1));
    }
}
