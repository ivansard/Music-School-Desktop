/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class Client implements Serializable, IDomainEntity {

    private int clientID;
    private String name;
    private String surname;
    private String country;
    private Date dateOfBirth;
    private String email;
    private List<Course> courses = new ArrayList<>();

    public Client() {
    }

    public Client(int clientID) {
        this.clientID = clientID;
    }

    public Client(String name, String surname, String country, Date dateOfBirth, String email) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public Client(int clientID, String name, String surname, String country, Date dateOfBirth, String email) {
        this.clientID = clientID;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String getTableName() {
        return "client";
    }

    @Override
    public String getColumnsNamesForInsert() {
        return "name, surname, date_of_birth, country, email";
    }

    @Override
    public String getColumnsNamesAndValuesForUpdate() {

        java.sql.Date sqlDate = new java.sql.Date(getDateOfBirth().getTime());

        StringBuilder sb = new StringBuilder("name =").append("'").append(getName()).append("'").append(",")
                .append("surname =").append("'").append(getSurname()).append("'").append(",")
                .append("date_of_birth =").append("'").append(sqlDate).append("'").append(",")
                .append("country =").append("'").append(getCountry()).append("'").append(",")
                .append("email =").append("'").append(getEmail()).append("'");

        return sb.toString();
    }

    @Override
    public String getColumnsValuesForInsert() {
        java.sql.Date sqlDate = new java.sql.Date(getDateOfBirth().getTime());

        return "'" + getName() + "' ,'" + getSurname() + "', '" + sqlDate
                + "', '" + getCountry() + "', '" + getEmail() + "'";
    }

    @Override
    public boolean isIdAutoIncrement() {
        return true;
    }

    @Override
    public void setAutoIncrementId(int id) {
        setClientID(id);
    }

    @Override
    public String getIdAndValueEquation() {
        return "clientID = " + getClientID();
    }

    @Override
    public String getCond() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IDomainEntity setValues(ResultSet rs) {
        Client client = new Client();
        try {
            client.setClientID(rs.getInt("clientID"));
            client.setName(rs.getString("name"));
            client.setSurname(rs.getString("surname"));
            client.setCountry(rs.getString("country"));
            client.setEmail(rs.getString("email"));
            client.setDateOfBirth(rs.getDate("date_of_birth"));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return client;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.clientID, other.clientID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        return true;
    }

    @Override
    public String getWithJoin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
