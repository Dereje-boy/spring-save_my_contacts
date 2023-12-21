package com.dit.save_my_contacts.service;

import com.dit.save_my_contacts.dao.ContactDAO;
import com.dit.save_my_contacts.model.ContactModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactDAO ContactDAO;

    public String insertNewContact(ContactModel contactModel) {
        ContactDAO.insertedResult insertedResult = ContactDAO.insertNewContact(contactModel);
        String toBeReturned = "";
        switch (insertedResult){
            case mightBeInserted : {
                toBeReturned = "your contact is inserted";
            }
            break;
            case notInserted: {
                toBeReturned =  "your contact isn't inserted, please try again later";
            }
            break;
            default: {
                toBeReturned =  "Unable to identify the result, check your contacts list. Thank you!";
            }
        }
        System.out.println(toBeReturned);
        return toBeReturned;
    }

    public List<ContactModel> getAllContacts(){
        return ContactDAO.getAllContacts();
    }


}
