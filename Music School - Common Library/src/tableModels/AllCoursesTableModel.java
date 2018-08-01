/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Course;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivan
 */
public class AllCoursesTableModel extends AbstractTableModel {

    private List<Course> courses;
    private String[] columns = {"Name", "Price", "Number of lessons", "Professor", "Instrument"};

    public AllCoursesTableModel(List<Course> courses) {
        this.courses = courses;
    }
    
    

    @Override
    public int getRowCount() {
        if (courses.isEmpty()) {
            return 0;
        }
        return courses.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowInd, int colInd) {
        Course course = courses.get(rowInd);
        switch (colInd) {
            case 0:
                return course.getName();
            case 1:
                return course.getPrice();
            case 2:
                return course.getLessons().size();
            case 3:
                return course.getProfessor().getName() + " " + course.getProfessor().getSurname();
            case 4:
                return course.getProfessor().getInstrument();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return columns[i];
    }

    public List<Course> getCourses() {
        return courses;
    }

}
