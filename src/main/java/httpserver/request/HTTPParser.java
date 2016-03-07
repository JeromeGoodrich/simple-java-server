package httpserver.request;

import sun.nio.cs.StandardCharsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPParser implements Parser {

    private Boolean bodyRequired = false;
    private int contentLength;
    private HTTPRequestBuilder builder = new HTTPRequestBuilder();
    private InputStreamReader reader;

    private String readLine() {
        StringBuilder stringBuilder = new StringBuilder();
        int charRead;
        try {
            while ((charRead = reader.read()) != -1) {
                    stringBuilder.append((char) charRead);
                    if ((char) charRead == '\n') break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void parseRequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        builder.method(splitRequestLine[0]);
        if (splitRequestLine[1].equals("/")) {
            builder.path(splitRequestLine[1]);
        } else {
            builder.path(splitRequestLine[1].replaceFirst("/", ""));
        }
        builder.version(splitRequestLine[2].substring(0, 8));
        if (splitRequestLine[0].equals("PUT")) {
            bodyRequired = true;
        }
    }

    private void parseHeaders(String header) {
        System.out.println(header);
        String[] splitHeader = header.split(":");
        builder.headers(splitHeader[0], splitHeader[1].trim());
        if (splitHeader[0].equals("Content-Length")) {
            contentLength = Integer.parseInt(splitHeader[1].trim());
        }
    }

    public HTTPRequest parse(InputStream rawRequest) {
        this.reader = new InputStreamReader(rawRequest);
        String requestLine = readLine();
        parseRequestLine(requestLine);

        String header;

        do {
            header = readLine();
            if (header.trim().isEmpty()) break;
            parseHeaders(header);
        } while (true);

        if (bodyRequired) {
            byte[] bytes = new byte[1024];
            try {
                rawRequest.read(bytes, 0, contentLength);
                builder.body(bytes);
                System.out.println(new String(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.build();

    }
}


