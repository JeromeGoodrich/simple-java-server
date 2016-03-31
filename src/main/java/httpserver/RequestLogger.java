package httpserver;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RequestLogger {

    private final String fileLocation;

    public RequestLogger(String fileLocation) {
        this.fileLocation = fileLocation;
        Logger requestLogger = Logger.getLogger("");
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        FileHandler requests = null;
        try {
            requests = new FileHandler(fileLocation);
        } catch (IOException e) {
            requestLogger.log(Level.INFO,"IO Exception", e);
        }
        requests.setFormatter(simpleFormatter);
        requestLogger.addHandler(requests);
        requestLogger.setLevel(Level.INFO);
    }

    public void log(Level level, String logContents) {
        Logger logger = Logger.getLogger(RequestLogger.class.getName());
        logger.log(level,logContents);
    }

    public void error(Level level, String logContents, Exception e) {
        Logger logger = Logger.getLogger(RequestLogger.class.getName());
        logger.log(level, logContents, e);

    }

    public byte[] accessLogs() {
        byte[] bytes = null;
        File file = new File(fileLocation);
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
