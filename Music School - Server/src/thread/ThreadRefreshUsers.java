/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import util.UserTableModel;
import util.Session;
import util.User;

/**
 *
 * @author Ivan
 */
public class ThreadRefreshUsers extends Thread {

    UserTableModel userTableModel;

    public ThreadRefreshUsers(UserTableModel userTableModel) {
        this.userTableModel = userTableModel;
    }

    
    
    @Override
    public void run() {
        while (!isInterrupted()) {
            for (User clientSession : Session.getInstance().getUsers()) {
                boolean exists = false;
                for (User user : userTableModel.getUsers()) {
                    if (user.equals(clientSession)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    continue;
                } else {
                    userTableModel.addClient(clientSession);
                }
            }
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
