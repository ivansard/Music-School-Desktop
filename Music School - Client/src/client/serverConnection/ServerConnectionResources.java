/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.serverConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class ServerConnectionResources {

    Properties properties;

    public ServerConnectionResources() {
        FileInputStream fis = null;
        try {
            this.properties = new Properties();
            fis = new FileInputStream("./resources/server.config.txt");
            properties.load(fis);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionResources.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String getIpAddress() {
        return properties.getProperty("ip");
    }

    public String getPort() {
        return properties.getProperty("port");
    }
    
    public static void updateConnectionDetailsInFile(String ipAddress, int port){
        try {
            PrintWriter writer = new PrintWriter("./resources/server.config.txt");
            writer.close();
            // Ovde si stao - moras da upises info u fajl nakon sto si obrisao!
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
