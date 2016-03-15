package httpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RealServerListener implements ServerListener{

    private ServerSocket serverSocket;

    public RealServerListener(int portNumber) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ClientSocketInterface accept() throws IOException {
        Socket socket = serverSocket.accept();
        return new ClientSocket(socket);
    }

    public boolean isOpen() {
        return true;
    }
}
