/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import client.serverConnection.ServerConnectionResources;
import domain.Admin;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ivan
 */
public class Session {

    private static Session instance;
    private Map<String, Object> map;
    private Socket socket;
    private Admin admin;
    private ServerConnectionResources resources;
    
    public Session() {
        this.map = new HashMap<>();
        resources = new ServerConnectionResources();
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ServerConnectionResources getResources() {
        return resources;
    }
    
    

}
