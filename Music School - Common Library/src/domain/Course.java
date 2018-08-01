/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class Course implements Serializable, IDomainEntity {

    private int courseID;
    private String name;
    private double price;
    private int duration;
    private Professor professor;
    private List<Lesson> lessons;

    public Course() {
        this.lessons = new ArrayList<Lesson>();
    }

    public Course(int courseID) {
        this.courseID = courseID;
        this.lessons = new ArrayList<>();
    }

    public Course(Professor professor) {
        this.professor = professor;
        this.lessons = new ArrayList<>();
    }

    public Course(int courseID, String name, double price, int duration, Professor professor) {
        this.courseID = courseID;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.professor = professor;
        this.lessons = new ArrayList<Lesson>();
    }

    public Course(String name, double price, int duration, Professor professor) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.professor = professor;
        this.lessons = new ArrayList<Lesson>();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String getTableName() {
        return "course";
    }

    @Override
    public String getColumnsNamesForInsert() {
        return "name, price, duration, professorID";
    }

    @Override
    public String getColumnsNamesAndValuesForUpdate() {
        StringBuilder sb = new StringBuilder("name = ").append("'").append(getName()).append("'").append(",")
                .append(("price = ")).append(getPrice()).append(",")
                .append(("duration = ")).append(getDuration()).append(",")
                .append(("professorID = ")).append(getProfessor().getProfessorID());

        return sb.toString();
    }

    @Override
    public String getColumnsValuesForInsert() {
        return "'" + getName() + "', " + getPrice() + ", " + getDuration() + ", "
                + getProfessor().getProfessorID();
    }

    @Override
    public boolean isIdAutoIncrement() {
        return true;
    }

    @Override
    public void setAutoIncrementId(int id) {
        setCourseID(id);
    }

    @Override
    public String getIdAndValueEquation() {
        return "courseID = " + getCourseID();

    }

    @Override
    public String getCond() {
        return "professorID = " + getProfessor().getProfessorID();
    }

    @Override
    public IDomainEntity setValues(ResultSet rs) {
        Course course = new Course();
        try {
            Professor professor = new Professor();
            professor.setProfessorID(rs.getInt("professorID"));
            course.setProfessor(professor);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return course;
    }

    @Override
    public String toString() {
        return courseID + ", " + name;
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.courseID, other.courseID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String getWithJoin() {
        return "course c INNER JOIN professor p ON c.professorID = p.professorID INNER JOIN lessonNumber ln ON ln.courseID = c.courseID";
    }
    
    

}
