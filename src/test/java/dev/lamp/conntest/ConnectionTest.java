package dev.lamp.conntest;

import dev.lamp.utilities.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest
{
    @Test
    void generates_connection(){
        Connection conn = ConnectionFactory.createConnection();
        System.out.println(conn);
        Assertions.assertNotNull(conn);
    }
}
