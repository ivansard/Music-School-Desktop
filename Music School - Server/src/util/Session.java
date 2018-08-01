/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ivan
 */
public class Session {

    private static Session instance;
    private Map<String, Object> map;
    private List<User> users;

    private Session() {
        map = new HashMap<>();
        users = new ArrayList<>();
    }

    public static Session getInstance() {
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public List<User> getUsers() {
        return users;
    }

}
