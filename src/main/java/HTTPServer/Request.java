package HTTPServer;

import java.util.Map;
import java.util.HashMap;

public class Request {
    private String method;
    private String path;
    private String version;
    private Map headers;

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

    public Map getHeaders() {
        return this.headers;
    }

    public void parse(String rawRequest) throws Exception {
        String[] rawRequestArray = rawRequest.split("\n");
        String[] requestString = rawRequestArray[0].split(" ");
        setMethod(requestString[0]);
        setPath(requestString[1]);
        setVersion(requestString[2].substring(5,8));
        HashMap newMap = new HashMap<String, String>();
        for (int i = 1; i < rawRequestArray.length; i++) {
           String[] splitHeaderArray = rawRequestArray[i].split(":");
            newMap.put(splitHeaderArray[0],splitHeaderArray[1]);
            setHeaders(newMap);
        }

    }
}

