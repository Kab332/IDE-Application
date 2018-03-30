package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientConnectionHandler extends Thread {
    Socket clientSocket;
    DataInputStream in;
    DataOutputStream out;

    int clientNumber;

    boolean isOpen;

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

    public void run() {
        String request = "";

        try {
            while (isOpen) {
                request = readMessage();
                processRequest(request);
            }

            clientSocket.close();

        } catch (Exception e) {}
    }

    public void processRequest(String request) throws Exception {
        System.out.println("Received a request: " + request);

        if (request.equals("CLOSE")) {
            System.out.println("Client #" + clientNumber + " has closed");
            isOpen = false;
        } else if (request.equals("GET ALL TEXT")) {

            System.out.println("Processing :" + request);

            String [] array = FileIOFunctions.getAllTexts();

//            System.out.print("getAllTexts(): ");
//            System.out.println(array == null);
//
//            System.out.println(array[0]);
//            System.out.println(array[1]);

            sendMessage("GET ALL TEXT");
            sendMessage(array[0]);
            sendMessage(array[1]);

        } else {}
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