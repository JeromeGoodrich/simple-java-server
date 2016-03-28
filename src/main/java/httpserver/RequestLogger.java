package httpserver;

import httpserver.request.Request;

import java.io.FileWriter;
import java.io.IOException;

public class RequestLogger {

    public void log(Request request) {
       String requestLine = request.getMethod() +
                            " /" + request.getPath() + " " +
                            request.getVersion()+ "\r\n";
        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/logs.txt", true);
            writer.write(requestLine);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
