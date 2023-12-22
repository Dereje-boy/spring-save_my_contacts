package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.model.ContactModel;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDAO {

    Connection connection;

    public ContactDAO() {
        this.connection = new ConnectionDB().getConnection();
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

    public List<ContactModel> getAllContacts( ){
        List<ContactModel> allContacts = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from contacts");
            do{
                ContactModel contact = new ContactModel(
                        rs.getInt("ID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("countryName"),
                        rs.getInt("countryCode"),
                        rs.getInt("phoneNumber")
                );
                allContacts.add(contact);
            }while (rs.next());
            return allContacts;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return  new ArrayList<>();
        }
    }
    public enum insertedResult {
        mightBeInserted,
        notInserted
    }
}
