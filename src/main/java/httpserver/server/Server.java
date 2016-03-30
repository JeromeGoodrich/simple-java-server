package httpserver.server;

import httpserver.RequestLogger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ServerListener serverListener;
    private final ServiceFactory factory;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public Server(ServerListener serverListener, ServiceFactory factory) {
        this.serverListener = serverListener;
        this.factory = factory;

    }



    public void startServer() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                    RequestLogger.clearLogs("logs.txt");
                    pool.shutdown();
            }
        });

        while (serverListener.isOpen()) {
            System.out.println("Waiting...");
            ClientConnection clientSocket = serverListener.accept();
            Runnable service = factory.createService(clientSocket);
            System.out.println("Accepted Connection");
            pool.execute(service);
        }
    }
}
