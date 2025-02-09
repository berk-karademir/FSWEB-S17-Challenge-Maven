package com.workintech.spring17challenge.entity;


import lombok.*;


public class JavaScriptCourse extends Course{


    public JavaScriptCourse() {
        super(99,"JS Course", 2, new Grade(3,"C"));
    }


}
