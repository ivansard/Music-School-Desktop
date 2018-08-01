/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Admin;
import java.time.LocalTime;
import thread.ThreadClient;

/**
 *
 * @author Ivan
 */
public class User {

    private Admin admin;
    private ThreadClient userThread;
    private LocalTime loggedAt;

    public User() {
    }

    public User(Admin admin, ThreadClient userThread, LocalTime loggedAt) {
        this.admin = admin;
        this.userThread = userThread;
        this.loggedAt = loggedAt;
    }

    public LocalTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ThreadClient getUserThread() {
        return userThread;
    }

    public void setUserThread(ThreadClient userThread) {
        this.userThread = userThread;
    }

}
