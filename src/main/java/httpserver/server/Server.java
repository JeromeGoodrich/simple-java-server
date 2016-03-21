package httpserver.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ServerListener serverListener;
    private final ServiceFactory factory;

    public Server(ServerListener serverListener, ServiceFactory factory) {
        this.serverListener = serverListener;
        this.factory = factory;

    }



    public void startServer() throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        while (serverListener.isOpen()) {
            System.out.println("Waiting...");
            ClientConnection clientSocket = serverListener.accept();
            Runnable service = factory.createService(clientSocket);
            System.out.println("Accepted Connection");
            pool.execute(service);
        }
    }
}
