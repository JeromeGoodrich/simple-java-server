package httpserver.logger;

import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLoggerTest {

    @Test
    public void initAccessAndClearLogsTest() {
        LogHandlerCreator logHandlerCreator = new LogHandlerCreator("test.log");
        RequestLogger logger = new RequestLogger(logHandlerCreator);
        logger.log(Level.INFO, "request1");
        logger.log(Level.INFO,"request2");
        logger.error(Level.INFO, "Exception Raised", new Exception());
        byte[] logData = logger.accessLogs();

        assertThat(new String(logData), containsString("request1"));
        assertThat(new String(logData), containsString("request2"));
        assertThat(new String(logData), containsString("Exception Raised"));
    }
}
