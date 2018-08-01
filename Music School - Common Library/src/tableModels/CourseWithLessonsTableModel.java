/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import domain.Course;
import domain.Lesson;
import domain.LessonType;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ivan
 */
public class CourseWithLessonsTableModel extends AbstractTableModel {

    private final Course course;
    private String[] columns = {"Lesson number", "Name", "Duration", "Tablature", "Type"};

    public CourseWithLessonsTableModel(Course course) {
        this.course = course;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4);
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        Lesson lesson = course.getLessons().get(rowIndex);
        switch (columnIndex) {
            case 1:
                lesson.setName(o.toString());
                break;
            case 2:
                try {
                    int lessonDuration = Integer.parseInt(o.toString());
                    lesson.setDuration(lessonDuration);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Insert valid number of minutes!");
                }

                break;
            case 3:
                lesson.setTablature(o.toString());
                break;
            case 4:
                lesson.setType((LessonType) o);
                break;
        }
    }

    @Override
    public int getRowCount() {
        return course.getLessons().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

//    return billItem.getProductEntity() != null ? billItem.getProductEntity().getName() : "-"
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Lesson lesson = course.getLessons().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return lesson.getLessonNumber();
            case 1:
                return lesson.getName() != null ? lesson.getName() : "-";
            case 2:
                return lesson.getDuration();
            case 3:
                return lesson.getTablature() != null ? lesson.getTablature() : "-";
            case 4:
                return lesson.getType() != null ? lesson.getType() : "-";
            default:
                return "N/A";
        }
    }

    public void addNewRow() {
        List<Lesson> lessons = course.getLessons();
        if (lessons.isEmpty() || (lessons.get(lessons.size() - 1).getName() != null && lessons.get(lessons.size() - 1).getDuration() > 0)) {
            Lesson lesson = new Lesson();
            lesson.setLessonNumber(lessons.size() + 1);
            lesson.setCourse(course);
            lessons.add(lesson);
            updateCourseDuration();

            fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(null, "Please insert all values in order to insert new lesson!");
        }
    }

    public void removeSelectedRow(int lessonNumber) {
        course.getLessons().remove(lessonNumber);
        updateLessonNumbers();
        updateCourseDuration();
        fireTableDataChanged();

    }

    private void updateLessonNumbers() {
        List<Lesson> lessons = course.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            lessons.get(i).setLessonNumber(i + 1);
        }
    }

    private void updateCourseDuration() {
        List<Lesson> courseLessons = course.getLessons();
        int currentDuration = 0;

        for (Lesson courseLesson : courseLessons) {
            currentDuration += courseLesson.getDuration();
        }

        course.setDuration(currentDuration);
    }

    public int getCurrentDuration() {
        return course.getDuration();
    }

    public Course getCourse() {
        return course;
    }

    public List<Lesson> getCourseLessons() {
        return course.getLessons();
    }

//     private void updateBillAmount() {
//        List<BillItemlEntity> billItems = bill.getItems();
//        double currentAmount = 0.0;
//        
//        for (BillItemlEntity billItem : billItems) {
//            if (billItem.getProductEntity() != null) {
//                currentAmount += 
//                    billItem.getProductEntity().getPrice() * billItem.getQuantity();
//            }
//        }
//        
//        bill.setAmount(currentAmount);
//    }
}
