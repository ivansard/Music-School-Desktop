/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Professor;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivan
 */
public class ProfessorTableModel extends AbstractTableModel {

    private final List<Professor> professors;
    private final String[] columnNames = new String[]{"Professor ID", "Name", "Surname", "Date of birth", "Instrument", "Email"};

    public ProfessorTableModel(List<Professor> profs) {
        this.professors = profs;
    }

    @Override
    public int getRowCount() {
        if(professors.isEmpty()){
            return 0;
        }
        return professors.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    public List<Professor> getProfessors() {
        return professors;
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Professor prof = professors.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return prof.getProfessorID();
            case 1:
                return prof.getName();
            case 2:
                return prof.getSurname();
            case 3:
                return prof.getDateOfBirth();
            case 4:
                return prof.getInstrument();
            case 5:
                return prof.getEmail();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public void addRow(Professor prof){
        professors.add(prof);
        fireTableDataChanged();
    }
    
    public void removeRow(Professor prof){
        professors.remove(prof);
        fireTableDataChanged();
    }
    
    public void removeRow(int index){
        professors.remove(index);
        fireTableDataChanged();
    }
    
    public void updateRow(int index, Professor prof){
        Professor professorToUpdate = professors.get(index);
        professorToUpdate.setProfessorID(prof.getProfessorID());
        professorToUpdate.setName(prof.getName());
        professorToUpdate.setSurname(prof.getSurname());
        professorToUpdate.setDateOfBirth(prof.getDateOfBirth());
        professorToUpdate.setInstrument(prof.getInstrument());
        professorToUpdate.setEmail(prof.getEmail());
        fireTableDataChanged();
    }
    
    
    

}
