package com.addressbookapp.service;

import com.addressbookapp.model.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressBookService {

    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void displayContacts() {

        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public Contact findContact(String firstName) {

        for (Contact contact : contacts) {
            if (contact.getFirstName().equalsIgnoreCase(firstName)) {
                return contact;
            }
        }

        return null;
    }

    public boolean deleteContact(String firstName) {

        Iterator<Contact> iterator = contacts.iterator();

        while (iterator.hasNext()) {

            Contact contact = iterator.next();

            if (contact.getFirstName().equalsIgnoreCase(firstName)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }
}