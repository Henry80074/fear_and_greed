package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTableIfNotExists {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/fear_and_greed",
                            "postgres", password.password);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            String create_table = "CREATE TABLE IF NOT EXISTS FEAR_GREED_INDEX " +
                    "(timestamp           CHAR(50)    UNIQUE, " +
                    "value          INT     NOT NULL, " +
                    "price          FLOAT     NOT NULL, " +
                    "value_classification          CHAR(50)    NOT NULL)";

            System.out.println(create_table);
            stmt.executeUpdate(create_table);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
