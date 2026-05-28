package com.addressbookapp.repository;

import com.addressbookapp.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

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
    
    // ------ UC 19 -----
    
    public Map<String,Integer> getContactCountByCity(){

        Map<String,Integer> result = new HashMap<>();

        String sql = "SELECT city, COUNT(*) AS total FROM contacts GROUP BY city";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){

                result.put(
                        rs.getString("city"),
                        rs.getInt("total")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    
    public Map<String,Integer> getContactCountByState(){

        Map<String,Integer> result = new HashMap<>();

        String sql = "SELECT state, COUNT(*) AS total FROM contacts GROUP BY state";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){

                result.put(
                        rs.getString("state"),
                        rs.getInt("total")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    
    // ----- UC 20 - transaction -----
    
    public void addContactWithTransaction(Contact contact){

        String insertPerson =
                "INSERT INTO person(first_name,last_name) VALUES (?,?)";

        String insertAddress =
                "INSERT INTO address(person_id,address,city,state,zip) VALUES (?,?,?,?,?)";

        String insertContact =
                "INSERT INTO contact_details(person_id,phone,email,date_added) VALUES (?,?,?,CURRENT_DATE)";

        Connection conn = null;

        try{
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert person
            PreparedStatement personStmt =
                    conn.prepareStatement(insertPerson, Statement.RETURN_GENERATED_KEYS);

            personStmt.setString(1,contact.getFirstName());
            personStmt.setString(2,contact.getLastName());

            personStmt.executeUpdate();

            ResultSet keys = personStmt.getGeneratedKeys();
            keys.next();

            int personId = keys.getInt(1);

            // Insert address
            PreparedStatement addressStmt = conn.prepareStatement(insertAddress);

            addressStmt.setInt(1,personId);
            addressStmt.setString(2,contact.getAddress());
            addressStmt.setString(3,contact.getCity());
            addressStmt.setString(4,contact.getState());
            addressStmt.setString(5,contact.getZip());

            addressStmt.executeUpdate();

            // Insert contact details
            PreparedStatement contactStmt = conn.prepareStatement(insertContact);

            contactStmt.setInt(1,personId);
            contactStmt.setString(2,contact.getPhoneNumber());
            contactStmt.setString(3,contact.getEmail());

            contactStmt.executeUpdate();

            conn.commit();

            System.out.println("Contact inserted successfully with transaction");

        }catch(Exception e){

            try{
                if(conn != null){
                    conn.rollback();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }
    
}