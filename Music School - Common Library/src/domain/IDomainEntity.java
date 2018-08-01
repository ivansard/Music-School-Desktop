/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;

/**
 *
 * @author Ivan
 */
public interface IDomainEntity {
    public String getTableName();
    public String getColumnsNamesForInsert();
    public String getColumnsNamesAndValuesForUpdate();
    public String getColumnsValuesForInsert();
    public boolean isIdAutoIncrement();
    public void setAutoIncrementId(int id);
    public String getIdAndValueEquation();
    public String getCond();
    public IDomainEntity setValues(ResultSet rs);
    public String getWithJoin();
    
}
