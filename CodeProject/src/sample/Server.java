package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    ServerSocket serverSocket;
    Socket clientSocket;
    int server_port;

    DataOutputStream out;
    DataInputStream in;

    public Server() {
        System.out.println("Please pass a port as an argument.");
        System.exit(0);
    }

    public Server(int port) {
        this.server_port = port;
    }

    public void run() {
        String message = "";

        try {
            serverSocket = new ServerSocket(server_port);
            serverSocket.setSoTimeout(0);

            while (true) {
                clientSocket = serverSocket.accept();
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());
                System.out.println("Client connection received.");

                while ((message = readMessage()) != null) {
                    System.out.println("Received (server): " + message);
                    System.out.println("");
                    sendResponse(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(String message) {
        byte [] data = message.getBytes();

        try {
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
        byte [] data = new byte[256];
        int length;

        try {
            length = in.readInt();
            if (length > 0) {
                data = new byte[length];
                in.readFully(data, 0, data.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(data);
    }
}
