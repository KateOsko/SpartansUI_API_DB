package com.sparta.utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DB_Util {
    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;

    public static int spartanId;

    public static void createConnection() {
        String dbUrl = ConfigurationReader.getProperty("spartan.db.url");
        String dbUser = ConfigurationReader.getProperty("spartan.db.username");
        String pass = ConfigurationReader.getProperty("spartan.db.password");
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, pass);
            System.out.println("The connection is successful");
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public static void getSpartanInfo() throws SQLException {

        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT name, gender, phone, spartan_id FROM spartans WHERE spartan_id = " + spartanId);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("NAME")
                    + " - " + resultSet.getString("GENDER")
                    + " - " + resultSet.getLong("PHONE")
                    + " - " + resultSet.getInt("SPARTAN_ID"));
        }
    }
}


