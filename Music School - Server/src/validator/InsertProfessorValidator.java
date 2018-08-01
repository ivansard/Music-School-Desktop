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
public class InsertProfessorValidator extends ProfessorValidator{

    @Override
    public void validate(Professor value) throws Exception {
        isNull(value);
        isEmpty(value);
    }
    
}
