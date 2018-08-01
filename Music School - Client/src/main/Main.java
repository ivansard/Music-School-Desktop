/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import forms.FLogin;
import domain.Professor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Controller;
import forms.FMain;

/**
 *
 * @author Ivan
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FLogin login = new FLogin();
        login.setVisible(true);
    }

}
