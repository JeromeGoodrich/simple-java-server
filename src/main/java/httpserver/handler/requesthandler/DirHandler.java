package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import org.json.simple.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class DirHandler implements Handler {

    private final String rootDir;

    public DirHandler(String rootDir) {
        this.rootDir = rootDir;
    }

    public Response handle(Request request) {
        byte[] data;
        File dir;
        if (request.getPath().equals("/")) {
            dir = new File(rootDir);
        } else {
            dir = new File(rootDir + request.getPath());
        }
        String[] dirListing = dir.list();
        ResponseBuilder builder = new ResponseBuilder(200);
        //if (request.getHeader("Accept").equals("application/json")) {
          //  data = createJSONContent(dirListing, request);
           // builder.addHeader("Content-Type", "application/json");
           // builder.addHeader("Content-Length", Integer.toString(data.length));
       // } else {
            String formattedDirListing = format(dirListing, request);
            data = formattedDirListing.getBytes();
       // }
        System.out.println(new String(data));
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("GET") && (new File(path).isDirectory())) return true;
        return false;
    }

    private byte[] createJSONContent(String[] dirListing, Request request) {

        JSONObject directories = new JSONObject();
        int jsonDirCounter = 1;
        int jsonFileCounter = 1;
        JSONObject files = new JSONObject();
        for (int i = 0; i < dirListing.length; i++) {
            if (new File(request.getPath() + "/" + dirListing[i]).isDirectory()) {
                directories.put(Integer.toString(jsonDirCounter), dirListing[i]);
                jsonDirCounter ++;
            } else if (new File(dirListing[i]).isFile()) {
                files.put(Integer.toString(jsonFileCounter), dirListing[i]);
                jsonFileCounter ++;
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSONObject content = new JSONObject();
        content.put("directories", directories);
        content.put("files", files);
        return content.toJSONString().getBytes();
    }

    private String format(String[] dirListing, Request request) {
        String doctypeTag = "<!DOCTYPE html>\n";
        String htmlTag = "<html>\n";
        String htmlContent = createHTMLContent(dirListing, request);
        String htmlBody = "<head></head><body>\n<ol>\n" + htmlContent + "</ol>\n<body>\n</html>";
        String htmlPage = doctypeTag + htmlTag + htmlBody;
        return htmlPage;
    }

    private String createHTMLContent(String[] dirListing, Request request) {
        String HTMLContent = "";
        for (int i = 0; i < dirListing.length; i++) {
            HTMLContent += "<li><a href=\""+ "/" + dirListing[i] +"\">" + dirListing[i] + "</a></li>\n";
        }
        return HTMLContent;
    }
}
