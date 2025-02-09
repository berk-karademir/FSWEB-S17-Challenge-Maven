package com.workintech.spring17challenge.controller;


import com.workintech.spring17challenge.dto.ApiResponse;
import com.workintech.spring17challenge.entity.*;
import com.workintech.spring17challenge.exceptions.ApiErrorResponse;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.validations.CourseValidation;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> courses;
    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") LowCourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") MediumCourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void initiation() {
        System.out.println(" ☢ ☢ ☢ ✅ CourseController's @PostConstruct initiated! ☢ ☢ ☢");
        courses = new ArrayList<>();
    }


    private Integer getTotalGpa(Course course) {
        Integer totalGpa = null;
        if (course.getCredit() <= 2) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        } else {
            totalGpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
        return totalGpa;
    }


    //mappings :


    @GetMapping
    public List<Course> getAllCourses() {
        return courses;
    }


    @GetMapping("/{name}")
    public Course getCourseByName(@PathVariable("name") String name) {
        // Gelen isimdeki boşlukları kaldır
        String normalizedInputName = name.replaceAll("\\s", "").toLowerCase();

        // CourseValidation.checkCourseName(name, courses); // Bu satırı güncellemeyi unutmayın

        for (Course course : courses) {
            // Course ismindeki boşlukları kaldır
            String normalizedCourseName = course.getName().replaceAll("\\s", "").toLowerCase();

            if (normalizedCourseName.equals(normalizedInputName)) {
                return course;
            } else {

            }
        }
        throw new ApiException("Cannot find a course with this name: " + name, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Course course) {
        CourseValidation.checkCourseCredit(course.getCredit());
        CourseValidation.checkCourseName(course.getName());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(course, totalGpa);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    private Course getExistingCourseById(Integer id) {
        for (Course anycourse : courses) {
            if (anycourse.getId().equals(id)) {
                return anycourse;
            }
        }
        throw new ApiException("Course not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        CourseValidation.checkCourseId(id);
        CourseValidation.checkCourseCredit(course.getCredit());
        course.setId(id);
        Course existingCourse = getExistingCourseById(id);
        int indexOfExisting = courses.indexOf(existingCourse);
        courses.set(indexOfExisting, course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(courses.get(indexOfExisting), totalGpa);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteACourseById(@PathVariable("id") Integer id) {
        Course existingCourse = getExistingCourseById(id);
        courses.remove(existingCourse);
    }

}
