package httpserver.handler;

import httpserver.Base64Decoder;
import httpserver.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.*;

public class BasicAuthHandler implements Handler {

    private final RequestLogger logger;

    public BasicAuthHandler(RequestLogger logger) {
        this.logger = logger;
    }

    public Response handle(Request request) {
        if (request.getHeader("Authorization") != null) {
            Base64Decoder decoder = new Base64Decoder();
            String decodedCredentials = decoder.decode(request.getHeader("Authorization"));
            String username = decodedCredentials.split(":")[0];
            String password = decodedCredentials.split(":")[1];
            if (username.equals("admin") && password.equals("hunter2")) {
                return accessGranted(request);
            } else {
                return accessDenied(request);
            }
        } else {
            return accessDenied(request);
        }
    }

    private Response accessDenied(Request request) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(401);
        builder.addHeader("WWW-Authenticate", "Basic realm=\"Camelot\"");
        return builder.version(request.getVersion()).reasonPhrase().build();
    }

    private Response accessGranted(Request request) {
        byte[] bytes = logger.accessLogs();
        return new Response.ResponseBuilder(200).reasonPhrase().body(bytes).version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (path.equals("logs"));
    }
}
