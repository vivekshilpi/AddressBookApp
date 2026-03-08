package com.addressbookapp.service;

import com.addressbookapp.model.Contact;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
    
    public boolean editContact(String firstName, Scanner scanner) {

        Contact contact = findContact(firstName);

        if (contact == null) {
            return false;
        }

        System.out.print("New Address: ");
        contact.setAddress(scanner.nextLine());

        System.out.print("New City: ");
        contact.setCity(scanner.nextLine());

        System.out.print("New State: ");
        contact.setState(scanner.nextLine());

        System.out.print("New Zip: ");
        contact.setZip(scanner.nextLine());

        System.out.print("New Phone: ");
        contact.setPhoneNumber(scanner.nextLine());

        System.out.print("New Email: ");
        contact.setEmail(scanner.nextLine());

        return true;
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