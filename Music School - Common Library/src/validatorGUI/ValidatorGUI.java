/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validatorGUI;

/**
 *
 * @author Ivan
 */
public class ValidatorGUI {
    
    private static ValidatorGUI instance;

    private ValidatorGUI() {

    }

    public static ValidatorGUI getInstance() {
        if (instance == null) {
            instance = new ValidatorGUI();
        }
        return instance;
    }

    public boolean validatePort(String text) {
        Integer port = null;
        try {
            port = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
        return (port > 0 && port <= 65535);
    }

    public boolean validateIPAddress(String text) {
        String[] values = text.split(".");
        for (String value : values) {
            Integer number = null;
            try {
                number = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();;
                return false;
            }
            if (number < 0 || number > 255) {
                return false;
            }
        }
        return true;
    }

    public boolean validateAlphaNumeric(String text) {
        String pattern = "^[a-zA-Z0-9_ ]*$";
        return text.matches(pattern);
    }
    
    public boolean validateAlpha(String text) {
        String pattern = "^[A-Z][a-z]*$";
        return text.matches(pattern);
    }
    
    public boolean validateNumeric(String text) {
        String pattern = "^\\d*$";
        return text.matches(pattern);
    }
    
    public boolean validateEmail(String text){
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return text.matches(pattern);
    }
}
