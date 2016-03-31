package httpserver.handler;

import httpserver.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public class FileHandler implements Handler {

    private final String rootDir;
    private final RequestLogger logger;

    public FileHandler(String rootDir, RequestLogger logger) {
        this.rootDir = rootDir;
        this.logger = logger;
    }

    private byte[] readPartiallyFromFile(String fileName, String rawRange) {
        File file = new File(fileName);
        int fileLength = ((int) file.length());
        int rangeStart;
        int rangeEnd;
        String stringRange = rawRange.split("=")[1];
        if (stringRange.endsWith("-")) {
            String stringRangeStart = stringRange.split("-")[0];
            rangeStart = Integer.parseInt(stringRangeStart);
            rangeEnd = fileLength;
        } else if (stringRange.startsWith("-")) {
            rangeEnd = fileLength;
            String stringBytesTilEnd = stringRange.split("-")[1];
            rangeStart = (fileLength - Integer.parseInt(stringBytesTilEnd));
        } else {
            String stringRangeStart = stringRange.split("-")[0];
            String stringRangeEnd = stringRange.split("-")[1];
            rangeStart = Integer.parseInt(stringRangeStart);
            rangeEnd = Integer.parseInt(stringRangeEnd)+1;
        }

        int range = rangeEnd - rangeStart;
        return readFromFile(file, rangeStart, range);
    }

    private byte[] readFromFile (File file, int rangeStart, int range) {
        byte[] fileContent = new byte[range];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.skip(rangeStart);
            inputStream.read(fileContent, 0, range);
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.error(Level.INFO, "File can not be located", e);
        } catch (IOException e) {
            logger.error(Level.INFO, "IO Exception",e);
        }
        return fileContent;
    }

    private Response handlePartialContent(Request request) {
        String rawRange = request.getHeader("Range");
        byte[] bytes = readPartiallyFromFile(rootDir + request.getPath(), rawRange);
        return new Response.ResponseBuilder(206).version(request.getVersion()).reasonPhrase().body(bytes).build();
    }

    private Response handleFile(Request request) {
        byte[] bytes = null;
        String mimeType = URLConnection.guessContentTypeFromName(request.getPath());
        if (mimeType == null) mimeType = "text/plain";
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        builder.addHeader("Content-Type", mimeType);
        try {
            bytes = Files.readAllBytes(Paths.get(rootDir + request.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.addHeader("Content-Length", Integer.toString(bytes.length));
        if (builder.getHeader("Content-Type").equals("application/pdf") && Integer.parseInt(builder.getHeader("Content-Length")) > 10485760) {
            builder.addHeader("Content-Disposition", "attachment; filename=\"big-pdf.pdf\"");
        }
        return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
    }

    public Response handle(Request request) {
        if (request.getMethod().equals("GET")) {
            if (request.getHeader("Range") != null) {
               return handlePartialContent(request);
            } else {
                return handleFile(request);
            }
        } else {
            Response.ResponseBuilder builder = new Response.ResponseBuilder(405);
            return builder.reasonPhrase().version(request.getVersion()).build();
        }
    }


    public boolean willHandle(String method, String path) {
        return new File(rootDir + path).isFile();
    }
}
