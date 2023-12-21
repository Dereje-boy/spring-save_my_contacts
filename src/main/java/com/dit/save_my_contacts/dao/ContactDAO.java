package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.model.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void deleteContact(int contactID) {
        final String deleteSQLCommand = "delete from contacts where ID = ?";
    }

    public insertedResult insertNewContact(ContactModel contactModel) {
        contactModel.setCountryCode(Integer.parseInt(contactModel.getCountryName().split(" ")[0]));
        final String insertCommand = String.format("insert into contacts (firstname,lastname,countryCode,countryName,phoneNumber) " +
                        "values ('%s','%s','%s','%s','%s')", contactModel.getFirstname(), contactModel.getLastname(),
                contactModel.getCountryCode(), contactModel.getCountryName(), contactModel.getPhoneNumber());
        try {
            jdbcTemplate.execute(insertCommand);
            return insertedResult.mightBeInserted;
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getCause().getMessage());
            return insertedResult.notInserted;
        }
    }

    public List<ContactModel> getAllContacts(){
        List<ContactModel> contacts = new ArrayList<>();
        try {
            contacts = jdbcTemplate.query("select * from contacts", (rs, rowNum) ->
                            new ContactModel(
                                    rs.getInt("ID"),
                                    rs.getString("firstname"),
                                    rs.getString("lastname"),
                                    rs.getString("countryName"),
                                    rs.getInt("countryCode"),
                                    rs.getInt("phoneNumber")
                                    )
                    );
            return contacts;
        }catch (DataAccessException dataAccessException){
            System.out.println("unable to get connection \n" + dataAccessException.getMessage());
            return new ArrayList<>();
        }
    }
    public enum insertedResult {
        mightBeInserted,
        notInserted
    }
}
