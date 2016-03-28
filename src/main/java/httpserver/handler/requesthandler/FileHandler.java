package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;;

public class FileHandler implements Handler {

    private final String rootDir;

    public FileHandler(String rootDir) {
        this.rootDir = rootDir;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public Response handle(Request request) {
        String fileName = rootDir + request.getPath();
        if (request.getMethod().equals("GET")) {
            if (request.getHeader("Range") != null) {
                String rawRange = request.getHeader("Range");
                byte[] bytes = readPartiallyFromFile(fileName, rawRange);
                return new ResponseBuilder(206).version(request.getVersion()).reasonPhrase().body(bytes).build();
            } else {
                byte[] bytes = null;
                String mimeType = URLConnection.guessContentTypeFromName(request.getPath());
                if (mimeType == null) mimeType = "text/plain";
                ResponseBuilder builder = new ResponseBuilder(200);
                builder.addHeader("Content-Type", mimeType);
                try {
                    bytes = Files.readAllBytes(Paths.get(rootDir + request.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                builder.addHeader("Content-Length", Integer.toString(bytes.length));
                if (builder.headers.get("Content-Type").equals("application/pdf") && Integer.parseInt(builder.headers.get("Content-Length")) > 10485760) {
                    builder.addHeader("Content-Disposition", "attachment; filename=\"big-pdf.pdf\"");
                }
                return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
            }
        } else {
            ResponseBuilder builder = new ResponseBuilder(405);
            return builder.reasonPhrase().version(request.getVersion()).build();
        }
    }


    public boolean willHandle(String method, String path) {
        return new File(rootDir + path).isFile();
    }
}
