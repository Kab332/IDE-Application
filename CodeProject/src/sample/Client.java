package sample;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    boolean isOpen;

    String currentMessage;

    public Client() {
        System.out.println("Please pass the IP address and Port as arguments");
        System.exit(0);
    }

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            isOpen = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (isOpen) {
            if (isOpen) {
                currentMessage = readMessage();
            }
            System.out.println(currentMessage);
        }

        try {
            socket.close();
        } catch (Exception e) {}
    }

    public void closeClient() throws IOException {
        isOpen = false;
        sendMessage("CLOSED");
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
        } catch (Exception e) {}

        return data;
    }

}
