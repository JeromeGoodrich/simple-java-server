package httpserver.parser;

import httpserver.request.Request;

import java.io.InputStream;

public interface Parser {
    Request parse(InputStream rawRequest);

}
