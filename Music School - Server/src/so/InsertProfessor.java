/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domain.IDomainEntity;
import domain.Professor;

/**
 *
 * @author Ivan
 */
public class InsertProfessor extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if(domainEntity instanceof Professor){
            Professor prof = (Professor) domainEntity;
        } else {
            throw new Exception("Not a professor!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        entity = dbBroker.save(domainEntity);
    }
    
}
