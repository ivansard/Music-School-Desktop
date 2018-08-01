/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class Purchase implements Serializable, IDomainEntity {

    private Course course;
    private Client client;
    private Date dateOfPurchase;

    public Purchase() {
    }

    public Purchase(Course course, Client client, Date dateOfPurchase) {
        this.course = course;
        this.client = client;
        this.dateOfPurchase = dateOfPurchase;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public String getTableName() {
        return "purchase";
    }

    @Override
    public String getColumnsNamesForInsert() {
        return "clientID, courseID, date_of_purchase";
    }

    @Override
    public String getColumnsNamesAndValuesForUpdate() {
        return "";
    }

    @Override
    public String getColumnsValuesForInsert() {
        java.sql.Date sqlDate = new java.sql.Date(getDateOfPurchase().getTime());

        return getClient().getClientID() + ", " + getCourse().getCourseID() + ", '" + sqlDate + "'";
    }

    @Override
    public boolean isIdAutoIncrement() {
        return false;
    }

    @Override
    public void setAutoIncrementId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIdAndValueEquation() {
        return "courseID = " + getCourse().getCourseID() + " AND clientID = " + getClient().getClientID();
    }

    @Override
    public String getCond() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IDomainEntity setValues(ResultSet rs) {
        Purchase purchase = new Purchase();
        try {

            Course course = new Course();
            course.setCourseID(rs.getInt("courseID"));
            purchase.setCourse(course);

            Client client = new Client();
            client.setClientID(rs.getInt("clientID"));
            purchase.setClient(client);

            purchase.setDateOfPurchase(rs.getDate("date_of_purchase"));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return purchase;
    }

    @Override
    public String getWithJoin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
