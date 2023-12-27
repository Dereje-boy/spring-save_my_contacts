package com.dit.save_my_contacts.controller;

import com.dit.save_my_contacts.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


@Controller
@RequestMapping("login")
public class LoginController {

    // ../login
    @GetMapping("")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userModel", new UserModel());
        modelAndView.addObject("message",
                "Please type your email and password in the boxes below and then click submit");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    //../login/
    @GetMapping("/")
    public ModelAndView loginSLASH(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    //../login post method
    @PostMapping("")
    public ModelAndView postLogin(UserModel user){
        System.out.println(user.toString());
        ModelAndView modelAndView = new ModelAndView();
        String message = "";
        String email = user.getEmail(), password = user.getPassword();

        if (email == null || password == null) message = "please insert your email and password!";
        else{
            if (password.length()<6) message = "password is not correct";
            if (email.length()<5) message = "please enter valid email address";
        }

        modelAndView.addObject("message",message);
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
