package httpserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MockService extends Service implements Runnable {

    public ClientSocketInterface socket;
    public Boolean running;
    public String output;

    public void setSocket(ClientSocketInterface socket) {
        this.socket = socket;
    }
    public void run() {
        running = true;
        try {
            readSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readSocket() throws IOException{
        InputStreamReader i = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(i);
        output = reader.readLine();
        return output;

    }
}
