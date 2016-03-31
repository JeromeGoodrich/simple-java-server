package httpserver;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

public class LogHandlerCreator {

    private final String logFilename;

    public LogHandlerCreator(String logFilename) {
        this.logFilename = logFilename;
    }

    public Handler createFileHandler() {
        FileHandler fh = null;
        try {
            fh = new FileHandler(logFilename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
        return fh;
    }

    public String getLogFilename() {
        return logFilename;
    }
}
