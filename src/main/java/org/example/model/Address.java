package org.example.model;

import java.io.Serializable;

public class Address implements Serializable {
    private String zipCode; //Поле не может быть null


    public Address(String zipCode) {
        this.zipCode = zipCode;
    }

    public Address() {

    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                '}';
    }
}