package httpserver.request;

import java.io.InputStream;

public interface Parser {
    public Request parse(InputStream rawRequest);


}
