package com.dit.save_my_contacts.dao;

import com.dit.save_my_contacts.SaveMyContactsApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionDB {
    public static final String DATABASE_URL = "jdbc:sqlite:database_name.db";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public Connection getConnection(){
        try {
            String dbPath = Objects.requireNonNull(ConnectionDB.class.getResource("")).toExternalForm().concat("database_name.db");

            System.out.println(dbPath);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }

        try{
            return DriverManager.getConnection(DATABASE_URL);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }
}
