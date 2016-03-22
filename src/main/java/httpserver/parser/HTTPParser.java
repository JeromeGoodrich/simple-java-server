package httpserver.parser;

import httpserver.request.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPParser implements Parser {

    private String readLine(InputStreamReader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        int charRead;
        try {
            while ((charRead = reader.read()) >= 0 || stringBuilder.length() < 1) {
                stringBuilder.append((char) charRead);
                if ((char) charRead == '\n') break ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void parseRequestLine(String requestLine, Request.RequestBuilder builder) {
        String[] splitRequestLine = requestLine.split(" ");
        builder.method(splitRequestLine[0]);
        if (splitRequestLine[1].equals("/")) {
            builder.path(splitRequestLine[1]);
        } else {
            builder.path(splitRequestLine[1].replaceFirst("/", ""));
        }
        builder.version(splitRequestLine[2].substring(0, 8));
    }

    private int parseHeaders(String header, Request.RequestBuilder builder) {
        String[] splitHeader = header.split(":");
        builder.headers(splitHeader[0], splitHeader[1].trim());
        if (splitHeader[0].equals("Content-Length")) {
            return Integer.parseInt(splitHeader[1].trim());
        } else {
            return 0;
        }
    }

    private void parseBody(char[] body, Request.RequestBuilder builder) {
        String stringBody = new String(body);
        String[] splitStringBody = stringBody.split("&");
        for (int i = 0; i < splitStringBody.length ; i++) {
            String[] keyValue = splitStringBody[i].split("=");
            builder.body(keyValue[0], keyValue[1]);
        }
    }

    public Request parse(InputStream rawRequest) throws IOException {
        Request.RequestBuilder builder = new Request.RequestBuilder();
        InputStreamReader reader = new InputStreamReader(rawRequest);
        boolean bodyRequired = false;
        int contentLength = 0;
        String header;

        String requestLine = readLine(reader);
        parseRequestLine(requestLine, builder);
        if (requestLine.contains("POST")) bodyRequired = true;

        do {
            header = readLine(reader);
            if (header.trim().isEmpty()) break;
            contentLength = parseHeaders(header, builder);
        } while (true);
        if (bodyRequired) {
            char[] body = new char[1024];
            try {
                reader.read(body, 0, contentLength);
                parseBody(body, builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return builder.build();

    }
}


