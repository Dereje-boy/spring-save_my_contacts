package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.model.UserModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    Statement statement;
    Connection connection;
    public int insertUser(UserModel userModel){
        connection = new ConnectionDB().getConnection();
        if (connection == null) return -1;
        try {
            statement = connection.createStatement();
            String insertCommand = String.format("insert into user(firstname,lastname,username,password) " +
                    "values ('%s','%s','%s','%s')",
                    userModel.getFirstname(),userModel.getLastname(),userModel.getEmail(),userModel.getPassword());
            statement.execute(insertCommand);
            return 1;
        } catch (SQLException e) {
            System.out.println("Unable to create statement \n"+e.getMessage());
            return -1;
        }
    }
}
