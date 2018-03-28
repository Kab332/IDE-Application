package sample;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

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
        try {
            serverSocket = new ServerSocket(server_port);
            serverSocket.setSoTimeout(0);

            threads = new ClientConnectionHandler[max_clients];

            while (true) {
                threads[number_of_clients] = new ClientConnectionHandler(serverSocket.accept(), number_of_clients+1);
                threads[number_of_clients].start();

                System.out.println("Client #" + (number_of_clients + 1) + " has connected");
                number_of_clients++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String message){
        try {
            for (int i = 0; i < number_of_clients; i++) {
                if (threads[i].isOpen) {
                    System.out.println("Sent to Client #" + threads[i].clientNumber);
                    threads[i].sendMessage(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
