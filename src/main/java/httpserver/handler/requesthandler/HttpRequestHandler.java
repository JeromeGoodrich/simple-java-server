package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import org.json.simple.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpRequestHandler implements RequestHandler {

    public Response handle(Request request) {
        if (request.getMethod().equals("POST")) {
            if (request.getPath().equals("form")) {
                return handlePost(request);
            } else {
                ResponseBuilder builder = new ResponseBuilder(404);
                return builder.reasonPhrase().version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
            }
        } else if (request.getMethod().equals("GET")) {
            if (request.getPath().equals("/")) {
                String greeting = "Hello World";
                ResponseBuilder builder = new ResponseBuilder(200);
                return builder.reasonPhrase().version(request.getVersion()).body(greeting.getBytes()).build();
            } else if (request.getPath().equals("form")) {
                return handleForm(request);
            } else if (new File(request.getPath()).isDirectory()) {
                return handleDir(request);
            } else if (new File(request.getPath()).isFile()) {
                return handleFile(request);
            } else {
                ResponseBuilder builder = new ResponseBuilder(404);
                return builder.reasonPhrase().version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
            }
        } else {
            ResponseBuilder builder = new ResponseBuilder(404);
            return builder.reasonPhrase().version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
        }
    }

    private Response handlePost(Request request) {
        String val1 = request.getBodyVal("firstname");
        String val2 = request.getBodyVal("lastname");
        String htmlContent = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n"+ val1 + " " + val2 + "\n</body>\n</html>";
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(htmlContent.getBytes()).reasonPhrase().version(request.getVersion()).build();
    }

    private Response handleForm(Request request) {
        String HTMLBoilerPlate = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n";
        String openFormTag = "<form method=\"post\">";
        String firstField = "First Name:\n<input type=\"text\" name=\"firstname\">\n";
        String secondField = "Last Name:\n<input type=\"text\" name=\"lastname\">\n";
        String submit = "<input type=\"submit\" value=\"Submit\">\n</form>\n</body>\n</html>";
        String htmlContent = HTMLBoilerPlate + openFormTag + firstField + secondField + submit;
        byte[] data = htmlContent.getBytes();
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    private Response handleFile(Request request) {
        byte[] bytes = null;
        String mimeType = URLConnection.guessContentTypeFromName(request.getPath());
        ResponseBuilder builder = new ResponseBuilder(200);
        builder.addHeader("Content-Type", mimeType);
            try {
                bytes = Files.readAllBytes(Paths.get(request.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        builder.addHeader("Content-Length", Integer.toString(bytes.length));
        return builder.body(bytes).reasonPhrase().version(request.getVersion()).build();
    }

    private Response handleDir(Request request) {
        byte[] data;
        File dir = new File(request.getPath());
        String[] dirListing = dir.list();
        ResponseBuilder builder = new ResponseBuilder(200);
        if (request.getHeader("Accept").equals("application/json")) {
            data = createJSONContent(dirListing, request);
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Content-Length", Integer.toString(data.length));
        } else {
            String formattedDirListing = format(dirListing, request);
            data = formattedDirListing.getBytes();
        }
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

    private byte[] createJSONContent(String[] dirListing, Request request) {
        JSONObject directories = new JSONObject();
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
    private int jsonDirCounter = 1;
    private int jsonFileCounter = 1;
}
