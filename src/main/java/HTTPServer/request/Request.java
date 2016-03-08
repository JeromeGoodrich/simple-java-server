package httpserver.request;

public abstract class Request {

public abstract String getMethod();

public abstract String getPath();

public abstract String getVersion();

public abstract String getHeader(String key);

public abstract String getBodyVal(String key);
}
