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

    public void run() {
        try {
            serverSocket = new ServerSocket(server_port);
            threads = new ClientConnectionHandler[max_clients];

            System.out.println("Server has started.");
            while (isOpen && (number_of_clients < max_clients)) {
                threads[number_of_clients] = new ClientConnectionHandler(serverSocket.accept(), number_of_clients+1);
                threads[number_of_clients].start();

                System.out.println("Client #" + (number_of_clients + 1) + " has connected");
                number_of_clients++;
            }
            serverSocket.close();
        } catch (Exception e) {}
    }

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

    public void sendAll(String command, String tabNumber, String message) {
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    threads[i].sendMessage(command);
                    threads[i].sendMessage(tabNumber);
                    threads[i].sendMessage(message);
                    System.out.println("Sent to Client #" + threads[i].clientNumber);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeServer() {
        sendAll("CLOSE");
        System.out.println("Server is closed.");
        isOpen = false;
    }
}