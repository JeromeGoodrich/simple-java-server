package HTTPServer;

import java.io.File;

/**
 * Created by admin on 3/2/16.
 */
public class DirectoryFormatter {
    public String format(String[] dirListing) {
        String doctypeTag = "<!DOCTYPE html>\n";
        String htmlTag = "<html>\n";
        String htmlContent = createHTMLContent(dirListing);
        String htmlBody = "<body>\n<ol>\n" + htmlContent + "</ol>\n<body>";
        String htmlPage = doctypeTag + htmlTag + htmlBody;
        return htmlPage;
    }

    private String createHTMLContent(String[] dirListing) {
        String HTMLContent = "";
        for (int i = 0; i < dirListing.length; i++) {
            HTMLContent += "<li><a href=\""+ dirListing[i] +"\">" + dirListing[i] +"</a></li>\n";
        }
        return HTMLContent;
    }
}
