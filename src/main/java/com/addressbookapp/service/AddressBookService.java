package com.addressbookapp.service;

import com.addressbookapp.model.Contact;

public class AddressBookService {

    private Contact contact;

    public void addContact(Contact contact) {
        this.contact = contact;
        System.out.println("\nContact added successfully!");
    }

    public void displayContact() {

        if (contact == null) {
            System.out.println("No contact available.");
            return;
        }

        System.out.println("\nContact Details:");
        System.out.println(contact);
    }
}