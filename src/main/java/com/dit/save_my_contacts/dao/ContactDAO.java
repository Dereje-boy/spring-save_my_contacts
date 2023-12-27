package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.model.ContactModel;
import com.dit.save_my_contacts.model.UserModel;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDAO {

    Connection connection;
    public static final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE contacts (ID integer primary key autoincrement not null,\n" +
            "firstname varchar(255) not null,lastname varchar(255),countryCode int,\n" +
            "countryName varchar(255),phoneNumber int);";

    public static final String SQL_CREATE_USER_TABLE = "Create table user(userID integer primary key autoincrement, " +
            "firstname varchar(255) not null, lastname varchar(255), username varchar(255) not null, password varchar(255) not null, " +
            "avatar varchar(255));";

    boolean contactsTableAlreadyExist = false;
    boolean usersTableAlreadyExist = false;
    Statement statement;

    public ContactDAO() {
        try {
            this.connection = new ConnectionDB().getConnection();
        } catch (Exception exception) {
            System.out.println(this.getClass().getName() + ": Unable to get connection object from ConnectionDB class");
            System.out.println(exception.getMessage());
            return;
        }

        try {
            statement = this.connection.createStatement();
        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": Unable to create statement from the connection" +
                    " This might deny creation of tables, Tables may not be created!");
            System.out.println(exception.getMessage());
            return;
        }

        try {
            //getting statement instance first
            //then creating contacts table
            //then after closing statement to connection
            statement.execute(SQL_CREATE_CONTACTS_TABLE);
        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": Contacts Table creation error ");
            System.out.println(exception.getMessage());
            contactsTableAlreadyExist = true;
        }
        try {
            //getting statement instance first
            //then creating user table
            //then after closing statement to connection
            statement.execute(SQL_CREATE_USER_TABLE);
        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": User Table creation error ");
            System.out.println(exception.getMessage());
            usersTableAlreadyExist = true;
        }

        if (!contactsTableAlreadyExist) insertContactsDemoData();
        if (!usersTableAlreadyExist) insertUsersDemoData();

    }

    private void insertUsersDemoData() {
        try {
            //after table creation let's insert admin data

            //admin info
            UserModel admin = new UserModel(
                    100,
                    "Admin",
                    "Admin",
                    "Admin@smc.com.et",
                    "A!d@m#i$n%",
                    "",
                    null);

            //sql code to insert first contact=ethiopian police
            String insertCommand = String.format(
                    "insert into user (userID,firstname,lastname,username,password,avatar) " +
                            "values ('%s','%s','%s','%s','%s','%s')",
                    admin.getUserID(),
                    admin.getFirstname(),
                    admin.getLastname(),
                    admin.getEmail(),
                    admin.getPassword(),
                    admin.getAvatar());

            //inserting the first contact
            statement.execute(insertCommand);

        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": Unable to insert admin data");
            System.out.println(exception.getMessage());
        }
    }

    private void insertContactsDemoData() {
        try {
            //after table creation let's insert dummy 2 contacts

            //contact1 ethiopian police
            ContactModel contactModel = new ContactModel(
                    0,
                    "Ethiopian",
                    "Police",
                    "Eth",
                    251,
                    "911"
            );

            //sql code to insert first contact=ethiopian police
            String insertCommand = String.format(
                    "insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                            "values ('%s','%s','%s','%s','%s')",
                    contactModel.getFirstname(),
                    contactModel.getLastname(),
                    contactModel.getCountryCode(),
                    contactModel.getCountryName(),
                    contactModel.getPhoneNumber());

            //inserting the first contact
            statement.execute(insertCommand);

            //creating the second contact = ethiopia electric utilities
            ContactModel contactModel2 = new ContactModel(
                    0,
                    "Ethiopian",
                    "Electric Utility",
                    "Eth",
                    251,
                    "904");
            //sql code to insert the second contact
            String insertCommand2 = String.format(
                    "insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                            "values ('%s','%s','%s','%s','%s')",
                    contactModel2.getFirstname(),
                    contactModel2.getLastname(),
                    contactModel2.getCountryCode(),
                    contactModel2.getCountryName(),
                    contactModel2.getPhoneNumber());
            //inserting the second contact
            statement.execute(insertCommand2);
        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": Unable to insert dummy data");
            System.out.println(exception.getMessage());
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Unable to close statement");
            System.out.println(e.getMessage());
        }
    }

    public void deleteContact(int contactID) {
        final String deleteSQLCommand = "delete from contacts where ID = ?";
    }

    public insertedResult insertNewContact(ContactModel contactModel) {
        if (connection == null) return insertedResult.notInserted;
        contactModel.setCountryCode(Integer.parseInt(contactModel.getCountryName().split(" ")[0]));
        final String insertCommand = String.format("insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                        "values ('%s','%s','%s','%s','%s')", contactModel.getFirstname(), contactModel.getLastname(),
                contactModel.getCountryCode(), contactModel.getCountryName(), contactModel.getPhoneNumber());
        try {
            //jdbcTemplate.execute(insertCommand);
            Statement statement = connection.createStatement();
            statement.execute(insertCommand);
            return insertedResult.mightBeInserted;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return insertedResult.notInserted;
        }
    }

//    public List<ContactModel> getAllContacts(){
//        List<ContactModel> contacts = new ArrayList<>();
//        try {
//            contacts = jdbcTemplate.query("select * from contacts", (rs, rowNum) ->
//                            new ContactModel(
//                                    rs.getInt("ID"),
//                                    rs.getString("firstname"),
//                                    rs.getString("lastname"),
//                                    rs.getString("countryName"),
//                                    rs.getInt("countryCode"),
//                                    rs.getInt("phoneNumber")
//                                    )
//                    );
//            return contacts;
//        }catch (DataAccessException dataAccessException){
//            System.out.println("unable to get connection \n" + dataAccessException.getMessage());
//            return new ArrayList<>();
//        }
//    }

    public List<ContactModel> getAllContacts() {
        List<ContactModel> allContacts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from contacts");
            while (rs.next()) {
                ContactModel contact = new ContactModel(
                        rs.getInt("ID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("countryName"),
                        rs.getInt("countryCode"),
                        rs.getString("phoneNumber")
                );
                allContacts.add(contact);
            }
//            do {
//                if (!rs.isClosed()) {
//                }
//                ContactModel contact = new ContactModel(
//                        rs.getInt("ID"),
//                        rs.getString("firstname"),
//                        rs.getString("lastname"),
//                        rs.getString("countryName"),
//                        rs.getInt("countryCode"),
//                        rs.getString("phoneNumber")
//                );
//                allContacts.add(contact);
//                System.out.println(contact);
//            } while (rs.next());
            System.out.println("The size of contacts is :" + allContacts.size());
            return allContacts;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return new ArrayList<>();
        }
    }

    public enum insertedResult {
        mightBeInserted,
        notInserted
    }
}
