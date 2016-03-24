//package httpserver;
//
//import httpserver.handler.requesthandler.Router;
//import httpserver.request.Request;
//import httpserver.response.Response;
//import org.junit.Test;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.Matchers.contains;
//import static org.junit.Assert.assertThat;
//import static org.hamcrest.CoreMatchers.is;
//
//public class HttpHandlerTest {
//
//    @Test
//    public void testHandleRoot() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("/").version("HTTP/1.1").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.getVersion(), is("HTTP/1.1"));
//        assertThat(response.getReasonPhrase(), is("OK"));
//        assertThat(new String(response.getBody()), is("Hello World"));
//    }
//
//    @Test
//    public void testHandleHTMLDir() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("src/test/fixtures").headers("Accept","*/*").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.getReasonPhrase(), is("OK"));
//        assertThat(new String(response.getBody()), containsString("a href="));
//        }
//
//    @Test
//    public void handleJSONdirListing() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("src/").headers("Accept", "application/json").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.getReasonPhrase(), is("OK"));
//        assertThat(new String(response.getBody()), containsString("{\"directories\":{"));
//    }
//
//    @Test
//    public void testHandleFile() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("src/test/fixtures/my_file.txt").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(response.getReasonPhrase(), is("OK"));
//        assertThat(response.getHeaderValue("Content-Type"), is("text/plain"));
//        assertThat(response.getHeaderValue("Content-Length"), is("66"));
//        assertThat(new String(response.getBody()), is("This is a text file.\nThere are many like it, but this one is mine."));
//    }
//
//    @Test
//    public void testGetError() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("this/is/not/a/path").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(404));
//        assertThat(response.getReasonPhrase(), is("Not Found"));
//    }
//
//    @Test
//    public void testHandleForm() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("GET").path("form").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(new String(response.getBody()), containsString("</form>"));
//    }
//
//    @Test
//    public void testHandlePost() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("POST").path("form").body("firstname", "Jerome").body("lastname", "Goodrich").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(200));
//        assertThat(new String(response.getBody()), containsString("Jerome"));
//        assertThat(new String(response.getBody()), containsString("Goodrich"));
//    }
//
//    @Test
//    public void testPostError() {
//        Request.RequestBuilder builder = new Request.RequestBuilder();
//        Request request = builder.method("POST").path("this/is/not/a/path").build();
//        Router handler = new Router(System.getProperty("user.dir"));
//        Response response = handler.handle(request);
//        assertThat(response.getStatusCode(), is(404));
//        assertThat(response.getReasonPhrase(), is("Not Found"));
//
//    }
//
////    @Test
////    public void testUnsupportedMethod() {
////        Request.RequestBuilder builder = new Request.RequestBuilder();
////        Request request = builder.method("CONNECT").path("/").build();
////        Router handler = new Router();
////        Response response = handler.handle(request);
////        assertThat(response.getStatusCode(), is(404));
////        assertThat(response.getReasonPhrase(), is("Not Found"));
////
////    }
//}