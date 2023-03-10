package org.generation.italy.projectDepartment.model.entities;

public class Address {
    private long id;
    private String street;
    private int houseNumber;
    private String city;
    private String country;

    public Address(long id, String street, int houseNumber, String city, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
