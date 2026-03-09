package com.addressbookapp.rest;

import com.addressbookapp.model.Contact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressBookRestTest {

    @Test
    public void givenContactsInJsonServer_whenRetrieved_shouldMatchCount() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        Response response =
                given()
                .when()
                .get("/contacts");

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        List<Contact> contacts =
                response.jsonPath().getList("", Contact.class);

        assertTrue(contacts.size() > 0);

        contacts.forEach(System.out::println);
    }
}