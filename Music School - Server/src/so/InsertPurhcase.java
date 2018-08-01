/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.IDomainEntity;
import domain.Purchase;

/**
 *
 * @author Ivan
 */
public class InsertPurhcase extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if(domainEntity instanceof Purchase){
            Purchase purchase = (Purchase) domainEntity;
        } else {
            throw new Exception("Not a purchase!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        entity = dbBroker.save(domainEntity);
    }
    
}
