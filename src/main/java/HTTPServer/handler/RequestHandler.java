package httpserver.handler;

import httpserver.request.HTTPRequest;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler implements Handler {

    public Response handle(Request request) {
        if (request.getPath().equals("/")) {
            String greeting = "Hello World";
            ResponseBuilder builder = new ResponseBuilder(200);
            return builder.reasonPhrase().version(request.getVersion()).body(greeting.getBytes()).build();
        } else if (new File(request.getPath()).isDirectory()) {
            return handleDir(request);
        } else if (new File(request.getPath()).isFile()) {
            return handleFile(request);
        } else {
            System.out.println(request.getPath());
            ResponseBuilder builder = new ResponseBuilder(404);
            return builder.reasonPhrase().version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
        }
    }

    private Response handleFile(Request request) {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(request.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseBuilder builder = new ResponseBuilder(200);
        System.out.println(Paths.get(request.getPath()));
        return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
    }

    private Response handleDir(Request request) {
        File dir = new File(request.getPath());
        String[] dirListing = dir.list();
        String formattedDirListing = format(dirListing, request);
        byte[] data = formattedDirListing.getBytes();
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    private String format(String[] dirListing, Request request) {
        String doctypeTag = "<!DOCTYPE html>\n";
        String htmlTag = "<html>\n";
        String htmlContent = createHTMLContent(dirListing, request);
        String htmlBody = "<body>\n<ol>\n" + htmlContent + "</ol>\n<body>";
        String htmlPage = doctypeTag + htmlTag + htmlBody;
        return htmlPage;
    }

    private String createHTMLContent(String[] dirListing, Request request) {
        String HTMLContent = "";
        for (int i = 0; i < dirListing.length; i++) {
            HTMLContent += "<li><a href=\""+ "/" + request.getPath() + "/" + dirListing[i] +"\">" + dirListing[i] +"</a></li>\n";
        }
        return HTMLContent;
    }
}
