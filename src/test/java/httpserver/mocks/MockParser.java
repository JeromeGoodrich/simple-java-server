package httpserver.mocks;

import httpserver.parser.Parser;
import httpserver.request.Request;

import java.io.InputStream;


public class MockParser implements Parser {

    private int callsToParse;

    public int getCallsToParse() {
        return  callsToParse;
    }

    public Request parse(InputStream rawRequest) {
        callsToParse ++;
        Request.RequestBuilder builder = new Request.RequestBuilder();
        return builder.build();
    }
}
