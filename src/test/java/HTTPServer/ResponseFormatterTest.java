package HTTPServer;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by admin on 3/1/16.
 */
public class ResponseFormatterTest {

    @Test
    public void TestCorrectOutput() {
        ResponseBuilder builder = new ResponseBuilder(200);
        Response response = builder.version("HTTP/1.1").reasonPhrase().body("Hello World".getBytes()).build();
        ResponseFormatter formatter = new ResponseFormatter();
        assertThat( new String(formatter.format(response)), is("HTTP/1.1 200 OK\r\n\r\nHello World"));
    }
}
