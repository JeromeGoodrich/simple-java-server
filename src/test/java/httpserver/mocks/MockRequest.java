package httpserver.mocks;

import httpserver.request.Request;

import java.io.InputStream;

public class MockRequest implements Request {

    private InputStream rawRequest;

    public MockRequest(InputStream rawRequest) {
        this.rawRequest = rawRequest;
    }

    public InputStream getRawRequest() {
        return rawRequest;
    }

    public String getMethod() {
        return null;
    }

    public String getPath() {
        return null;
    }

    public String getVersion() {
        return null;
    }


    public String getHeader(String key) {
        return null;
    }


    public String getBodyVal(String key) {
        return null;
    }
}