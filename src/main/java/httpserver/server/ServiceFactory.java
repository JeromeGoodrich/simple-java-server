package httpserver.server;

import httpserver.handler.RequestHandler;
import httpserver.request.HTTPParser;

import java.net.Socket;

public class ServiceFactory {
    public Service createHTTPService(Socket socket) {
        return new Service(new HTTPIO(socket), new RequestHandler(),new HTTPParser());
    }
}
