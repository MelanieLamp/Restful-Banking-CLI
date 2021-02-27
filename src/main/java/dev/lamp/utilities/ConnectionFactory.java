package dev.lamp.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
    public static Connection createConnection()
    {
        try{
            String secretDetails = System.getenv("CONN_DETAILS");
            Connection connection = DriverManager.getConnection(secretDetails);

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
