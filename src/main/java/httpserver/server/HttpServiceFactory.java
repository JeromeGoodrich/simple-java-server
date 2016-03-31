package httpserver.server;

import httpserver.RequestLogger;
import httpserver.handler.Handler;
import httpserver.parser.Parser;
import httpserver.request.Request;

public class HttpServiceFactory implements ServiceFactory {

    private final Handler handler;
    private final Parser parser;
    private final RequestLogger logger;

    public HttpServiceFactory(Handler requestHandler, Parser parser, RequestLogger logger) {
        this.handler = requestHandler;
        this.parser = parser;
        this.logger = logger;
    }

    public Runnable createService(ClientConnection clientSocket) {
        return new HttpService(handler, parser, logger, clientSocket);
    }
}
