package httpserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocket implements ClientSocketInterface {

    private Socket socket;

    public ClientSocket(ServerSocket serverSocket){
        try {
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        InputStream in = null;
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
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

    public void close() {}
}
