package httpserver.request;

import java.io.InputStream;

public class MockRequest extends Request {

    private InputStream rawRequest;

    public MockRequest(InputStream rawRequest) {
        this.rawRequest = rawRequest;
    }

    public InputStream getRawRequest() {
        return rawRequest;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getHeader(String key) {
        return null;
    }

    @Override
    public String getBodyVal(String key) {
        return null;
    }
}