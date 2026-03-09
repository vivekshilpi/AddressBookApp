package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import com.addressbookapp.repository.AddressBookRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressBookService {

    private List<Contact> contactList = new ArrayList<>();

    private static final String FILE_PATH = "src/main/resources/data/AddressBook.txt";
    private static final String CSV_FILE = "src/main/resources/data/AddressBook.csv";
    private static final String JSON_FILE = "src/main/resources/data/AddressBook.json";

    // UC16 – Repository Layer
    private AddressBookRepository repository = new AddressBookRepository();

    public boolean addContact(Contact contact){

        boolean duplicate = contactList
                .stream()
                .anyMatch(c -> c.equals(contact));

        if(duplicate){
            return false;
        }

        contactList.add(contact);
        return true;
    }

    public void displayContacts() {

        if(contactList.isEmpty()){
            System.out.println("No contacts available");
            return;
        }

        for(Contact contact : contactList){
            System.out.println(contact);
        }
    }

    // -------- SORTING --------

    public void sortContactsByName(){

        if(contactList.isEmpty()){
            System.out.println("No contacts available to sort.");
            return;
        }

        contactList.stream()
                .sorted((c1, c2) -> c1.getFirstName()
                .compareToIgnoreCase(c2.getFirstName()))
                .forEach(System.out::println);
    }

    public void sortContactsByCity(){

        if(contactList.isEmpty()){
            System.out.println("No contacts available.");
            return;
        }

        contactList.stream()
                .sorted(java.util.Comparator.comparing(Contact::getCity))
                .forEach(System.out::println);
    }

    public void sortContactsByState(){

        if(contactList.isEmpty()){
            System.out.println("No contacts available.");
            return;
        }

        contactList.stream()
                .sorted(java.util.Comparator.comparing(Contact::getState))
                .forEach(System.out::println);
    }

    public void sortContactsByZip(){

        if(contactList.isEmpty()){
            System.out.println("No contacts available.");
            return;
        }

        contactList.stream()
                .sorted(java.util.Comparator.comparing(Contact::getZip))
                .forEach(System.out::println);
    }

    // -------- FIND --------

    public Contact findContact(String firstName) {

        for (Contact contact : contactList) {
            if (contact.getFirstName().equalsIgnoreCase(firstName)) {
                return contact;
            }
        }

        return null;
    }

    // -------- DELETE --------

    public boolean deleteContact(String firstName) {

        Iterator<Contact> iterator = contactList.iterator();

        while (iterator.hasNext()) {

            Contact contact = iterator.next();

            if (contact.getFirstName().equalsIgnoreCase(firstName)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public List<Contact> getContacts(){
        return contactList;
    }

    // -------- FILE IO --------

    public void writeContactsToFile(){

        try{

            List<String> contactData = new ArrayList<>();

            for(Contact contact : contactList){
                contactData.add(contact.toString());
            }

            java.nio.file.Files.write(
                    java.nio.file.Paths.get(FILE_PATH),
                    contactData
            );

            System.out.println("Contacts written to file successfully.");

        }catch(Exception e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readContactsFromFile(){

        try{

            List<String> lines = java.nio.file.Files.readAllLines(
                    java.nio.file.Paths.get(FILE_PATH)
            );

            System.out.println("\nContacts from file:");

            lines.forEach(System.out::println);

        }catch(Exception e){
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // -------- CSV --------

    public void writeContactsToCSV() {

        try {

            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(CSV_FILE));

            writer.println("firstName,lastName,address,city,state,zip,phoneNumber,email");

            for (Contact contact : contactList) {

                writer.println(
                        contact.getFirstName() + "," +
                        contact.getLastName() + "," +
                        contact.getAddress() + "," +
                        contact.getCity() + "," +
                        contact.getState() + "," +
                        contact.getZip() + "," +
                        contact.getPhoneNumber() + "," +
                        contact.getEmail()
                );
            }

            writer.close();

            System.out.println("Contacts written to CSV file successfully.");

        } catch (Exception e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }
    }

    public void readContactsFromCSV() {

        try {

            java.nio.file.Path path = java.nio.file.Paths.get(CSV_FILE);

            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);

            System.out.println("\nContacts from CSV file:");

            for (int i = 1; i < lines.size(); i++) {
                System.out.println(lines.get(i));
            }

        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // -------- JSON --------

    public void writeContactsToJSON() {

        try {

            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();

            mapper.writeValue(
                    new java.io.File(JSON_FILE),
                    contactList
            );

            System.out.println("Contacts written to JSON file successfully.");

        } catch (Exception e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }

    public void readContactsFromJSON() {

        try {

            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();

            java.util.List<Contact> contacts =
                    mapper.readValue(
                            new java.io.File(JSON_FILE),
                            new com.fasterxml.jackson.core.type.TypeReference<List<Contact>>() {}
                    );

            System.out.println("\nContacts from JSON file:");

            contacts.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // -------- UC16 DATABASE METHODS --------

    public List<Contact> getContactsFromDatabase(){

        return repository.getAllContacts();
    }

    public void saveContactToDatabase(Contact contact){

        repository.insertContact(contact);
    }
    
    // ------ UC 17 -----
    
    public void updateContactCity(String name, String newCity){

        repository.updateContactCity(name,newCity);
    }
    public Contact getContactFromDB(String name){

        return repository.getContactByName(name);
    }
    
    // ------ UC 18 -----
    public List<Contact> getContactsByDateRange(String startDate,String endDate){

        return repository.getContactsByDateRange(startDate,endDate);
    }
}