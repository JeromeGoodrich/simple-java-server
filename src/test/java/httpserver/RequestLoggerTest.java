package httpserver;

import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLoggerTest {

    @Test
    public void initAccessAndClearLogsTest() {
        RequestLogger logger = new RequestLogger("testlog.txt");
        logger.log(Level.INFO, "request1");
        logger.log(Level.INFO,"request2");
        byte[] logData = logger.accessLogs();

        assertThat(new String(logData), containsString("request1"));
        assertThat(new String(logData), containsString("request2"));
    }
}
