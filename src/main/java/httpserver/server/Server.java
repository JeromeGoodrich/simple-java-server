package httpserver.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerListener serverListener;
    private ClientSocketInterface clientSocket;
    private Service service;
    private ServiceFactory factory;

    public Server(ServerListener serverListener, ServiceFactory factory) {
        this.serverListener = serverListener;
        this.factory = factory;

    }

    public ClientSocketInterface getSocket() {
        return clientSocket;
    }

    public void startServer() throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        while (serverListener.isOpen()) {
            System.out.println("Waiting...");
            clientSocket = serverListener.accept();
            service = factory.createService(clientSocket);
            System.out.println("Accepted Connection");
            pool.execute(service);
        }
    }
}
