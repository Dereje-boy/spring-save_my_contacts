package com.dit.save_my_contacts.controller;

import com.dit.save_my_contacts.service.ContactService;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.dit.save_my_contacts.model.ContactModel;

@RestController
@Service
public class indexController {
    @Autowired
    ContactService contactService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("contactModel", new ContactModel());
        modelAndView.addObject("allContacts",contactService.getAllContacts());
        //for (ContactModel c : contactService.getAllContacts()) System.out.println(c);
        return modelAndView;
    }
    @GetMapping("/error")
    public String errorHandlerEndPoint() {
        System.out.println("wrong page request");
        return "errorPage";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ApplicationReadyEvent(){
        System.out.println("Event application context raised");
    }
}
