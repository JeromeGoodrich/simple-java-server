package httpserver.request;

import java.io.InputStream;

public interface Request {

    String getMethod();
    String getPath();
    String getVersion();
    String getHeader(String key);
    String getBodyVal(String key);
    InputStream getRawRequest();
}
