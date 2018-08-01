/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domain.IDomainEntity;
import java.util.List;

/**
 *
 * @author Ivan
 */
public abstract class AbstractGenericOperation {

    protected DBBroker dbBroker;
    protected IDomainEntity entity;
    List<IDomainEntity> list;

    public AbstractGenericOperation() {
        dbBroker = new DBBroker();
        entity = null;
    }

    public IDomainEntity getEntity() {
        return entity;
    }

    public List<IDomainEntity> getList() {
        return list;
    }

    public void templateExecute(IDomainEntity domainEntity) throws Exception {
        try {
            validate(domainEntity);
            try {
                startTransaction();
                execute(domainEntity);
                commitTransaction();
            } catch (Exception e) {
                rollbackTransaction();
                e.printStackTrace();
                throw new Exception("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract void validate(IDomainEntity domainEntity) throws Exception;

    protected abstract void execute(IDomainEntity domainEntity) throws Exception;

    private void rollbackTransaction() throws Exception {
        dbBroker.rollbackTransaction();
    }

    private void commitTransaction() throws Exception {
        dbBroker.commitTransaction();
    }

    private void startTransaction() throws Exception {
        dbBroker.startTransaction();
    }
}
