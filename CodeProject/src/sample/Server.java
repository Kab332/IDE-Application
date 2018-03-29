package sample;

import java.net.ServerSocket;

public class Server extends Thread {
    ServerSocket serverSocket;
    ClientConnectionHandler [] threads;

    int server_port;
    int number_of_clients = 0;
    int max_clients = 999;

    boolean endOfSession = false;

    public Server() {
        System.out.println("Please pass a port as an argument.");
        System.exit(0);
    }

    public Server(int port) {
        this.server_port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(server_port);

            threads = new ClientConnectionHandler[max_clients];

            while (!endOfSession && (number_of_clients < max_clients)) {
                threads[number_of_clients] = new ClientConnectionHandler(serverSocket.accept(), number_of_clients+1);
                threads[number_of_clients].start();

                System.out.println("Client #" + (number_of_clients + 1) + " has connected");
                number_of_clients++;
            }
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String command, String message){
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    System.out.println("Sent to Client #" + threads[i].clientNumber);
                    threads[i].sendMessage(command);
                    threads[i].sendMessage(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeServer() {
        sendAll("CLOSE", "");
        endOfSession = true;
    }
}