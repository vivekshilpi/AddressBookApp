package com.addressbookapp;

import java.util.Scanner;
import com.addressbookapp.service.AddressBookService;

public class AddressBookAppApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddressBookService service = new AddressBookService();

        int choice;

        do {

            System.out.println("\n===== Address Book Menu =====");
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Display Contacts");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    service.addContact(scanner);
                    break;

                case 2:
                    System.out.print("Enter first name to edit: ");
                    String name = scanner.nextLine();
                    service.editContact(name, scanner);
                    break;

                case 3:
                    service.displayContacts();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        scanner.close();
    }
}