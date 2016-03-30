package httpserver;

import httpserver.request.Request;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RequestLogger {

    public final static Logger logger = Logger.getLogger(RequestLogger.class.getName());

    private static FileHandler requests = null;

    public static void init() {
        try {
            requests = new FileHandler("logs.txt");
        } catch (SecurityException | IOException e) {
            RequestLogger.logger.log(Level.INFO,"Exception Raised", e);
        }
        Logger requestLogger = Logger.getLogger("");
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        requests.setFormatter(simpleFormatter);

        requestLogger.addHandler(requests);
        requestLogger.setLevel(Level.INFO);
    }

    public static void clearLogs(String fileName) {
        try {
            new FileWriter(fileName).write("");
        } catch (IOException e) {
            RequestLogger.logger.log(Level.INFO, "IOException Raised", e);
        }
    }

    public static byte[] accessLogs(String fileName) {
        byte[] bytes = null;
        File file = new File(fileName);
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
