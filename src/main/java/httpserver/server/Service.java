package httpserver.server;

public abstract class Service implements Runnable {
    public abstract void setSocket(ClientSocketInterface socket);
}
