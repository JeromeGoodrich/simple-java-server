package httpserver.server;

public class Server {

    private ServerListener serverListener;
    private ClientSocketInterface clientSocket;
    private Service service;

    public Server(ServerListener serverListener, Service service) {
        this.serverListener = serverListener;
        this.service = service;

    }

    public void startServer() throws Exception {

        while (serverListener.isOpen()) {
            System.out.println("Waiting...");
            clientSocket = serverListener.accept();
            service.setSocket(clientSocket);
            System.out.println("Accepted Connection");
            Thread thread = new Thread(service);
            thread.start();
        }
    }
}
