package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookServiceTest {

    AddressBookService service = new AddressBookService();

    @Test
    public void givenContact_whenUpdated_shouldSyncWithDB() {

        // Step 1: Insert a contact into DB
        Contact contact = new Contact(
                "Test",
                "User",
                "Test Address",
                "Bhopal",
                "MP",
                "462001",
                "9999999999",
                "test@gmail.com"
        );

        service.saveContactToDatabase(contact);

        // Step 2: Update contact city
        service.updateContactCity("Test", "Indore");

        // Step 3: Retrieve contact from DB
        Contact dbContact = service.getContactFromDB("Test");

        // Step 4: Expected contact after update
        Contact expected = new Contact(
                "Test",
                "User",
                "Test Address",
                "Indore",
                "MP",
                "462001",
                "9999999999",
                "test@gmail.com"
        );

        // Step 5: Verify DB data == expected data
        Assertions.assertEquals(expected, dbContact);
    }

    @Test
    public void givenDatabase_whenContactsRetrieved_shouldReturnList() {

        List<Contact> contacts = service.getContactsFromDatabase();

        Assertions.assertNotNull(contacts);
    }
    
    @Test
    public void givenDateRange_whenContactsRetrieved_shouldReturnList(){

        AddressBookService service = new AddressBookService();

        List<Contact> contacts =
                service.getContactsByDateRange("2024-01-01","2030-01-01");

        Assertions.assertNotNull(contacts);
    }
}