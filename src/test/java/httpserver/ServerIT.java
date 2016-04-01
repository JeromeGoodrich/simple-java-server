package httpserver;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerIT {

    private String readFromServer(InputStream in) throws IOException {
        int requestChar;
        StringBuffer rawRequest = new StringBuffer();

        while ((requestChar = in.read()) != -1){
            rawRequest.append((char) requestChar);
        }
        return rawRequest.toString();
    }

    @Test
    public void serverIntegrationTest() throws Exception {
        ServerITLauncher launcher = new ServerITLauncher();
        String[] args = {"-p","5001","-d","./src/test/fixtures"};
        launcher.launch(args);
        Socket testerSocket = new Socket(InetAddress.getLocalHost(),5001);
        PrintWriter out = new PrintWriter(testerSocket.getOutputStream(), true);
        out.write("GET /file1 HTTP/1.1\r\nHost: localhost:5001\r\nAccept: */*\r\n\r\n");
        String response = readFromServer(testerSocket.getInputStream());

        assertThat(response, is("HTTP/1.1 200 OK\r\nContent-Length: 14\r\nContent-Type: text/plain\r\n\r\nfile1 contents"));
        launcher.killServer();

        assertThat(response, is("Connection Refused"));
    }

}
