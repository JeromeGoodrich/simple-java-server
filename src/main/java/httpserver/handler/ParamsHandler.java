package httpserver.handler;


import httpserver.request.Request;
import httpserver.response.Response;

import java.util.Map;

public class ParamsHandler implements Handler {

    public Response handle(Request request) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        byte[] data = responseParams(request.getParams());
        return builder.reasonPhrase().version(request.getVersion()).body(data).build();
    }

    private byte[] responseParams(Map<String,String> params) {
        String paramData = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = entry.getKey();
            String content = entry.getValue();
            String variable = name + " = " + content + "\n";
            paramData += variable;
        }
        return  paramData.getBytes();
    }


    public boolean willHandle(String method, String path) {
        return (method.equals("GET") && path.contains("parameters"));
    }
}
