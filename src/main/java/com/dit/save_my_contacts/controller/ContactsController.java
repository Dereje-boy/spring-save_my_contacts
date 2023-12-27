package com.dit.save_my_contacts.controller;

import com.dit.save_my_contacts.model.ContactModel;
import com.dit.save_my_contacts.service.ContactService;
import com.sun.jdi.connect.spi.Connection;
import jakarta.servlet.http.WebConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("contacts/")
public class ContactsController {
    @Autowired
    ContactService contactService;

    @PostMapping("new")
    public ModelAndView addNewContact(ContactModel contact){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        System.out.println(contact);
        contactService.insertNewContact(contact);
        return modelAndView;
    }
    @GetMapping("/all")
    public ModelAndView getAllContacts(){

        //consider redirecting to homepage rather than render index page in contacts/all path

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/allcontacts")
    public List<ContactModel> allContacts(){

        return contactService.getAllContacts();
    }
}
