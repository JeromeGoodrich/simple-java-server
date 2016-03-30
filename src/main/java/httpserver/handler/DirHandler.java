package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

import java.io.File;

public class DirHandler implements Handler {

    private final String rootDir;

    public DirHandler(String rootDir) {
        this.rootDir = rootDir;
    }

    public Response handle(Request request) {
        byte[] data;
        File dir = new File(rootDir);
        String[] dirListing = dir.list();
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        if (request.getHeader("Accept") != null && request.getHeader("Accept").equals("application/json")) {
            data = createJSONContent(dirListing);
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Content-Length", Integer.toString(data.length));
        } else {
            data = createHTMLContent(dirListing);
        }
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("GET") && (new File(path).isDirectory())) return true;
        return false;
    }

    private byte[] createJSONContent(String[] dirListing) {
        String open = "{ contents: [";
        String jsonDirListing = createJsondirListing(dirListing);
        String close = "] }";
        String jsonContent = open + jsonDirListing + close;
        return jsonContent.getBytes();
    }

    private String createJsondirListing(String[] dirListing) {
        String jsonString = String.join(", ", dirListing);
        return jsonString;
    }

    private byte[] createHTMLContent(String[] dirListing) {
        String doctypeTag = "<!DOCTYPE html>\n";
        String htmlTag = "<html>\n";
        String dirLinks = createDirLinks(dirListing);
        String htmlBody = "<head></head><body>\n<ol>\n" + dirLinks + "</ol>\n<body>\n</html>";
        String htmlPage = doctypeTag + htmlTag + htmlBody;
        return htmlPage.getBytes();
    }

    private String createDirLinks(String[] dirListing) {
        String HTMLContent = "";
        for (int i = 0; i < dirListing.length; i++) {
            HTMLContent += "<li><a href=\""+ "/" + dirListing[i] +"\">" + dirListing[i] + "</a></li>\n";
        }
        return HTMLContent;
    }
}
