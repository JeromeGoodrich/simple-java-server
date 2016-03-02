package HTTPServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Port Number Required");
            System.exit(1);
        }

        Integer portNumber = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Boolean listening = true;

            while (listening) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to Server");

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

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();}
    }
}
