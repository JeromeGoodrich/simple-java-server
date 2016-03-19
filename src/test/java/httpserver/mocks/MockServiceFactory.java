package httpserver.mocks;

import httpserver.server.ClientSocket;
import httpserver.server.ClientSocketInterface;
import httpserver.server.Service;
import httpserver.server.ServiceFactory;

public class MockServiceFactory implements ServiceFactory {

    private MockService service;
    private ClientSocketInterface socket;

    public MockServiceFactory(MockService service, ClientSocketInterface socket) {
        this.service = service;
        this.socket = socket;
    }

    public Runnable createService(ClientSocketInterface clientSocket) {
        service.setSocket(socket);
        return service;
    }
}
