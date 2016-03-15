package httpserver.mocks;

import httpserver.server.ClientSocketInterface;
import httpserver.server.Service;
import httpserver.server.ServiceFactory;

/**
 * Created by admin on 3/14/16.
 */
public class MockServiceFactory extends ServiceFactory {

    private Service service;

    public MockServiceFactory(Service service) {
        this.service = service;
    }
    @Override
    public Service createService(ClientSocketInterface clientSocket) {
        return service;
    }
}
