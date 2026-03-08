package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookService {

    private List<Contact> contactList = new ArrayList<>();

    public void addContact(Scanner scanner) {

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter State: ");
        String state = scanner.nextLine();

        System.out.print("Enter Zip: ");
        String zip = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, address,
                city, state, zip, phoneNumber, email);

        contactList.add(contact);

        System.out.println("\nContact added successfully!");
    }

    public void editContact(String name, Scanner scanner) {

        for (Contact contact : contactList) {

            if (contact.getFirstName().equalsIgnoreCase(name)) {

                System.out.print("Enter new Address: ");
                contact.setAddress(scanner.nextLine());

                System.out.print("Enter new City: ");
                contact.setCity(scanner.nextLine());

                System.out.print("Enter new State: ");
                contact.setState(scanner.nextLine());

                System.out.print("Enter new Zip: ");
                contact.setZip(scanner.nextLine());

                System.out.print("Enter new Phone: ");
                contact.setPhoneNumber(scanner.nextLine());

                System.out.print("Enter new Email: ");
                contact.setEmail(scanner.nextLine());

                System.out.println("\nContact updated successfully!");
                return;
            }
        }

        System.out.println("Contact not found.");
    }

    public void displayContacts() {

        if (contactList.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        for (Contact contact : contactList) {
            System.out.println(contact);
        }
    }
}