package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TeacherServer {
    public TeacherServer() throws IOException {
        //Listens for incoming connections (server, passive open):
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                InputStream inStream = clientSocket.getInputStream();
                InputStreamReader reader = new InputStreamReader(inStream);
                BufferedReader in = new BufferedReader(reader);

                String line = null;
                while ((line = in.readLine()) != null) {
                    // do something with 'line'
                    System.out.println(line);
                }

//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//                String request = "What up Kabster";
//                out.print(request);
//                out.flush();

            } catch (IOException e) {
                System.out.println("Client Disconnected");
//                e.printStackTrace();


            }
            //... input and output goes here ...
        }
    }
}
