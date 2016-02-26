package HTTPServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Responder {

    private String status;
    private ArrayList responseBody;

    public void setStatusLine(String status) {
        this.status = status;
    }

    public void setResponseBody(ArrayList responseBody) {
        this.responseBody = responseBody;
    }

    public String getStatusLine() {
        return status;
    }

    public ArrayList getResponseBody() {
        return responseBody;
    }

    public void createStatusLine(String path, String version) {
        if (path == "/"){
            setStatusLine("HTTP/"+ version + " 200 OK" + "\r\n");
        } else {
            String fileName = path.replaceFirst("/","");
            if (new File(fileName).isFile() || new File(fileName).isDirectory()) {
             setStatusLine("HTTP/" + version + " 200 OK" + "\r\n");
            } else {
                setStatusLine("HTTP/" + version +" 404 Not Found" + "\r\n");
            }
        }
    }

    public void createResponseBody(String path) {
        if (path == "/") {
            ArrayList body = new ArrayList<String>();
            body.add("Hello World");
            setResponseBody(body);
        } else {
            String fileName = path.replaceFirst("/","");
            if (new File(fileName).isDirectory()) {
                File dir = new File(fileName);
                String[] dirContents = dir.list();
                ArrayList files = new ArrayList<String>();
                for (String s: dirContents)
                    files.add(s);
                setResponseBody(files);
            } else {
                if (new File(fileName).isFile())
                    try {
                        FileReader fileReader = new FileReader(fileName);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line = null;
                        ArrayList lines = new ArrayList<String>();
                        while ((line = bufferedReader.readLine()) != null) {
                            lines.add(line);
                        }
                        setResponseBody(lines);
                    } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

