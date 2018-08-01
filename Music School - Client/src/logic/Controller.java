/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Client;
import domain.Course;
import domain.Professor;
import domain.Admin;
import domain.Purchase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import session.Session;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author Ivan
 */
public class Controller {

    private static Controller instance;
//    private DBBroker broker;

    public Controller() {
//        this.broker = DBBroker.getInstance();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static Admin connectToServer(Admin admin) throws IOException, ClassNotFoundException, Exception {
        RequestObject request = new RequestObject();
        request.setOperation(IOperation.CONNECT);
        request.setData(admin);
        Socket socket = Session.getInstance().getSocket();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
        out.flush();

        //Wait for response
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ResponseObject response = (ResponseObject) in.readObject();
        int status = response.getStatus();
        if (status == IStatus.OK) {
            return (Admin) response.getData();
        } else {
            throw new Exception("Error in retrieving admin!!");
        }
    }

    public Professor insertProfessor(Professor prof)
            throws SQLException, IOException, IOException, ClassNotFoundException {

        // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
        Socket socket = Session.getInstance().getSocket();
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        RequestObject request = new RequestObject();
        request.setOperation(IOperation.INSERT_PROFESSOR);
        request.setData(prof);
        // Slanje zahteva do servera
        outSocket.writeObject(request);
        outSocket.flush();

        //Kreiranje input stream-a i citanje response object-a iz njega
        ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
        ResponseObject response = (ResponseObject) inSocket.readObject();

        if (response.getStatus() == IStatus.OK) {
            Professor p = (Professor) response.getData();
            return p;
        } else {
            throw new IOException("Issue in communication with server!");
        }

    }
//    
//     public static List<DepartmentEntity> getAllDepartments() throws Exception {
//        RequestObject request = new RequestObject();
//        request.setOperation(IOperation.GET_ALL_DEPARTMENTS);
//        DepartmentEntity department = new DepartmentEntity();
//        request.setData(department);
//        Socket socket = Session.getInstance().getSocket();
//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//        out.writeObject(request);
//        out.flush();
//
//        //Wait for response
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//        ResponseObject response = (ResponseObject) in.readObject();
//        int code = response.getCode();
//        if (code == IStatus.OK) {
//            return (List<DepartmentEntity>) response.getData();
//        } else {
//            throw new Exception("Error in retrieving departments!");
//        }
//    }

    public List<Professor> getAllProfessors() {
        ObjectOutputStream outSocket = null;
        List<Professor> professors = new ArrayList<>();
        try {
            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            Professor professor = new Professor();
            request.setData(professor);
            request.setOperation(IOperation.GET_ALL_PROFESSORS);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                professors = (List<Professor>) response.getData();
                return professors;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return professors;
    }

    public Professor getProfessorByID(int id) throws SQLException {

        ObjectOutputStream outSocket = null;
        Professor prof = new Professor(id);
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.GET_PROFESSOR_BY_ID);
            request.setData(prof);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                prof = (Professor) response.getData();
                System.out.println("Iscitao sam profesore i da li su prazni? Odgovor: " + prof == null);
                return prof;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return prof;
    }

