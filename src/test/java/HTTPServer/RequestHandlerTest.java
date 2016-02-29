package HTTPServer;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RequestHandlerTest {

    @Test
    public void testHandleRoot() {
        Request request = new Request();
        RequestHandler handler = new RequestHandler();
        request.setPath(Paths.get("/"));
        Response response = handler.handle(request);
        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), is("Hello World"));
    }


}