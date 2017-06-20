package pl.novaris.resumebuilder.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class HibernateConfig {

    public void connectToDb() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/resumebuilder";
        String user = "postgres";
        String password = "root";

        try {
            System.out.println("Connecting to the database: " + jdbcUrl);
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
