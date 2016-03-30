package httpserver.server;

import httpserver.RequestLogger;
import httpserver.handler.Router;
import httpserver.parser.HTTPParser;

public class Main {

    public static void main(String[] args) throws Exception {

        RequestLogger.init();
        ServerConfig config = new ServerConfig(args);
        int portNumber = config.getPort();
        String rootDir = config.getRootDir();
        Server server = new Server(new RealServerListener(portNumber), new HttpServiceFactory(new Router(rootDir), new HTTPParser()));
        server.startServer();
    }

}
