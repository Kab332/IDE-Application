package sample;

import java.net.ServerSocket;

public class Server extends Thread {
    ServerSocket serverSocket;
    ClientConnectionHandler [] threads;
    int server_port;

    int number_of_clients = 0;
    int max_clients = 999;

    public Server() {
        System.out.println("Please pass a port as an argument.");
        System.exit(0);
    }

    public Server(int port) {
        this.server_port = port;
    }

    public void run() {
        String message = "";

        try {
            serverSocket = new ServerSocket(server_port);
            serverSocket.setSoTimeout(0);

            threads = new ClientConnectionHandler[max_clients];

            while (true) {
                threads[number_of_clients] = new ClientConnectionHandler(serverSocket.accept());
                threads[number_of_clients].start();
                System.out.println("Client connection received.");

                number_of_clients++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
