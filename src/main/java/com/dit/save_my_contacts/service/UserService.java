package com.dit.save_my_contacts.service;

import com.dit.save_my_contacts.dao.UserDAO;
import com.dit.save_my_contacts.model.UserModel;

public class UserService {
    public int insertUser(UserModel userModel){
        return new UserDAO().insertUser(userModel);
    }
}
