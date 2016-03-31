package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FormHandlerTest {

    @Test
    public void willHandleFormTest() {
        Handler handler = new FormDataHandler();
        Request request = new Request.RequestBuilder()
                .method("POST")
                .version("HTTP/1.1")
                .path("/form")
                .body("data=fatcat")
                .build();

        assertEquals(true, handler.willHandle(request.getMethod(), request.getPath()));
    }

    @Test
    public void willNotHandleTest() {
        Handler handler = new FormDataHandler();
        Request request = new Request.RequestBuilder()
                .method("POST")
                .version("HTTP/1.1")
                .path("/foo")
                .build();

        assertEquals(false, handler.willHandle(request.getMethod(), request.getPath()));
    }

    @Test
    public void postFormDataTest() {
        Handler handler = new FormDataHandler();
        Request postRequest = new Request.RequestBuilder()
                .method("POST")
                .version("HTTP/1.1")
                .path("/form")
                .body("data=fatcat")
                .build();
        Response postResponse = handler.handle(postRequest);

        assertEquals(200, postResponse.getStatusCode());

        Request getRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/form")
                .build();

        Response getResponse = handler.handle(getRequest);

        assertEquals(200, getResponse.getStatusCode());
        assertEquals("data=fatcat", new String(getResponse.getBody()));
    }

    @Test
    public void putFormDataTest() {
        Handler handler = new FormDataHandler();
        Request putRequest = new Request.RequestBuilder()
                .method("PUT")
                .version("HTTP/1.1")
                .path("/form")
                .body("data=heathcliff")
                .build();
        Response putResponse = handler.handle(putRequest);

        assertEquals(200, putResponse.getStatusCode());

        Request getRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/form")
                .build();
        Response getResponse = handler.handle(getRequest);

        assertEquals("data=heathcliff", new String(getResponse.getBody()));

        Request deleteRequest = new Request.RequestBuilder()
                .method("DELETE")
                .version("HTTP/1.1")
                .path("/form")
                .build();
        Response deleteResponse = handler.handle(deleteRequest);

        assertEquals(200, deleteResponse.getStatusCode());

        Request newGetRequest = new Request.RequestBuilder()
                .method("GET")
                .version("HTTP/1.1")
                .path("/form")
                .build();
        Response newGetResponse = handler.handle(newGetRequest);

        assertEquals(null, newGetResponse.getBody());

    }
}
