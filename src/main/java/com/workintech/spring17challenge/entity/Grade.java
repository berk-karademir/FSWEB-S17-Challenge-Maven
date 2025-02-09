package com.workintech.spring17challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grade {

    private int coefficient;
    private String note;

    @Override
    public String toString() {
        return
                "Coefficient: " + coefficient +
                ", Note: " + note;

    }
}
