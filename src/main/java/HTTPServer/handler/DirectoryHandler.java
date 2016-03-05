package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import httpserver.views.DirectoryFormatter;

import java.io.File;

public class DirectoryHandler implements Handler {
    public Response handle(Request request) {
        File dir = new File(request.getPath());
        String[] dirListing = dir.list();
        DirectoryFormatter formatter = new DirectoryFormatter();
        String formattedDirListing = formatter.format(dirListing, request);
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
