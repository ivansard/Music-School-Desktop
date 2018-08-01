/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Client;
import domain.IDomainEntity;

/**
 *
 * @author Ivan
 */
public class DeleteClient extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if(domainEntity instanceof Client){
            Client client = (Client) domainEntity;
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        return ;
    }
    
}
