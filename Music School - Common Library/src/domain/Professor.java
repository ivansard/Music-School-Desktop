/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
public class Professor implements Serializable, IDomainEntity {

    private Integer professorID;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String instrument;
    private String email;
    private List<Course> courses;

    public Professor() {
    }

    public Professor(int professorID, String name, String surname, Date dateOfBirth, String instrument, String email) {
        this.professorID = professorID;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.instrument = instrument;
        this.email = email;
        this.courses = new ArrayList<Course>();
    }

    public Professor(int professorID) {
        this.professorID = professorID;
    }

    public Professor(String name, String surname, Date dateOfBirth, String instrument, String email) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.instrument = instrument;
        this.email = email;
        this.courses = new ArrayList<Course>();
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return new StringBuilder("Name: ").append(this.getName()).append(" , Surname: ").append(this.getSurname()).append(", Instrument: ").append(this.getInstrument()).toString();
    }

    @Override
    public String getTableName() {
        return "professor";
    }

    @Override
    public String getColumnsNamesForInsert() {
        return "name, surname, date_of_birth, instrument, email";
    }

    @Override
    public String getColumnsValuesForInsert() {
        java.sql.Date sqlDate = new java.sql.Date(getDateOfBirth().getTime());

        return "'" + getName() + "', '" + getSurname() + "', '" + sqlDate + "', '"
                + getInstrument() + "', '" + getEmail() + "'";
    }

    @Override
    public boolean isIdAutoIncrement() {
        return true;
    }

    @Override
    public void setAutoIncrementId(int id) {
        setProfessorID(id);
    }

    @Override
    public String getIdAndValueEquation() {
        return "professorID = " + getProfessorID();
    }

    @Override
    public String getCond() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IDomainEntity setValues(ResultSet rs) {
        Professor prof = new Professor();
        try {
            prof.setProfessorID(rs.getInt(1));
            prof.setName(rs.getString(2));
            prof.setSurname(rs.getString(3));
            prof.setDateOfBirth(rs.getDate(4));
            prof.setInstrument(rs.getString(5));
            prof.setEmail(rs.getString(6));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return prof;
    }

    @Override
    public String getColumnsNamesAndValuesForUpdate() {

        java.sql.Date sqlDate = new java.sql.Date(getDateOfBirth().getTime());

        StringBuilder sb = new StringBuilder("name = ").append("'").append(getName()).append("'").append(",")
                .append(("surname = ")).append("'").append(getSurname()).append("'").append(",")
                .append(("date_of_birth = ")).append("'").append(sqlDate).append("'").append(",")
                .append(("instrument = ")).append("'").append(getInstrument()).append("'").append(",")
                .append(("email = ")).append("'").append(getEmail()).append("'").append(",");

        return sb.toString();
    }

    public Integer getProfessorID() {
        return professorID;
    }

    public void setProfessorID(Integer professorID) {
        this.professorID = professorID;
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
        final Professor other = (Professor) obj;
        if (!Objects.equals(this.professorID, other.professorID)) {
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
