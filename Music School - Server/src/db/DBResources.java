/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

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
public class DBResources {

    private Properties dbProperties;
    private FileInputStream fileInputStream;

    public DBResources() {
        try {
            this.dbProperties = new Properties();
            fileInputStream = new FileInputStream("./resources/database.config.txt");
            dbProperties.load(fileInputStream);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return dbProperties.getProperty("url");
    }

    public String getUsername() {
        return dbProperties.getProperty("username");
    }

    public String getPassword() {
        return dbProperties.getProperty("password");
    }

    public void writeToFile(String url, String username, String password) throws FileNotFoundException, IOException {
        PrintWriter pw = new PrintWriter("./resources/database.config.txt");
        pw.close();
        dbProperties.clear();

        String content = "url = " + url + "\n" + "username = " + username + "\n" + "password = " + password + "\n";
        FileOutputStream out = new FileOutputStream("./resources/database.config.txt");
        byte[] contentInBytes = content.getBytes();

        out.write(contentInBytes);
        out.flush();
        out.close();

        dbProperties.load(fileInputStream);
    }

}
