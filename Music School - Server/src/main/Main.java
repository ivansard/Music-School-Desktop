/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import serverForm.FDBConnect;
import serverForm.FServer;

/**
 *
 * @author Ivan
 */
public class Main {
    public static void main(String[] args) {
        FDBConnect fConnect = new FDBConnect(null, true);
        fConnect.setVisible(true);
    }
}
