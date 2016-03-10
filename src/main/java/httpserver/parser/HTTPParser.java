package httpserver.parser;

import httpserver.parser.Parser;
import httpserver.request.HTTPRequest;
import httpserver.request.HTTPRequestBuilder;

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

    //Occasionaly gets index OutofBounds error - think it has something to do with buf not being clean
    private void parseRequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(" ");
        builder.method(splitRequestLine[0]);
        if (splitRequestLine[1].equals("/")) {
            builder.path(splitRequestLine[1]);
        } else {
            builder.path(splitRequestLine[1].replaceFirst("/", ""));
        }
        builder.version(splitRequestLine[2].substring(0, 8));
        if (splitRequestLine[0].equals("POST")) {
            bodyRequired = true;
        }
    }

    private void parseHeaders(String header) {
        String[] splitHeader = header.split(":");
        builder.headers(splitHeader[0], splitHeader[1].trim());
        if (splitHeader[0].equals("Content-Length")) {
            contentLength = Integer.parseInt(splitHeader[1].trim());
        }
    }

    private void parseBody(char[] body) {
        String stringBody = new String(body);
        String[] splitStringBody = stringBody.split("&");
        for (int i = 0; i < splitStringBody.length ; i++) {
            String[] keyValue = splitStringBody[i].split("=");
            builder.body(keyValue[0], keyValue[1]);
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
            char[] body = new char[1024];
            try {
                reader.read(body, 0, contentLength);
                parseBody(body);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.build();

    }
}


