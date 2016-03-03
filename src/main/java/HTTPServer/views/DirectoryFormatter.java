package httpserver.views;

import httpserver.request.Request;


public class DirectoryFormatter {
    public String format(String[] dirListing, Request request) {
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
