package HTTPServer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

public class Request {
    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;

    public Request() {
        this.headers = new HashMap<String, String>();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHeaders(HashMap headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getVersion() {
        return this.version;
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    private void parseRequestLine (String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        setMethod(splitRequestLine[0]);
        if (splitRequestLine[1].equals("/")) {
            setPath(splitRequestLine[1]);
        } else {
            setPath(splitRequestLine[1].replaceFirst("/",""));
        }
        setVersion(splitRequestLine[2].substring(0, 8));
    }

    private void parseHeaders (String[] rawRequestArray) {
        HashMap<String, String> newMap = new HashMap<String, String>();
        for (int i = 1; i < rawRequestArray.length; i++) {
            String[] splitHeaderArray = rawRequestArray[i].split(":");
            newMap.put(splitHeaderArray[0], splitHeaderArray[1]);
            setHeaders(newMap);
        }
    }

    public void parse(String rawRequest) throws Exception {
        String[] rawRequestArray = rawRequest.split("\n");
        parseRequestLine(rawRequestArray[0]);
        parseHeaders(rawRequestArray);

    }
}

