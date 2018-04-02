package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * This class' purpose is to handle each Server to Client connection. This is the class that sends and receives messages
 * to the client.
 */
public class ClientConnectionHandler extends Thread {
    Socket clientSocket;

    // Using Data Input/Output Stream to send and receive messages
    DataInputStream in;
    DataOutputStream out;

    int clientNumber;

    boolean isOpen;

    // The constructor, initializing variables
    public ClientConnectionHandler(Socket socket, int number) {
        clientSocket = socket;
        clientNumber = number;
        isOpen = true;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // When ClientConnectionHandler is started...
    public void run() {
        String request = "";

        try {
            /**
             * The ClientConnectionHandler constantly waits for requests from the client. Requests are the initial message
             * that the client sends. This initial message determines how the server receives the messages that come after
             * it (if there are any).
             */
            while (isOpen) {
                request = readMessage();
                processRequest(request);
            }
            clientSocket.close();
            System.out.println("Client #" + clientNumber + " has closed");
        } catch (Exception e) {}
    }

    // These are the different actions that the ClientConnectionHandler can take based on the request message from the Client
    public void processRequest(String request) {
        // Request for closing connection
        if (request.equals("CLOSE")) {
            isOpen = false;

        // Request for getting all of the text on the Server's text areas
        } else if (request.equals("GET ALL TEXT")) {
            String [] array = FileIOFunctions.getAllTexts();

            sendMessage("GET ALL TEXT");
            sendMessage(array[0]);
            sendMessage(array[1]);
        } else {}
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}