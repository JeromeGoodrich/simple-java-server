package httpserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocket implements ClientSocketInterface {

    private Socket socket;

    public ClientSocket(Socket socket){
            this.socket = socket;
    }

    public InputStream getInputStream() {
        return InputStream in = socket.getInputStream();

    }

    public OutputStream getOutputStream(){
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void close() throws IOException {
        socket.close();
    }
}
