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
            if(isOpen) {
                System.out.println("Client is connected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Client Run Thread Number: "+Thread.currentThread().getId());
        String command = "";
        try {

            while (isOpen) {
                command = readMessage();
                processCommand(command);
            }

            socket.close();

        } catch (Exception e) {}

        //  System.exit(0);
    }

//    public static void main (String [] args) {
//        (new Thread(new Client())).start();
//    }

    public void processCommand(String command) throws Exception {
        System.out.println("Processing " + command + ".");
        if (command.equals("CHANGE")) {
            currentMessage = readMessage();
        } else if (command.equals("CLOSE")) {
            closeClient();
        } else if (command.equals("TABS")) {
            // Potential future code
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

    public void closeClient() throws IOException {
        isOpen = false;
        sendMessage("CLOSE");
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