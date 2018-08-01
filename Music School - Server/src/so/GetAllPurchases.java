/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Client;
import domain.Course;
import domain.IDomainEntity;
import domain.Purchase;

/**
 *
 * @author Ivan
 */
public class GetAllPurchases extends AbstractGenericOperation {

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if (domainEntity instanceof Purchase) {
            Purchase purhcase = (Purchase) domainEntity;
        } else {
            throw new Exception("Not a purchase!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        list = dbBroker.findAll(domainEntity);

        for (IDomainEntity iDomainEntity : list) {
            Purchase purchase = (Purchase) iDomainEntity;
            setClient(purchase);
            setCourse(purchase);
        }
    }

    private void setClient(Purchase purchase) {
        Client clientOld = purchase.getClient();
        IDomainEntity clientNew = dbBroker.findById(clientOld);
        Client clientFinal = (Client) clientNew;
        purchase.setClient(clientFinal);
    }

    private void setCourse(Purchase purchase) {
        Course courseOld = purchase.getCourse();
        IDomainEntity courseNew = dbBroker.findByID(courseOld);
        Course courseFinal = (Course) courseNew;
        purchase.setCourse(courseFinal);
    }

}
