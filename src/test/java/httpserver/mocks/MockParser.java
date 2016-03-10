package httpserver.mocks;

import httpserver.parser.Parser;

import java.io.InputStream;


public class MockParser implements Parser {
    public MockRequest parse(InputStream rawRequest) {
        return new MockRequest(rawRequest);
    }
}
