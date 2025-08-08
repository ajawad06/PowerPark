package org.example.powerpark.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyJDBC {
    public Connection databaseLink;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(
                    DBConfig.getUrl(),
                    DBConfig.getUser(),
                    DBConfig.getPassword()
            );
            System.out.println("✅ Database connected successfully.");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return databaseLink;
    }
}
