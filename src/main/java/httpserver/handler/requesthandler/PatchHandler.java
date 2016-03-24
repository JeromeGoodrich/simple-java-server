//package httpserver.handler.requesthandler;
//
//import httpserver.request.Request;
//import httpserver.response.Response;
//import httpserver.response.ResponseBuilder;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//
//import static java.nio.file.StandardOpenOption.*;
//
//public class PatchHandler implements Handler {
//
//    private final String rootDir;
//    private String etag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
//
//
//    public PatchHandler(String rootDir) {
//        this.rootDir = rootDir;
//    }
//
//    public Response handle(Request request) {
//        String requestEtag = request.getHeader("If-Match");
//        ResponseBuilder builder = null;
//        String fileName = rootDir + request.getPath();
//        try {
//            byte[] fileContent = Files.readAllBytes(Paths.get(rootDir +request.getPath()));
//
//            if (etag.trim().equals(requestEtag.trim())) {
//                builder = new ResponseBuilder(204);
//                writeToFile(fileName, request.getBody());
//                etag = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";
//            } else{
//                builder = new ResponseBuilder(200);
//                writeToFile(fileName, request.getBody());
//                builder.body(fileContent);
//                etag = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return builder.build();
//    }
//
//    private void writeToFile(String fileName, String content) {
//        File file = new File(fileName);
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(content.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean willHandle(String method, String path) {
//        if (method.equals("PATCH") && path.equals("patch-content.txt")) return true;
//        return false;
//    }
//}
