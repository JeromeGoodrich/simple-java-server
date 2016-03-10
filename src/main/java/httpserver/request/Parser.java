package httpserver.request;

import java.io.InputStream;

public interface Parser {
    Request parse(InputStream rawRequest);

}
