package httpserver;

import httpserver.handler.requesthandler.Handler;
import httpserver.handler.requesthandler.ParamsHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParamsHandlerTest {

    @Test
    public void willHandleTest() {
        Handler handler = new ParamsHandler() ;
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/parameters?value1=1&value2=2")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(true));
    }

    @Test
    public void willnotHandleTest() {
        Handler handler = new ParamsHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/")
                .build();
        assertThat(handler.willHandle(request.getMethod(), request.getPath()), is(false));
    }

    @Test
    public void paramsHandlerTest() {
        Handler handler = new ParamsHandler();
        Request request = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%" +
                        "20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff")
                .params("variable_1","Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?")
                .params("variable_2", "stuff")
                .build();
        Response response = handler.handle(request);

        assertThat(response.getStatusCode(), is(200));
        assertThat(new String(response.getBody()), containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
        assertThat(new String(response.getBody()), containsString("variable_2 = stuff"));
    }

}
