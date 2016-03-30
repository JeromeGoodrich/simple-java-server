package httpserver;

import org.junit.Test;

import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestLoggerTest {

    @Test
    public void initAccessAndClearLogsTest() {
        String logLocation = "logs.txt";
        RequestLogger.init();
        RequestLogger.logger.log(Level.INFO, "request1");
        RequestLogger.logger.log(Level.INFO,"request2");
        byte[] logData = RequestLogger.accessLogs(logLocation);

        assertThat(new String(logData), containsString("request1"));
        assertThat(new String(logData), containsString("request2"));

        RequestLogger.clearLogs(logLocation);
        byte[] emptyData = RequestLogger.accessLogs(logLocation);

        assertThat(new String(emptyData), is(""));
    }
}
