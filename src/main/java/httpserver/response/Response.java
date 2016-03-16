package httpserver.response;

import java.io.InputStream;
import java.util.Map;

public interface Response {
    InputStream getRawRequest();

    String getVersion();

    int getStatusCode();

    String getReasonPhrase();

    byte[] getBody();

    Map<String, String> getHeaders();

    String getHeaderValue(String s);
}
