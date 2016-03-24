package httpserver.server;

import httpserver.handler.requesthandler.Router;
import httpserver.handler.responsehandler.HttpResponseHandler;
import httpserver.parser.HTTPParser;

public class Main {

    public static void main(String[] args) throws Exception {


        ServerConfig config = new ServerConfig(args);
        int portNumber = config.getPort();
        String rootDir = config.getRootDir();
        Server server = new Server(new RealServerListener(portNumber), new HttpServiceFactory(new Router(rootDir), new HTTPParser(), new HttpResponseHandler()));
        server.startServer();
    }

}
