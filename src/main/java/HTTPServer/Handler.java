package HTTPServer;

/**
 * Created by admin on 2/29/16.
 */
public interface Handler {
    Response handle(Request request);
}
