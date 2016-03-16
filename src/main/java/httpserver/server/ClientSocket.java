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

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();

    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }
}
