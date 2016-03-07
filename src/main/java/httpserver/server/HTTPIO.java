package httpserver.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPIO implements ClientServerIO{
    private Socket socket;

    public HTTPIO(Socket socket) {
        this.socket = socket;
    }

    public InputStream inFromClient() throws IOException{
        return socket.getInputStream();
    }

    public OutputStream outToClient() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public void close() throws IOException {
        socket.close();
    }
}

