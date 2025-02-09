package com.workintech.spring17challenge.validations;

import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Iterator;
import java.util.List;

public class CourseValidation {


    public static void checkCourseCredit(Integer credit) {

        if ( credit==null || credit < 0 || credit > 4) {
            throw new ApiException("Invalid course credit! Credit can not be null/empty or out of 0-4 interval !", HttpStatus.BAD_REQUEST);
        }

    }


    public static void checkCourseName(String courseName) {
//

        if ( courseName == null || courseName.isEmpty()) {
            throw new ApiException("Course name can not be null or empty!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCourseIfAlreadyExist(Integer id , List<Course> courses) {
        for(Course course: courses) {
//            if(course.getName().contains(courseName)) {
//                throw new ApiException("'" + courseName + "' is already exist!", HttpStatus.CONFLICT);
//            }

            if(course.getId().equals(id)) {
                throw new ApiException("ID '" + id + "' is already belongs to another course!", HttpStatus.CONFLICT);
            }
        }
    }


    public static void checkCourseId(Integer id) {
        if (id==null || id < 0 ) {
            throw new ApiException("Course ID can not be null/empty or lower than 0!", HttpStatus.BAD_REQUEST);

        }
    }


}
