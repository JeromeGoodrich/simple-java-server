package httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;

public class RequestLogger {

    private final LogHandlerCreator handlerCreator;
    private final Handler handler;

    public RequestLogger(LogHandlerCreator handlerCreator) {
        this.handlerCreator = handlerCreator;
        this.handler = handlerCreator.createFileHandler();
        Logger requestLogger = Logger.getLogger("");
        requestLogger.addHandler(handler);
        requestLogger.setLevel(Level.INFO);
    }

    public void log(Level level, String logContents) {
        Logger logger = Logger.getLogger(RequestLogger.class.getName());
        logger.log(level,logContents);
        handler.close();
    }

    public void error(Level level, String logContents, Exception e) {
        Logger logger = Logger.getLogger(RequestLogger.class.getName());
        logger.log(level, logContents, e);
        handler.close();
    }

    public byte[] accessLogs() {
        byte[] bytes = null;
        File file = new File(handlerCreator.getLogFilename());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int fileLength = (int) file.length();
            bytes = new byte[fileLength];
            inputStream.read(bytes, 0, fileLength);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
