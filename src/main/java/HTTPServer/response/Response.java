package httpserver.response;

import java.io.InputStream;

public interface Response {
    InputStream getRawRequest();

    String getVersion();

    int getStatusCode();

    String getReasonPhrase();

    byte[] getBody();
}
