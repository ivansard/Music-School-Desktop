/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import domain.Professor;

/**
 *
 * @author Ivan
 */
public abstract class ProfessorValidator {

    public abstract void validate(Professor value) throws Exception;

    protected void isEmpty(Professor value) throws Exception {

        if (value.getProfessorID().toString().isEmpty()) {
            throw new Exception("Professor ID is empty!");
        }
        if (value.getName().isEmpty()) {
            throw new Exception("Name is empty!");
        }
        if (value.getSurname().isEmpty()) {
            throw new Exception("Surname ID is empty!");
        }
        if (value.getDateOfBirth().toString().isEmpty()) {
            throw new Exception("Date of birth ID is empty!");
        }
        if (value.getInstrument().isEmpty()) {
            throw new Exception("Instrument ID is empty!");
        }
        if (value.getEmail().isEmpty()) {
            throw new Exception("Email ID is empty!");
        }

    }

    protected void isNull(Professor value) throws Exception {
        if (value != null) {
            if (value.getProfessorID() == null) {
                throw new Exception("Professor ID is null!");
            }
            if (value.getName() == null) {
                throw new Exception("Name is null!");
            }
            if (value.getSurname() == null) {
                throw new Exception("Surname ID is null!");
            }
            if (value.getDateOfBirth() == null) {
                throw new Exception("Date of birth ID is null!");
            }
            if (value.getInstrument() == null) {
                throw new Exception("Instrument ID is null!");
            }
            if (value.getEmail() == null) {
                throw new Exception("Email ID is null!");
            }
        } else {
            throw new Exception("Professor is null!");
        }
    }
    
    public void isCorrect(){
        // IMPLEMENTIRATI !!!!!
    }
}
