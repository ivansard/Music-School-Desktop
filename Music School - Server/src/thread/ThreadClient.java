/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import db.DBBroker;
import domain.Client;
import domain.Course;
import domain.IDomainEntity;
import domain.Lesson;
import domain.Professor;
import domain.Admin;
import domain.Purchase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.AbstractGenericOperation;
import so.DeleteClient;
import so.DeleteCourse;
import so.DeleteProfessor;
import so.FindClient;
import so.FindLesson;
import so.FindProfessor;
import so.GetAllClients;
import so.GetAllCourses;
import so.GetAllCoursesCond;
import so.GetAllProfessors;
import so.GetAllPurchases;
import so.InsertClient;
import so.InsertCourse;
import so.InsertLesson;
import so.InsertProfessor;
import so.InsertPurhcase;
import so.UpdateClient;
import so.UpdateCourse;
import so.UpdateProfessor;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;
import util.Session;
import util.User;

/**
 *
 * @author Ivan
 */
public class ThreadClient extends Thread {

    Socket socket;

    public ThreadClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //Prihvatanje poruke od klijenta
                ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
                RequestObject request = (RequestObject) inSocket.readObject();
                // Slanje odgovora do klijenta
                ResponseObject response = new ResponseObject();
                int operation = request.getOperation();
                switch (operation) {
                    case IOperation.GET_ALL_PROFESSORS:
                        Professor professor = (Professor) request.getData();
                        AbstractGenericOperation getAllProfs = new GetAllProfessors();
                        getAllProfs.templateExecute(professor);
                        response.setStatus(IStatus.OK);
                        response.setData(getAllProfs.getList());
                        break;
                    case IOperation.INSERT_PROFESSOR:
                        Professor profToInsert = (Professor) request.getData();
                        AbstractGenericOperation insertProf = new InsertProfessor();
                        insertProf.templateExecute(profToInsert);
                        response.setStatus(IStatus.OK);
                        response.setData(insertProf.getEntity());
                        break;
                    case IOperation.GET_PROFESSOR_BY_ID:
                        Professor profFind = (Professor) request.getData();
                        AbstractGenericOperation findProf = new FindProfessor();
                        findProf.templateExecute(profFind);
                        response.setStatus(IStatus.OK);
                        response.setData(findProf.getEntity());
                        break;
                    case IOperation.DELETE_PROFESSOR:
                        Professor profToDelete = (Professor) request.getData();
                        AbstractGenericOperation deleteProf = new DeleteProfessor();
                        deleteProf.templateExecute(profToDelete);
                        response.setStatus(IStatus.OK);
                        response.setData(deleteProf.getEntity());
                        break;
                    case IOperation.GET_USER_BY_USERNAME:
                        Admin sentAdmin = (Admin) request.getData();
                        Admin adminFromDb = (Admin) DBBroker.getInstance().findById(sentAdmin);
                        response.setStatus(IStatus.OK);
                        response.setData(adminFromDb);
                        break;
                    case IOperation.DELETE_COURSE:
                        Course courseToDelete = (Course) request.getData();
                        AbstractGenericOperation deleteCourse = new DeleteCourse();
                        deleteCourse.templateExecute(courseToDelete);
                        response.setStatus(IStatus.OK);
                        break;
                    case IOperation.UPDATE_COURSE:
                        Course courseToUpdate = (Course) request.getData();
                        AbstractGenericOperation updateCourse = new UpdateCourse();
                        updateCourse.templateExecute(courseToUpdate);
                        response.setStatus(IStatus.OK);
                        response.setData(updateCourse.getEntity());
                        break;
                    case IOperation.UPDATE_PROFESSOR:
                        Professor profToUpdate = (Professor) request.getData();
                        AbstractGenericOperation updateProf = new UpdateProfessor();
                        updateProf.templateExecute(profToUpdate);
                        response.setStatus((IStatus.OK));
                        response.setData(updateProf.getEntity());
                        break;
                    case IOperation.INSERT_COURSE:
                        Course courseToInsert = (Course) request.getData();
                        AbstractGenericOperation insertCourse = new InsertCourse();
                        insertCourse.templateExecute(courseToInsert);
                        Course insertedCourse = (Course) insertCourse.getEntity();

                        for (Lesson lesson : insertedCourse.getLessons()) {
                            lesson.setCourse(insertedCourse);
                        }
                        for (Lesson lesson : insertedCourse.getLessons()) {
                            AbstractGenericOperation insertLesson = new InsertLesson();
                            insertLesson.templateExecute(lesson);
                        }
                        response.setStatus((IStatus.OK));
                        response.setData(insertedCourse);
                        break;
                    case IOperation.GET_ALL_COURSES:
//                        Course course = (Course) request.getData();
                        List<Course> allCourses = DBBroker.getInstance().getAllCourses();
                        if (allCourses != null) {
                            response.setStatus(IStatus.OK);
                            response.setData(allCourses);
                        } else {
                            throw new Exception("Courses are null!");
                        }

//                        List<Course> allCourses = new ArrayList<>();
//                        for (IDomainEntity entity : getAllCourses.getList()) {
//                            Course courseToAdd = (Course) entity;
//                            allCourses.add(courseToAdd);
//                        }
//                        for (IDomainEntity entity : getAllCourses.getList()) {
//                            Course c = (Course) entity;
//
//                            AbstractGenericOperation findCourseProf = new FindProfessor();
//                            findCourseProf.templateExecute(c.getProfessor());
//                            c.setProfessor((Professor) findCourseProf.getEntity());
//
//                            int lessonNumber = 1;
//                            boolean notNull = true;
//                            do {
//                                Lesson courseLesson = new Lesson(lessonNumber, course);
//                                AbstractGenericOperation findCourseLesson = new FindLesson();
//                                findCourseLesson.templateExecute(courseLesson);
//                                if (findCourseLesson.getEntity() != null) {
//                                    c.getLessons().add((Lesson) findCourseLesson.getEntity());
//                                    lessonNumber++;
//                                } else {
//                                    notNull = false;
//                                }
//                            } while (notNull);
//                            allCourses.add(c);
////                        }
//                        response.setStatus(IStatus.OK);
//                        response.setData(allCourses);
                        break;
                    case IOperation.GET_ALL_CLIENTS:
                        Client client = (Client) request.getData();
                        AbstractGenericOperation getAllClients = new GetAllClients();
                        getAllClients.templateExecute(client);
                        response.setStatus(IStatus.OK);
                        response.setData(getAllClients.getList());
                        break;
                    case IOperation.GET_CLIENT_BY_ID:
                        Client clientWithId = (Client) request.getData();
                        AbstractGenericOperation findClient = new FindClient();
                        findClient.templateExecute(clientWithId);
                        response.setStatus(IStatus.OK);
                        response.setData(findClient.getEntity());
                        break;
                    case IOperation.INSERT_CLIENT:
                        Client clientToInsert = (Client) request.getData();
                        AbstractGenericOperation insertClient = new InsertClient();
                        insertClient.templateExecute(clientToInsert);
                        response.setStatus(IStatus.OK);
                        response.setData(insertClient.getEntity());
                        break;
                    case IOperation.UPDATE_CLIENT:
                        Client clientToUpdate = (Client) request.getData();
                        AbstractGenericOperation updateClient = new UpdateClient();
                        updateClient.templateExecute(clientToUpdate);
                        response.setStatus(IStatus.OK);
                        response.setData(updateClient.getEntity());
                        break;
                    case IOperation.DELETE_CLIENT:
                        Client clientToDelete = (Client) request.getData();
                        AbstractGenericOperation deleteClient = new DeleteClient();
                        deleteClient.templateExecute(clientToDelete);
                        response.setStatus(IStatus.OK);
                        break;
                    case IOperation.GET_ALL_COURSES_BY_PROFESSOR:
                        Course professorsCourse = (Course) request.getData();
                        AbstractGenericOperation getCoursesByProf = new GetAllCoursesCond();
                        getCoursesByProf.templateExecute(professorsCourse);
                        response.setStatus(IStatus.OK);
                        response.setData(getCoursesByProf.getList());
                        break;
                    case IOperation.INSERT_PURCHASE:
                        Purchase purhcaseToInsert = (Purchase) request.getData();
                        AbstractGenericOperation insertPurchase = new InsertPurhcase();
                        insertPurchase.templateExecute(purhcaseToInsert);
                        response.setStatus(IStatus.OK);
                        response.setData(insertPurchase.getEntity());
                        break;
                    case IOperation.GET_ALL_PURCHASES:
                        Purchase purchase = (Purchase) request.getData();
                        AbstractGenericOperation getAllPurchases = new GetAllPurchases();
                        getAllPurchases.templateExecute(purchase);
                        response.setStatus(IStatus.OK);
                        response.setData(getAllPurchases.getList());
                        break;
                    case IOperation.CONNECT:
                        Admin admin = (Admin) request.getData();
                        try {
                            response.setStatus(IStatus.OK);
                            User newUser = new User(admin, this, LocalTime.now());
                            Session.getInstance().getUsers().add(newUser);
                            response.setData(admin);
                        } catch (Exception e) {
                            response.setStatus(IStatus.ERROR);
                            response.setMessage(e.getMessage());
                        }
                        break;
                    default:
                        break;
                }
                ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
                outSocket.writeObject(response);
                outSocket.flush();
            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
//                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void notifyShutdown() {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.SHUTDOWN);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(request);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
