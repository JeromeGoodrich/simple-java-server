package httpserver.server;

import httpserver.handler.Handler;
import httpserver.parser.Parser;

public class HttpServiceFactory implements ServiceFactory {

    private Handler handler;
    private Parser parser;

    public HttpServiceFactory(Handler requestHandler, Parser parser) {
        this.handler = requestHandler;
        this.parser = parser;
    }

    public Runnable createService(ClientConnection clientSocket) {
        return new HttpService(handler, parser, clientSocket);
    }
}
