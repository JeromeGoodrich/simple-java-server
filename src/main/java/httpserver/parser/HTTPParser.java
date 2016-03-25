package httpserver.parser;

import httpserver.request.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
            if (splitRequestLine[1].contains("parameters")) {
                parseParams(splitRequestLine[1], builder);

            }
        }
        builder.version(splitRequestLine[2].substring(0, 8));
    }

    private void parseParams(String path, Request.RequestBuilder builder) {
        String[] splitPath = path.split("\\?");
        String[] rawVariables = splitPath[1].split("\\&");
        for (String variable : rawVariables) {
            String[] nameAndRawValue = variable.split("=");
            String name = nameAndRawValue[0];
            String rawValue = nameAndRawValue[1];
            String value = decodeRawValue(rawValue);
            builder.params(name, value);
        }
    }

    private String decodeRawValue(String rawValue){
        String decodedValue = rawValue;
        HashMap<String, String> decoder = decodeUrl();
        for (Map.Entry<String, String> entry : decoder.entrySet()) {
            String urlCode = entry.getKey();
            String decodedValues = entry.getValue();
            decodedValue = decodedValue.replaceAll(urlCode, decodedValues);
        }
        return  decodedValue.replaceAll("24", "\\$");
    }

    private HashMap decodeUrl(){
        HashMap<String, String> urlCodes = new HashMap();
        urlCodes.put("20", " ");
        urlCodes.put("22", "\"");
        urlCodes.put("23", "#");
        urlCodes.put("26", "&");
        urlCodes.put("40", "@");
        urlCodes.put("43", "+");
        urlCodes.put("2B", "+");
        urlCodes.put("2C", ",");
        urlCodes.put("2F", "/");
        urlCodes.put("3A", ":");
        urlCodes.put("3B", ";");
        urlCodes.put("3C", "<");
        urlCodes.put("3D", "=");
        urlCodes.put("3E", ">");
        urlCodes.put("3F", "?");
        urlCodes.put("5B", "[");
        urlCodes.put("5D", "]");
        urlCodes.put("%", "");
        return urlCodes;
    }


    private void parseHeaders(String header, Request.RequestBuilder builder) {
        String[] splitHeader = header.split(":");
        builder.headers(splitHeader[0], splitHeader[1].trim());
    }

    private int setContentLength(String header) {
       String length = header.split(":")[1].trim();
        return Integer.parseInt(length);
    }


    private void parseBody(char[] body, Request.RequestBuilder builder) {
        String stringBody = new String(body);
        String[] splitStringBody = stringBody.split("&");
        for (int i = 0; i < splitStringBody.length ; i++) {
            String[] keyValue = splitStringBody[i].split("=");
            if (keyValue.length < 2) {
                builder.body("","");
            } else {
                builder.body(keyValue[0], keyValue[1]);
            }
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
            parseHeaders(header, builder);
            if (header.contains("Content-Length")) {
                contentLength = setContentLength(header);
                System.out.println(contentLength);
            }
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


