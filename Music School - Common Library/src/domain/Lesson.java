/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class Lesson implements Serializable, IDomainEntity {

    private int lessonNumber;
    private Course course;
    private String name;
    private int duration;
    private String tablature;
    private LessonType type;

    public Lesson() {
    }

    public Lesson(Course course) {
        this.course = course;
    }

    public Lesson(int lessonNumber, Course course) {
        this.lessonNumber = lessonNumber;
        this.course = course;
    }

    public Lesson(int lessonNumber, Course course, String name, int duration, String tablature, LessonType type) {
        this.lessonNumber = lessonNumber;
        this.course = course;
        this.name = name;
        this.duration = duration;
        this.tablature = tablature;
        this.type = type;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTablature() {
        return tablature;
    }

    public void setTablature(String tablature) {
        this.tablature = tablature;
    }

    @Override
    public String getTableName() {
        return "lesson";
    }

    @Override
    public String getColumnsNamesForInsert() {
        return "lessonNumber, courseID, duration, tablature, lessonType, name";
    }

    @Override
    public String getColumnsNamesAndValuesForUpdate() {

        StringBuilder sb = new StringBuilder("duration = ").append(getDuration()).append(",")
                .append("tablature = ").append("'").append(getTablature()).append("' ,")
                .append("lessonType = ").append("'").append(getType().toString()).append("'");

        return sb.toString();
    }

    @Override
    public String getColumnsValuesForInsert() {

        System.out.println(getLessonNumber());
        System.out.println(getCourse().getCourseID());
        System.out.println(getDuration());
        System.out.println(getTablature());
        System.out.println(getType().toString());
        System.out.println(getName());

        return getLessonNumber() + "," + getCourse().getCourseID() + ", " + getDuration()
                + ", '" + getTablature() + "' , '" + getType().toString() + "'"
                + ", '" + getName() + "'";
    }

    @Override
    public boolean isIdAutoIncrement() {
        return false;
    }

    @Override
    public void setAutoIncrementId(int id) {
        return;
    }

    @Override
    public String getIdAndValueEquation() {
        return "lessonNumber = " + getLessonNumber() + " AND courseID = " + getCourse().getCourseID();
    }

    @Override
    public String getCond() {
        return "courseID = " + getCourse().getCourseID();
    }

    @Override
    public IDomainEntity setValues(ResultSet rs) {
        Lesson lesson = new Lesson();
        try {
            int lessonNumber = rs.getInt("lessonNumber");
            lesson.setLessonNumber(lessonNumber);

            int courseId = rs.getInt("courseID");
            Course course = new Course(courseId);

            lesson.setCourse(course);

            lesson.setDuration(rs.getInt(3));
            lesson.setTablature(rs.getString(4));
            String lesType = rs.getString(5);
            lesson.setType(LessonType.valueOf(lesType));
            lesson.setName(rs.getString("name"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return lesson;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return lessonNumber + ". " + name;
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
        final Lesson other = (Lesson) obj;
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.lessonNumber, other.lessonNumber)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String getWithJoin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
