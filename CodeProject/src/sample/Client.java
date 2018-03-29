package sample;

import javafx.scene.control.Tab;
import jdk.nashorn.api.tree.ForInLoopTree;

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

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e.getStackTrace());

        }

        System.out.println("CLOSING");
//        System.exit(0);
    }


    public void serverBridge(String tabNum, String serialString){
        System.out.println("Bridged");
        getDoc(FileIOFunctions.tabs.getTabs().get(Integer.valueOf(tabNum))).change(serialString);
    }

    private Document getDoc(Tab tab){
        for(Document i : FileIOFunctions.docList){
            if(i.tab.equals(tab)){
                return i;
            }
        }
        return null;
    }

    public void processCommand(String command) throws Exception {
        if (command.equals("CHANGE")) {
            String tabNum = readMessage();
            currentMessage = readMessage();

            System.out.println("TabNum: "+tabNum);
            System.out.println("currentMessage: "+currentMessage);

//            Main.controller.serverBridge(tabNum,currentMessage);

            // Thomas code
            serverBridge(tabNum,currentMessage);

        } else if (command.equals("CLOSE")) {
            closeClient();
        } else if (command.equals("TABS")) {

        } else {
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
