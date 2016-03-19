package httpserver.server;

import httpserver.handler.requesthandler.RequestHandler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.parser.Parser;

public class HttpServiceFactory implements ServiceFactory {

    private RequestHandler requestHandler;
    private Parser parser;
    private ResponseHandler responseHandler;

    public HttpServiceFactory(RequestHandler requestHandler, Parser parser, ResponseHandler responseHandler) {
        this.requestHandler = requestHandler;
        this.parser = parser;
        this.responseHandler = responseHandler;
    }

    public Runnable createService(ClientSocketInterface clientSocket) {
        return new HttpService(requestHandler, parser, responseHandler, clientSocket);
    }
}
