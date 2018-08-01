/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domain.Course;
import domain.IDomainEntity;
import domain.Lesson;
import domain.LessonType;
import domain.Professor;
import domain.Admin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.AbstractGenericOperation;
import so.FindProfessor;
import so.GetAllLessons;

/**
 *
 * @author Ivan
 */
public class DBBroker {

    private static DBBroker instance;
    private Connection connection;
    private DBResources resources;

    public DBBroker() {
        resources = new DBResources();
        setUpConnection();
    }

    public DBResources getResources() {
        return resources;
    }

    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public void setUpConnection() {
//        String url = "jdbc:mysql://localhost:3306/softveri_seminarski";
        String url = resources.getUrl();
        String username = resources.getUsername();
        String password = resources.getPassword();

        try {
            this.connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void disconnectConnection() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public IDomainEntity save(IDomainEntity domainEntity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(domainEntity.getTableName())
                    .append("(")
                    .append(domainEntity.getColumnsNamesForInsert()).append(")").append(" VALUES")
                    .append(" (")
                    .append(domainEntity.getColumnsValuesForInsert())
                    .append(")");
            String query = sb.toString();
            System.out.println("Query: " + query);
            Statement stat = connection.createStatement();

            if (domainEntity.isIdAutoIncrement()) {
                stat.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = stat.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    domainEntity.setAutoIncrementId(id);
                }
            } else {
                stat.executeUpdate(query);
            }
            return domainEntity;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public IDomainEntity update(IDomainEntity domainEntity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(domainEntity.getTableName())
                    .append(" SET ")
                    .append(domainEntity.getColumnsNamesAndValuesForUpdate())
                    .append(" WHERE ")
                    .append(domainEntity.getIdAndValueEquation() + ";");
            String query = sb.toString();
            System.out.println("Query: " + query);
            Statement stat = connection.createStatement();

            stat.executeUpdate(query);

            return findById(domainEntity);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public IDomainEntity findById(IDomainEntity domainEntity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(domainEntity.getTableName())
                    .append(" WHERE ")
                    .append(domainEntity.getIdAndValueEquation());
            String query = sb.toString();
            System.out.println("Query: " + query);

            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);

            if (rs.next()) {
                domainEntity = domainEntity.setValues(rs);
                return domainEntity;
            } else {
                throw new Exception(domainEntity.getTableName() + " does not exist!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void delete(IDomainEntity domainEntity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(domainEntity.getTableName())
                    .append(" WHERE ")
                    .append(domainEntity.getIdAndValueEquation());
            String query = sb.toString();
            System.out.println("Query: " + query);

            Statement stat = connection.createStatement();
            stat.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return;
    }

    public List<IDomainEntity> findByJoin(IDomainEntity domainEntity) {
        List<IDomainEntity> list = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(domainEntity.getWithJoin());
            String query = sb.toString();
            System.out.println("Query: " + query);
            Statement s = connection.createStatement();

            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                list.add(domainEntity.setValues(rs));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<IDomainEntity> findAll(IDomainEntity domainEntity) throws Exception {
        List<IDomainEntity> list = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(domainEntity.getTableName());
            String query = sb.toString();
            System.out.println("Query: " + query);
            Statement s = connection.createStatement();

            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                list.add(domainEntity.setValues(rs));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<IDomainEntity> findByCond(IDomainEntity ide) throws SQLException {

        List<IDomainEntity> list = new ArrayList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(ide.getTableName())
                    .append(" WHERE ")
                    .append(ide.getCond());
            String query = sb.toString();
            System.out.println("Query: " + query);

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                list.add(ide.setValues(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteByCond(IDomainEntity domainEntity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(domainEntity.getTableName())
                    .append(" WHERE ")
                    .append(domainEntity.getCond());
            String query = sb.toString();
            System.out.println("Query: " + query);

            Statement stat = connection.createStatement();
            stat.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return;
    }

    public List<String> getDatabases(Connection conn) throws Exception {
        List<String> databaseNames = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SHOW DATABASES");

            if (stmt.execute("SHOW DATABASES")) {
                rs = stmt.getResultSet();
            }

            while (rs.next()) {
                databaseNames.add(rs.getString("Database"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return databaseNames;
    }

    public List<Course> getAllCourses() {
//        setUpConnection();
        List<Course> allCourses = new ArrayList<>();

        try {
            String query = "SELECT * FROM course";
            Statement stat = connection.createStatement();

            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                Course course = new Course(courseID);

                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int duration = rs.getInt("duration");

                int profID = rs.getInt("professorID");
                Professor professorToFind = new Professor(profID);
                IDomainEntity professorNew = findById(professorToFind);
                Professor professorFinal = (Professor) professorNew;

                List<Lesson> courseLessons = getAllCourseLessons(course);
//                Lesson lesson = new Lesson(course);
//                AbstractGenericOperation getAllLessons = new GetAllLessons();
//                getAllLessons.templateExecute(lesson);
//                List<IDomainEntity> courseLessons = getAllLessons.getList();

                course.setLessons(courseLessons);

                course.setProfessor(professorFinal);
                course.setDuration(duration);
                course.setPrice(price);
                course.setName(name);

                allCourses.add(course);
            }
            return allCourses;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private List<Lesson> getAllCourseLessons(Course course) {
//        setUpConnection();
        List<Lesson> courseLessons = new ArrayList<>();

        try {
            String query = "SELECT * FROM lesson WHERE courseID = " + course.getCourseID();
            Statement stat = connection.createStatement();

            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {

                int lessonNumber = rs.getInt("lessonNumber");
                int duration = rs.getInt("duration");
                String tablature = rs.getString("tablature");
                String name = rs.getString("name");
                LessonType type = LessonType.valueOf(rs.getString("lessonType"));

                Lesson lesson = new Lesson(lessonNumber, course, name, duration, tablature, type);

                courseLessons.add(lesson);
            }
            return courseLessons;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Course findByID(Course givenCourse) {
        setUpConnection();

        try {
            String query = "SELECT * FROM course WHERE courseID = " + givenCourse.getCourseID();
            Statement stat = connection.createStatement();

            ResultSet rs = stat.executeQuery(query);
            if (rs.next()) {

                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int duration = rs.getInt("duration");

                int profId = rs.getInt("professorID");
                IDomainEntity prof = new Professor(profId);
                Professor profFinal = (Professor) findById(prof);

                Course finalCourse = new Course(givenCourse.getCourseID(), name, price, duration, profFinal);
                return finalCourse;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

}
