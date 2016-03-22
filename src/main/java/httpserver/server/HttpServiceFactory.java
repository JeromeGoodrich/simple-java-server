package httpserver.server;

import httpserver.handler.requesthandler.Handler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.parser.Parser;

public class HttpServiceFactory implements ServiceFactory {

    private Handler requestHandler;
    private Parser parser;
    private ResponseHandler responseHandler;

    public HttpServiceFactory(Handler requestHandler, Parser parser, ResponseHandler responseHandler) {
        this.requestHandler = requestHandler;
        this.parser = parser;
        this.responseHandler = responseHandler;
    }

    public Runnable createService(ClientConnection clientSocket) {
        return new HttpService(requestHandler, parser, responseHandler, clientSocket);
    }
}
