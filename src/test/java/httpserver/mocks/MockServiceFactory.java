package httpserver.mocks;

import httpserver.server.ClientConnection;
import httpserver.server.ServiceFactory;

public class MockServiceFactory implements ServiceFactory {

    private MockService service;
    private ClientConnection socket;

    public MockServiceFactory(MockService service, ClientConnection socket) {
        this.service = service;
        this.socket = socket;
    }

    public Runnable createService(ClientConnection clientSocket) {
        service.setSocket(socket);
        return service;
    }
}
