package httpserver.server;

public abstract class ServiceFactory {
    public abstract Service createService(ClientSocketInterface clientSocket);
}
