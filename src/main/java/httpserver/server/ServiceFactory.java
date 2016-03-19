package httpserver.server;

public interface ServiceFactory {
    Runnable createService(ClientSocketInterface clientSocket);
}
