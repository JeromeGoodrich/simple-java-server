package httpserver.server;

public interface ServiceFactory {
    Runnable createService(ClientConnection clientSocket);
}
