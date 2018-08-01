/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Client;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivan
 */
public class ClientTableModel extends AbstractTableModel {

    private final List<Client> clients;
    private String[] columns = {"ID", "Name", "Surname", "Date of birth", "Country", "Email"};

    public ClientTableModel(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        if (clients.isEmpty()) {
            return 0;
        }
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowInd, int colInd) {
        Client client = clients.get(rowInd);

        switch (colInd) {
            case 0:
                return client.getClientID();
            case 1:
                return client.getName();
            case 2:
                return client.getSurname();
            case 3:
                return client.getDateOfBirth();
            case 4:
                return client.getCountry();
            case 5:
                return client.getEmail();
            default:
                return "N/A";
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addRow(Client insertedClient) {
        clients.add(insertedClient);
        fireTableDataChanged();
    }

    public void updateRow(int selectedRow, Client updatedClient) {
        clients.set(selectedRow, updatedClient);
        fireTableDataChanged();
    }

    public void removeRow(int selectedRow) {
        clients.remove(selectedRow);
        fireTableDataChanged();
    }

}
