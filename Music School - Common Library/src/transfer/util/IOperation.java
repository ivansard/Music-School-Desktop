/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.util;

/**
 *
 * @author Ivan
 */
public interface IOperation {

    public static final int GET_ALL_PROFESSORS = 1;
    public static final int INSERT_PROFESSOR = 2;
    public static final int UPDATE_PROFESSOR = 3;
    public static final int DELETE_PROFESSOR = 4;
    public static int GET_PROFESSOR_BY_ID = 5;
    public static int GET_USER_BY_USERNAME = 6;
    public static int INSERT_COURSE = 7;
    public static int GET_ALL_COURSES = 8;
    public static int GET_ALL_CLIENTS = 9;
    public static int GET_CLIENT_BY_ID = 10;
    public static int INSERT_CLIENT = 11;
    public static int UPDATE_CLIENT = 12;
    public static int DELETE_CLIENT = 13;
    public static int GET_ALL_COURSES_BY_PROFESSOR = 14;
    public static int DELETE_COURSE = 15;
    public static int UPDATE_COURSE = 16;
    public static int CONNECT = 17;
    public static int SHUTDOWN = 18;
    public static int INSERT_PURCHASE = 19;
    public static int GET_ALL_PURCHASES = 20;
}
