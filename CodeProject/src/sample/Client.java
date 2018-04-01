package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    boolean isOpen = false;

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
            System.out.println("Client is connected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String command = "";
        try {
            while (isOpen) {
                command = readMessage();
                processCommand(command);
            }
            socket.close();
            System.out.println("Client has disconnected.");
        } catch (Exception e) {}
    }

    public void processCommand(String command) {
        if (command.equals("CHANGE")) {
            String tabNumber = readMessage();
            currentMessage = readMessage();

            System.out.println(tabNumber);
            System.out.println(currentMessage);

        } else if (command.equals("CLOSE")) {
            closeClient();

        } else if (command.equals("GET ALL TEXT")) {
            String tabNames = readMessage();
            String tabContents = readMessage();

            FileIOFunctions.tabNames = tabNames;
            FileIOFunctions.tabContents = tabContents;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //runs in main thread
                    FileIOFunctions.addTabsToTeacherPane();
                }
            });
        }else {
            currentMessage = readMessage();
        }
    }

    public void closeClient() {
        sendMessage("CLOSE");
        isOpen = false;
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