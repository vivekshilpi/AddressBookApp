package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookServiceTest {

    AddressBookService service = new AddressBookService();

    @Test
    public void givenContact_whenSavedToDatabase_shouldRetrieveSuccessfully() {

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

        // insert contact into DB
        service.saveContactToDatabase(contact);

        // fetch contacts from DB
        List<Contact> contacts = service.getContactsFromDatabase();

        // verify DB not empty
        Assertions.assertTrue(contacts.size() > 0);
    }
}