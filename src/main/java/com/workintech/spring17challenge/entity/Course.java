package com.workintech.spring17challenge.entity;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class Course {

    private Integer id;
    private String name;
    private Integer credit;
    private Grade grade;




    @Override
    public String toString() {
        return getClass().getSimpleName() + "'s\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Credit: " + credit + "\n" +
                "Grade: " + grade;
    }
}
