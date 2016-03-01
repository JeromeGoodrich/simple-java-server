package HTTPServer;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by admin on 2/29/16.

public class FileHandler implements Handler {

    public Response handle(Request request) {
        /*String path = request.getPath();
        String fileName = path.replaceFirst("/","");
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
        return new
    }
}
*/