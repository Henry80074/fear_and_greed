package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
/* currently creating bug where table will  not update info correctly */
public class Connect {
    public static Connection ConnectDB() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fear_and_greed",
                    "postgres", password.password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            return c;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }
}