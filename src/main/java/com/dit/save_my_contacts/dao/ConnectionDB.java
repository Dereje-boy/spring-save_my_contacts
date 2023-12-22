package com.dit.save_my_contacts.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static final String DATABASE_URL = "jdbc:sqlite:database_name.db";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public Connection getConnection(){
        try{
            return DriverManager.getConnection(DATABASE_URL);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }
}
