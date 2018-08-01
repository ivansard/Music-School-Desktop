/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Admin;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.Session;
import util.User;

/**
 *
 * @author Ivan
 */
public class UserTableModel extends AbstractTableModel {

    private List<User> users;
    private String[] columns = {"Username", "First Name", "Last Name", "Logged at"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        if (users.isEmpty()) {
            return 0;
        }
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowInd, int colInd) {
        User user = users.get(rowInd);
        switch (colInd) {
            case 0:
                return user.getAdmin().getUsername();
            case 1:
                return user.getAdmin().getFirstName();
            case 2:
                return user.getAdmin().getLastName();
            case 3:
                Date now = new Date();
                return now;
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return columns[i];
    }

    public List<User> getUsers() {
        return users;
    }

    public void addClient(User clientSession) {
        users.add(clientSession);
        fireTableDataChanged();
    }

    public void removeClients() {
        Session.getInstance().getUsers().clear();
        users.clear();
        fireTableDataChanged();
    }

    public User removeClient(int selectedRow) {
        User clientToRemove = users.get(selectedRow);
        users.remove(selectedRow);
        fireTableDataChanged();
        return clientToRemove;
    }
}
