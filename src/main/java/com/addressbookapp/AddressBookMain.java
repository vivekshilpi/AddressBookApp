package com.addressbookapp;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBookService;

import java.util.Scanner;

public class AddressBookMain {

    public static void start() {

        Scanner scanner = new Scanner(System.in);
        AddressBookService service = new AddressBookService();

        while (true) {

            System.out.println("\n----- Address Book Menu -----");
            System.out.println("1 Add Contact");
            System.out.println("2 Display Contacts");
            System.out.println("3 Edit Contact");
            System.out.println("4 Delete Contact");
            System.out.println("5 Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    char option;

                    do {

                        System.out.print("First Name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Last Name: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Address: ");
                        String address = scanner.nextLine();

                        System.out.print("City: ");
                        String city = scanner.nextLine();

                        System.out.print("State: ");
                        String state = scanner.nextLine();

                        System.out.print("Zip: ");
                        String zip = scanner.nextLine();

                        System.out.print("Phone Number: ");
                        String phone = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        Contact contact = new Contact(
                                firstName,
                                lastName,
                                address,
                                city,
                                state,
                                zip,
                                phone,
                                email
                        );

                        service.addContact(contact);

                        System.out.println("Contact Added Successfully!");

                        System.out.print("Add another contact? (y/n): ");
                        option = scanner.next().charAt(0);
                        scanner.nextLine();

                    } while (option == 'y' || option == 'Y');

                    break;

                case 2:
                    service.displayContacts();
                    break;

                case 3:

                    System.out.print("Enter First Name to edit: ");
                    String editName = scanner.nextLine();

                    if(service.editContact(editName, scanner))
                        System.out.println("Contact Updated Successfully!");
                    else
                        System.out.println("Contact not found!");

                    break;

                case 4:
                    System.out.print("Enter First Name to delete: ");
                    String deleteName = scanner.nextLine();

                    if (service.deleteContact(deleteName))
                        System.out.println("Contact Deleted Successfully!");
                    else
                        System.out.println("Contact not found!");

                    break;

                case 5:
                    System.out.println("Exiting Address Book...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}