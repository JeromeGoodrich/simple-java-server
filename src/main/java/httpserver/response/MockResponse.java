package httpserver.response;


import java.io.InputStream;

public class MockResponse implements Response {

    private InputStream rawRequest;

    public MockResponse(InputStream rawRequest) {
        this.rawRequest = rawRequest;
    }

    public InputStream getRawRequest() {
        return rawRequest;
    }

    public String getVersion() {
        return null;
    }

    public int getStatusCode() {
        return 0;
    }

    public String getReasonPhrase() {
        return null;
    }

    public byte[] getBody() {
        return new byte[0];
    }
}

