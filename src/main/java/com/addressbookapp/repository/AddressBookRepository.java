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
	            "(first_name,last_name,address,city,state,zip,phone,email,date_added) " +
	            "VALUES (?,?,?,?,?,?,?,?,CURRENT_DATE)";

	    try(Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, contact.getFirstName());
	        stmt.setString(2, contact.getLastName());
	        stmt.setString(3, contact.getAddress());
	        stmt.setString(4, contact.getCity());
	        stmt.setString(5, contact.getState());
	        stmt.setString(6, contact.getZip());
	        stmt.setString(7, contact.getPhoneNumber());
	        stmt.setString(8, contact.getEmail());

	        stmt.executeUpdate();

	    } catch(Exception e){
	        e.printStackTrace();
	    }
	}
    
    // UC 17- update
    public void updateContactCity(String firstName, String newCity) {

        String sql = "UPDATE contacts SET city=? WHERE first_name=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCity);
            stmt.setString(2, firstName);

            stmt.executeUpdate();

            System.out.println("Contact updated successfully");

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Contact getContactByName(String firstName) {

        String sql = "SELECT * FROM contacts WHERE first_name=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){

                return new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
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
    
    // ------ UC 18 ------
    public List<Contact> getContactsByDateRange(String startDate, String endDate) {

        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,startDate);
            stmt.setString(2,endDate);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

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

                contacts.add(contact);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return contacts;
    }
}