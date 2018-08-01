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
public class FindLesson extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
          if (domainEntity instanceof Lesson) {
            Lesson lessons = (Lesson) domainEntity;
        } else {
            throw new Exception("Not a lesson!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        entity = dbBroker.findById(domainEntity);
    }
    
}
