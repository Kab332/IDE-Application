package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.Socket;

/**
 * This is the Client class. It connects to a server and then is capable of sending messages to the server or receiving
 * commands from it.
 */
public class Client extends Thread{
    Socket socket;

    // Using Data Input/Output Stream to send and receive messages
    DataOutputStream out;
    DataInputStream in;

    boolean isOpen = false;

    String currentMessage;

    // The Client requires an IP address and a port, an error message will be given if they are not provided
    public Client() {
        System.out.println("Please pass the IP address and Port as arguments");
    }

    // The main constructor
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

    // When the Client is started...
    public void run() {
        String command = "";
        try {
            /**
             * The Client constant waits to receive commands from the Server (through ClientConnectionHandler). Commands
             * are the initial messages that the Server sends. This initial message determines how the Client receives the
             * messages that come after it (if there are any).
             */
            while (isOpen) {
                command = readMessage();
                processCommand(command);
            }
            socket.close();
            System.out.println("Client has disconnected.");
        } catch (Exception e) {}
    }

    // These are the different actions that the Client can take based on the command given by the Server (ClientConnectionHandler)
    public void processCommand(String command) {
        // A potential future command that updates the Client side teacher text more efficiently.
        if (command.equals("CHANGE")) {
            String tabNumber = readMessage();
            currentMessage = readMessage();

            System.out.println(tabNumber);
            System.out.println(currentMessage);

        // A message from the Server to close the connection on the Client side
        } else if (command.equals("CLOSE")) {
            closeClient();

        // This command makes the Client update its Teacher text area with the information in the two messages coming after it
        } else if (command.equals("GET ALL TEXT")) {
            String tabNames = readMessage();
            String tabContents = readMessage();

            FileIOFunctions.tabNames = tabNames;
            FileIOFunctions.tabContents = tabContents;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Runs in main thread
                    FileIOFunctions.addTabsToTeacherPane();
                }
            });
        }else {
            currentMessage = readMessage();
        }
    }

    // Sending a message to the Client, takes a string and sends it as a byte
    public void sendMessage(String message) {
        sendMessageAsByte(message.getBytes());
    }

    // Sends a byte message to the Client
    public void sendMessageAsByte(byte [] message) {
        try {
            out.writeInt(message.length);
            out.write(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Converts the message read as bytes to String
    public String readMessage() {
        return new String(readMessageAsByte());
    }

    // Reads an incoming message as bytes
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

    // Closing the Client, sending a CLOSE request to the server and then stopping the while loop
    public void closeClient() {
        sendMessage("CLOSE");
        isOpen = false;
    }
}