    public void deleteProfessorByID(int id) {
        ObjectOutputStream outSocket = null;
        Professor professorToDelete = new Professor(id);
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.DELETE_PROFESSOR);
            request.setData(professorToDelete);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                return;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    public Professor updateProfessor(Professor prof) {
        ObjectOutputStream outSocket = null;
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.UPDATE_PROFESSOR);
            request.setData(prof);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Professor updatedProf = (Professor) response.getData();
                return updatedProf;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Admin getAdminByUsername(Admin user) {
        ObjectOutputStream outSocket = null;
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.GET_USER_BY_USERNAME);
            request.setData(user);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Admin userFromDb = (Admin) response.getData();
                return userFromDb;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Course saveCourse(Course courseToSave) {

        ObjectOutputStream outSocket = null;
        try {
            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.INSERT_COURSE);
            request.setData(courseToSave);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();
            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();
            if (response.getStatus() == IStatus.OK) {
                Course insertedCourse = (Course) response.getData();
                return insertedCourse;
            } else {
                throw new IOException("Issue in communication with server!");
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Course> getAllCourses() {
        ObjectOutputStream outSocket = null;
        List<Course> courses = new ArrayList<>();
        try {

            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());

            RequestObject request = new RequestObject();
            Course course = new Course();
            request.setData(course);
            request.setOperation(IOperation.GET_ALL_COURSES);

            outSocket.writeObject(request);
            outSocket.flush();

            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                courses = (List<Course>) response.getData();
                return courses;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public List<Client> getAllClients() {
        ObjectOutputStream outSocket = null;
        List<Client> clients = new ArrayList<>();
        try {
            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim 
            RequestObject request = new RequestObject();
            Client client = new Client();
            request.setData(client);
            request.setOperation(IOperation.GET_ALL_CLIENTS);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                clients = (List<Client>) response.getData();
                return clients;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Client getClientByID(int clientID) {
        ObjectOutputStream outSocket = null;
        Client client = new Client(clientID);
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.GET_CLIENT_BY_ID);
            request.setData(client);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                client = (Client) response.getData();
                return client;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Client insertClient(Client clientToInsert) {
        // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
        Socket socket = Session.getInstance().getSocket();
        ObjectOutputStream outSocket;
        try {
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.INSERT_CLIENT);
            request.setData(clientToInsert);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Client insertedClient = (Client) response.getData();
                return insertedClient;
            } else {
                throw new IOException("Issue in communication with server!");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Client updateClient(Client clientToUpdate) {
        ObjectOutputStream outSocket = null;
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.UPDATE_CLIENT);
            request.setData(clientToUpdate);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Client updatedClient = (Client) response.getData();
                return updatedClient;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public void deleteClientByID(int clientId) {
        ObjectOutputStream outSocket = null;
        Client clientToDelete = new Client(clientId);
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.DELETE_CLIENT);
            request.setData(clientToDelete);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                return;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Course> getCoursesByProfessor(Professor selectedProf) {
        ObjectOutputStream outSocket = null;
        List<Course> professorsCourses = new ArrayList<>();
        try {
            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            Course course = new Course(selectedProf);
            request.setData(course);
            request.setOperation(IOperation.GET_ALL_COURSES_BY_PROFESSOR);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                professorsCourses = (List<Course>) response.getData();
                return professorsCourses;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void deleteCourse(Course courseToDelete) {
        ObjectOutputStream outSocket = null;
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.DELETE_COURSE);
            request.setData(courseToDelete);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                return;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Course updateCourse(Course courseToUpdate) {
        ObjectOutputStream outSocket = null;
        try {

            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim profesorima
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.UPDATE_COURSE);
            request.setData(courseToUpdate);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Course updatedCourse = (Course) response.getData();
                return updatedCourse;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Purchase insertPurhcase(Purchase createdPurhcase) {
        // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
        Socket socket = Session.getInstance().getSocket();
        ObjectOutputStream outSocket;
        try {
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            RequestObject request = new RequestObject();
            request.setOperation(IOperation.INSERT_PURCHASE);
            request.setData(createdPurhcase);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                Purchase insertedPurhcase = (Purchase) response.getData();
                return insertedPurhcase;
            } else {
                throw new IOException("Issue in communication with server!");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public List<Purchase> getallPurchases() {
        ObjectOutputStream outSocket = null;
        List<Purchase> allPurchases = new ArrayList<>();
        try {
            // Kreiramo output stream naseg soketa da bi mogli da posaljemo zahtev
            Socket socket = Session.getInstance().getSocket();
            outSocket = new ObjectOutputStream(socket.getOutputStream());
            // Kreiramo zahtev za svim 
            RequestObject request = new RequestObject();
            Purchase purhcase = new Purchase();
            request.setData(purhcase);
            request.setOperation(IOperation.GET_ALL_PURCHASES);
            // Slanje zahteva do servera
            outSocket.writeObject(request);
            outSocket.flush();

            //Kreiranje input stream-a i citanje response object-a iz njega
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) inSocket.readObject();

            if (response.getStatus() == IStatus.OK) {
                allPurchases = (List<Purchase>) response.getData();
                return allPurchases;
            } else {
                throw new IOException("Issue in communication with server!");

            }

        } catch (IOException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
