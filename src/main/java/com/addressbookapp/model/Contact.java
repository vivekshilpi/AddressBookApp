package com.addressbookapp.model;

import lombok.Data;

@Data
public class Contact {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    public Contact(String firstName, String lastName, String address,
                   String city, String state, String zip,
                   String phoneNumber, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nContact Details:\n" +
               "First Name : " + firstName + "\n" +
               "Last Name  : " + lastName + "\n" +
               "Address    : " + address + "\n" +
               "City       : " + city + "\n" +
               "State      : " + state + "\n" +
               "Zip        : " + zip + "\n" +
               "Phone      : " + phoneNumber + "\n" +
               "Email      : " + email;
    }
}