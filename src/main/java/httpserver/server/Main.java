package httpserver.server;

import httpserver.logger.LogHandlerCreator;
import httpserver.logger.RequestLogger;
import httpserver.handler.*;
import httpserver.parser.HTTPParser;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ServerConfig config = new ServerConfig(args);
        int portNumber = config.getPort();
        String rootDir = config.getRootDir();

        LogHandlerCreator logHandlerCreator = new LogHandlerCreator("./server.log");
        RequestLogger logger = new RequestLogger(logHandlerCreator);

        List<Handler> handlers = new ArrayList<>();
        handlers.add(new BasicAuthHandler(logger));
        handlers.add(new DirHandler(rootDir));
        handlers.add(new PatchHandler(rootDir));
        handlers.add(new FileHandler(rootDir, logger));
        handlers.add(new OldFormHandler());
        handlers.add(new FormDataHandler());
        handlers.add(new PutHandler());
        handlers.add(new OptionsHandler());
        handlers.add(new RedirectHandler());
        handlers.add(new ParamsHandler());
        handlers.add(new NotFoundHandler());

        Server server = new Server(new RealServerListener(portNumber), new HttpServiceFactory(new Router(handlers), new HTTPParser(), logger));
        server.startServer();
    }

}
