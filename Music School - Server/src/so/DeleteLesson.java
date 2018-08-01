/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.IDomainEntity;
import domain.Lesson;

/**
 *
 * @author Ivan
 */
public class DeleteLesson extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if(domainEntity instanceof Lesson){
            Lesson lesson = (Lesson) domainEntity;
        } else {
            throw new Exception("Not a lesson!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        dbBroker.delete(domainEntity);
    }
    
}
