package httpserver.server;

import httpserver.logger.LogHandlerCreator;
import httpserver.logger.RequestLogger;
import httpserver.mocks.MockClientSocket;
import httpserver.mocks.MockHandler;
import httpserver.mocks.MockParser;
import httpserver.request.Request;
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
        LogHandlerCreator lhc = new LogHandlerCreator("test.log");
        RequestLogger logger = new RequestLogger(lhc);
        MockHandler requestHandler = new MockHandler();
        MockParser parser = new MockParser();
        HttpServiceFactory factory = new HttpServiceFactory(requestHandler, parser, logger);
        Runnable service = factory.createService(socket);

        assertThat(parser.getCallsToParse(), is(0));
        assertThat(requestHandler.getCallsToHandle(), is(0));
        assertThat(socket.isClosed(), is(false));

        service.run();

        assertThat(parser.getCallsToParse(), is(1));
        assertThat(socket.getInputStream(), is(inputStream));
        assertThat(parser.parse(socket.getInputStream()), instanceOf(Request.class));
        assertThat(requestHandler.getCallsToHandle(), is(1));
        assertThat(socket.isClosed(), is(true));
    }
}
