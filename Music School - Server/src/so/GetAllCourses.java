/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Course;
import domain.IDomainEntity;
import domain.Lesson;
import domain.Professor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class GetAllCourses extends AbstractGenericOperation {

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if (domainEntity instanceof Course) {
            Course course = (Course) domainEntity;
        } else {
            throw new Exception("Not a course!");
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {
        list = dbBroker.findAll(domainEntity);

        for (IDomainEntity iDomainEntity : list) {
            Course course = (Course) iDomainEntity;
            setProfessor(course);
            setLessons(course);
        }
    }

    private void setProfessor(Course course) {
        Professor professorOld = course.getProfessor();
        IDomainEntity professorNew = dbBroker.findById(professorOld);
        Professor professorFinal = (Professor) professorNew;
        course.setProfessor(professorFinal);
    }

    private void setLessons(Course course) {
        try {
            for (IDomainEntity less : dbBroker.findByCond(new Lesson(course))) {
                Lesson lesson = (Lesson) less;
                course.getLessons().add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetAllCourses.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
