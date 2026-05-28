package com.addressbookapp.rest;

import com.addressbookapp.model.Contact;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    
    // ---- UC 23 ----
    
    @Test
    public void givenNewContact_whenAdded_shouldReturn201() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        Contact contact = new Contact(
                "Rahul",
                "Verma",
                "Indore Address",
                "Indore",
                "MP",
                "452001",
                "7777777777",
                "rahul@test.com"
        );

        Response response =
                given()
                .contentType(ContentType.JSON)
                .body(contact)
                .when()
                .post("/contacts");

        int statusCode = response.getStatusCode();

        assertEquals(201, statusCode);

        System.out.println(response.getBody().asString());
    }
    
    // ---- UC 24 -----
    @Test
    public void givenContact_whenUpdated_shouldReturn200() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        // contact id to update
        int contactId = 1;

        Contact updatedContact = new Contact(
                "Akash",
                "Sharma",
                "Updated Address",
                "Bhopal",
                "MP",
                "462001",
                "9999999999",
                "akash@test.com"
        );

        Response response =
                given()
                .contentType(ContentType.JSON)
                .body(updatedContact)
                .when()
                .put("/contacts/" + contactId);

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        System.out.println(response.getBody().asString());
    }
    
    // ----- UC 25 -----
    
    @Test
    public void givenContact_whenDeleted_shouldReturn200() {

        int contactId = 1;

        Response response =
                given()
                .when()
                .delete("/contacts/" + contactId);

        int statusCode = response.getStatusCode();

        assertEquals(200, statusCode);

        System.out.println("Contact deleted successfully");
    }
}