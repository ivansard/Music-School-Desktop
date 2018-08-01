/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Course;
import domain.IDomainEntity;

/**
 *
 * @author Ivan
 */
public class DeleteCourse extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if(domainEntity instanceof Course){
            Course course = (Course) domainEntity;
        } else {
            throw new Exception("Not a course!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        dbBroker.delete(domainEntity);
    }
    
}
