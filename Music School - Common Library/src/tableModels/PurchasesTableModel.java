/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Purchase;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivan
 */
public class PurchasesTableModel extends AbstractTableModel {

    private List<Purchase> purchases;
    private String[] columns = {"Course", "Client", "Date of purchase"};

    public PurchasesTableModel(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public int getRowCount() {
        if (purchases.isEmpty()) {
            return 0;
        }
        return purchases.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowI, int colI) {
        Purchase purhcase = purchases.get(rowI);

        switch (colI) {
            case 0:
                return purhcase.getCourse().getName();
            case 1:
                return purhcase.getClient().toString();
            case 2:
                return purhcase.getDateOfPurchase();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return columns[i];
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

}
