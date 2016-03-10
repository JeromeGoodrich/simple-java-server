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
        String input = "Hello World";
        MockClientSocket socket = new MockClientSocket(input);
        MockRequestHandler requestHandler = new MockRequestHandler();
        MockParser parser = new MockParser();
        MockResponseHandler responseHandler = new MockResponseHandler();
        Service service = new HttpService(requestHandler, parser, responseHandler);
        service.setSocket(socket);
        service.run();
        assertThat(responseHandler.outputString, is(input));
    }
}
