package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.model.ContactModel;
import org.springframework.stereotype.Service;

import javax.swing.tree.RowMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDAO {

    Connection connection;
    public static final String SQL_CREATE_TABLE = "CREATE TABLE contacts (ID integer primary key autoincrement not null,\n" +
            "firstname varchar(255) not null,lastname varchar(255),countryCode int,\n" +
            "countryName varchar(255),phoneNumber int);";

    boolean tableAlreadyExist = false;
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
            statement.execute(SQL_CREATE_TABLE);


        } catch (SQLException exception) {
            System.out.println(this.getClass().getName() + ": Table creation error ");
            System.out.println(exception.getMessage());
            tableAlreadyExist = true;
        }

        if (tableAlreadyExist) return;
        else {
            if (statement != null){
                try {
                    //after table creation let's insert dummy 2 contacts

                    //contact1 ethiopian police
                    ContactModel contactModel = new ContactModel(0, "Ethiopian", "Police", "Eth", 251, "911");
                    //sql code to insert first contact=ethiopian police
                    String insertCommand = String.format("insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                                    "values ('%s','%s','%s','%s','%s')", contactModel.getFirstname(), contactModel.getLastname(),
                            contactModel.getCountryCode(), contactModel.getCountryName(), contactModel.getPhoneNumber());

                    //inserting the first contact
                    statement.execute(insertCommand);

                    //creating the second contact = ethiopia electric utilities
                    ContactModel contactModel2 = new ContactModel(0, "Ethiopian", "Electric Utility", "Eth", 251, "904");
                    //sql code to insert the second contact
                    String insertCommand2 = String.format("insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                                    "values ('%s','%s','%s','%s','%s')", contactModel2.getFirstname(), contactModel2.getLastname(),
                            contactModel2.getCountryCode(), contactModel2.getCountryName(), contactModel2.getPhoneNumber());
                    //inserting the second contact
                    statement.execute(insertCommand2);
                }catch (SQLException exception){
                    System.out.println(this.getClass().getName()+": Unable to insert dummy data");
                    System.out.println(exception.getMessage());
                }
            }
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
            while (rs.next()){
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
