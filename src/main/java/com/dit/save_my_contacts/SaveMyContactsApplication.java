package com.dit.save_my_contacts;

import com.dit.save_my_contacts.dao.ContactDAO;
import com.dit.save_my_contacts.model.ContactModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaveMyContactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveMyContactsApplication.class, args);
        System.out.println("The web app started......");

    }
}
