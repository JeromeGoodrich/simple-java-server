package httpserver.server;

import httpserver.handler.RequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.views.ResponseFormatter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Port Number Required");
            System.exit(1);
        }

        Integer portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Boolean listening = true;

        while (listening) {
            System.out.println("Waiting...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted Connection: " + clientSocket);

            WebService webService = new WebService(clientSocket);
            Thread thread = new Thread(webService);
            thread.start();
        }
    }
}
