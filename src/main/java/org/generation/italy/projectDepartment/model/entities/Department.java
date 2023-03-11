package org.generation.italy.projectDepartment.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Department {
    private long id;
    private String name;
    private Address address;
    private int maxCapacity;
    private Set<Employee> employeeSet;

    public Department(long id, String name, int maxCapacity) {
        this(id,name,null,maxCapacity);
    }
    public Department(long id, String name, Address address, int maxCapacity) {
        this(id,name,address,maxCapacity,new HashSet<>());
    }
    public Department(long id, String name, Address address, int maxCapacity, HashSet<Employee> employeeSet) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.employeeSet = employeeSet;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }
}
