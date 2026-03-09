package com.addressbookapp.repository;

import com.addressbookapp.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressBookRepository {

    // INSERT CONTACT INTO DATABASE
    public void insertContact(Contact contact) {

        String sql = "INSERT INTO contacts " +
                "(first_name, last_name, address, city, state, zip, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getAddress());
            stmt.setString(4, contact.getCity());
            stmt.setString(5, contact.getState());
            stmt.setString(6, contact.getZip());
            stmt.setString(7, contact.getPhoneNumber());
            stmt.setString(8, contact.getEmail());

            stmt.executeUpdate();

            System.out.println("Contact inserted into database successfully.");

        } catch (Exception e) {
            System.out.println("Error inserting contact: " + e.getMessage());
        }
    }

    // GET ALL CONTACTS FROM DATABASE
    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();

        String sql = "SELECT * FROM contacts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Contact contact = new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                contactList.add(contact);
            }

        } catch (Exception e) {
            System.out.println("Error retrieving contacts: " + e.getMessage());
        }

        return contactList;
    }
}