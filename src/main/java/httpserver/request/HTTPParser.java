package httpserver.request;

import sun.nio.cs.StandardCharsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPParser implements Parser {

    HTTPRequestBuilder builder = new HTTPRequestBuilder();

    private String readRequest(InputStream rawRequest) {
        InputStreamReader reader = new InputStreamReader(rawRequest);
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
        builder.path(splitRequestLine[1].replaceFirst("/",""));
        builder.version(splitRequestLine[2].substring(0, 8));
    }

    private void parseHeaders(String header) {
        String[] splitHeader = header.split(":");
        builder.headers(splitHeader[0], splitHeader[1]);
    }

    public HTTPRequest parse(InputStream rawRequest) {
        String requestLine = readRequest(rawRequest);
        parseRequestLine(requestLine);

        String header;

        do {
            header = readRequest(rawRequest);
            if (header.isEmpty()) break;
            parseHeaders(header);
        } while (true);

        return builder.build();
    }
}


