/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Course;
import domain.IDomainEntity;
import domain.Lesson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class UpdateCourse extends AbstractGenericOperation {

    @Override
    protected void validate(IDomainEntity domainEntity) throws Exception {
        if (domainEntity instanceof Course) {
            Course course = (Course) domainEntity;
        }
    }

    @Override
    protected void execute(IDomainEntity domainEntity) throws Exception {

        Course course = (Course) domainEntity;
        dbBroker.deleteByCond(new Lesson(course));
        entity = dbBroker.update(course);
        for (Lesson less : course.getLessons()) {
            System.out.println(less.getCourse());
            dbBroker.save(less);
//            AbstractGenericOperation insertLesson = new InsertLesson();
//            insertLesson.templateExecute(less);
        }
//        updateLessons(course);
    }

//     CourseEntity course = (CourseEntity) ide;
//        entity = db.save(ide);
//        for (CourseUnitEntity courseUnit : course.getCourseUnits()) {
//            db.saveSubitem(courseUnit);
//        }
//        for (LecturerEntity lecturer : course.getLecturers()) {
//            db.save(new RoleEntity(lecturer, course));
//        }
    private void updateLessons(Course course) throws Exception {

        for (Lesson less : course.getLessons()) {
            AbstractGenericOperation insertLesson = new InsertLesson();
            insertLesson.templateExecute(less);
        }

    }

    private void deleteLessons(Course course) throws Exception {
        for (Lesson less : course.getLessons()) {
            AbstractGenericOperation deleteLesson = new DeleteLesson();
            deleteLesson.templateExecute(less);
        }
    }

}
