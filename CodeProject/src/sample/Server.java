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
                    sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        sendMessageAsByte(message.getBytes());
    }

    public void sendMessageAsByte(byte [] message) {
        try {
            out.writeInt(message.length);
            out.write(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
        return new String(readMessageAsByte());
    }

    public byte [] readMessageAsByte() {
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

        return data;
    }
}
