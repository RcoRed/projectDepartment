package org.generation.italy.projectDepartment.model.entities;

import java.time.LocalDate;

public class Employee {
    private long id;
    private String name;
    private String surname;
    private LocalDate hireDate;
    private Sex sex;
    private Department department;

    public Employee(long id, String name, String surname, LocalDate hireDate, Sex sex, Department department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.hireDate = hireDate;
        this.sex = sex;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Sex getSex() {
        return sex;
    }

    public Department getDepartment() {
        return department;
    }
}
