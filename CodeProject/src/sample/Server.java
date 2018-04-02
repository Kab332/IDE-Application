package sample;

import java.net.ServerSocket;

/** Server class
 *  This class contains the ServerSocket that the Clients will connect to and has the basic Server functionality.
 *  It contains an array of ClientConnectionHandler classes which manage the connection between their respective Client
 *  and the Server. This class extends Thread so that it can run simultaneously with the JavaFX UI.
 */
public class Server extends Thread{
    ServerSocket serverSocket;
    ClientConnectionHandler [] threads;

    int server_port;
    int number_of_clients = 0;
    int max_clients = 9999;

    boolean isOpen = true;

    // A port is required so this will give an error message if one is not passed
    public Server() {
        System.out.println("Please pass a port as an argument.");
    }

    // The main constructor
    public Server(int port) {
        this.server_port = port;
    }

    // When the Server is started...
    public void run() {
        try {
            serverSocket = new ServerSocket(server_port);
            threads = new ClientConnectionHandler[max_clients];

            System.out.println("Server has started.");

            /**
             *  The Server constantly waits for Client connections and creates a ClientConnectionHandler instance for  each
             *  one. It only stops when the server is closed or when the number of Clients exceed the maximum clients possible.
             */
            while (isOpen && (number_of_clients < max_clients)) {
                threads[number_of_clients] = new ClientConnectionHandler(serverSocket.accept(), number_of_clients+1);
                threads[number_of_clients].start();

                System.out.println("Client #" + (number_of_clients + 1) + " has connected");
                number_of_clients++;
            }
            serverSocket.close();
        } catch (Exception e) {}
    }

    // Sending a single command to all connected clients
    public void sendAll(String command){
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    threads[i].sendMessage(command);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Sending a command and a message to all connected clients
    public void sendAll(String command, String message){
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    threads[i].sendMessage(command);
                    threads[i].sendMessage(message);
                    System.out.println("Sent to Client #" + threads[i].clientNumber);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Sending a command, and two messages to all connected clients
    public void sendAll(String command, String message1, String message2) {
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    threads[i].sendMessage(command);
                    threads[i].sendMessage(message1);
                    threads[i].sendMessage(message2);
                    System.out.println("Sent to Client #" + threads[i].clientNumber);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Closing the server, sending a CLOSE command to all clients to deal with connections and then stopping the while loop
    public void closeServer() {
        System.out.println("Server has closed, disconnecting all clients.");
        sendAll("CLOSE");
        isOpen = false;
    }
}