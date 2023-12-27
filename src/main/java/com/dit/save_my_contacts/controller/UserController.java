package com.dit.save_my_contacts.controller;

import com.dit.save_my_contacts.model.UserModel;
import com.dit.save_my_contacts.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("newaccount")
public class UserController {
    @GetMapping("")
    public ModelAndView newAccount() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newAccount");
        modelAndView.addObject("user", new UserModel());
        modelAndView.addObject("message", "Please fill the boxes below to register");
        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView newAccountPost(UserModel user) {
        String message = "";
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newAccount");
        modelAndView.addObject("user", new UserModel());

        if (user == null) {
            message = "Invalid user data.";
            modelAndView.addObject("message", message);
            return modelAndView;
        }

        //check firstname
        if (!checker.checkFirstname(user.getFirstname())) {
            message = checker.firstnameErrorMessage;
            modelAndView.addObject("message", message);
            return modelAndView;
        }
        //check email
        if (!checker.checkEmail(user.getEmail())) {
            message = checker.emailErrorMessage;
            modelAndView.addObject("message", message);
            return modelAndView;
        }
        //check password
        if (!checker.checkPassword(user.getPassword())) {
            message = checker.passwordErrorMessage;
            modelAndView.addObject("message", message);
            return modelAndView;
        }

        if (new UserService().insertUser(user) == 1) {
            System.out.println("your account has been created and activated");
        }
        else System.out.println("Unable to insert the user");

        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping(value = "/images", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView imagesPost(@ModelAttribute UserModel userModel) {
        MultipartFile file = userModel.getMultipartFile();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/newaccount");
        modelAndView.addObject("user", new UserModel());
        if (file == null) System.out.println("File not uploaded....");
        else {
            System.out.println("The file uploaded success...");
            System.out.println("name : " + file.getName());
            System.out.println("original name : " + file.getOriginalFilename());
            System.out.println("content type : " + file.getContentType());
            System.out.println("isEmpty : " + file.isEmpty());
            System.out.println("Size : " + file.getSize());

//          Throws error
//            System.out.println("Bytes : "+ file.getBytes());

            String contentType = file.getOriginalFilename().split("/")
                    [file.getOriginalFilename().split("/").length - 1];
            System.out.println("new content type: " + contentType);

            //now copying to our source folder
            try {
                file.transferTo(new File("./" + file.getOriginalFilename()));
            } catch (IOException | IllegalStateException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
        return modelAndView;
    }

    class checker {
        private static String firstnameErrorMessage = "";
        private static String emailErrorMessage = "";
        private static String passwordErrorMessage = "";

        public static boolean checkFirstname(String firstname) {
            if (firstname == null) {
                firstnameErrorMessage = "firstname should be defined";
                return false;
            } else if (firstname.length() < 3) {
                firstnameErrorMessage = "Firstname should not be less than three characters";
                return false;
            }
            return true;
        }

        public static boolean checkEmail(String email) {
            if (email == null) {
                emailErrorMessage = "valid email should be defined";
                return false;
            } else if (email.length() < 6) {
                emailErrorMessage = "email should not be less than six characters";
                return false;
            }
            if (!email.contains("@")) {
                emailErrorMessage = "email should be valid address";
                return false;
            }
            return true;
        }

        public static boolean checkPassword(String password) {
            if (password == null) {
                passwordErrorMessage = "password should be greater than five characters";
                return false;
            } else if (password.length() < 5) {
                passwordErrorMessage = "password should be greater than five characters";
                return false;
            }
            return true;
        }
    }
}
