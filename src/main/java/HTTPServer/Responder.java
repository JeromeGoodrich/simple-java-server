package HTTPServer;

import java.io.File;

public class Responder {

    private String status;

    public void setStatusLine(String status) {
        this.status = status;
    }

    public String getStatusLine() {
        return status;
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
}
