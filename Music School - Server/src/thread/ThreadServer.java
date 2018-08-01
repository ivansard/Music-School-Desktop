/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.User;

/**
 *
 * @author Ivan
 */
public class ThreadServer extends Thread {

    ServerSocket serverSocket;
    List<ThreadClient> clients;
    boolean stopped = false;

    public ThreadServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (stopped) {
                    interrupt();
                }
                System.out.println("Server up and running and waiting for new clients!");
                Socket clientCommunicationSocket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ThreadClient client = new ThreadClient(clientCommunicationSocket);
                client.start();
                clients.add(client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server stopped!");
    }

    public void notifyClients() {
        for (Thread user : clients) {
            ThreadClient clientThread = (ThreadClient) user;
            clientThread.notifyShutdown();
        }
    }

    public void removeUsers() throws InterruptedException {
        for (ThreadClient user : clients) {
            user.interrupt();
//            client.join();
        }
        clients.clear();
        stopped = true;
    }

    public void removeUser(User userToRemove) {
        for (ThreadClient user : clients) {
            if (userToRemove.getUserThread().equals(user)) {
                user.interrupt();
            }
        }
        clients.remove(userToRemove.getUserThread());
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void removeClients() {
        for (ThreadClient client : clients) {
            client.interrupt();
//            client.join();
        }
        clients.clear();
        stopped = true;
    }

    public void removeClient(User userToRemove) {
        for (ThreadClient client : clients) {
            if (userToRemove.getUserThread().equals(client)) {
                client.interrupt();
            }
        }
        clients.remove(userToRemove.getUserThread());
    }

}
