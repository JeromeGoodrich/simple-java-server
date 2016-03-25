package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;


public class FormDataHandler implements Handler {
    private String data;

    public Response handle(Request request) {
        if (request.getMethod().equals("GET")) {
            return handleGet(request);
        } else if (request.getMethod().equals("DELETE")) {
            return handleDelete(request);
        } else {
            return handlePostPut(request);
        }
    }

    private Response handleDelete(Request request) {
        this.data = null;
        return new ResponseBuilder(200)
                .reasonPhrase()
                .version(request.getVersion())
                .build();
    }

    private Response handlePostPut(Request request) {
        System.out.println(request.getBody());
        this.data = request.getBody();
        return new ResponseBuilder(200)
                .reasonPhrase()
                .version(request.getVersion())
                .build();
    }

    private Response handleGet(Request request) {
        if (data != null) {
            return new ResponseBuilder(200)
                .reasonPhrase()
                .version(request.getVersion())
                .body(this.data.getBytes())
                .build();
        } else {
            return new ResponseBuilder(200)
                .reasonPhrase()
                .version(request.getVersion())
                .build();
        }
    }

    public boolean willHandle(String method, String path) {
        return path.equals("form");
    }
}
