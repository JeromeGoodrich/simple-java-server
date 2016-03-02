package HTTPServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void main() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Boolean listening = true;

            while (listening) {
                Socket clientSocket = serverSocket.accept();

                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader inFromClient = new BufferedReader(inputReader);

                String rawRequest = inFromClient.readLine();
                Request request = new Request();
                request.parse(rawRequest);
                RequestHandler handler = new RequestHandler();
                Response response = handler.handle(request);
                ResponseFormatter formatter = new ResponseFormatter();
                byte[] formattedResponse = formatter.format(response);

                outToClient.write(formattedResponse);
                outToClient.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();}
    }
}
