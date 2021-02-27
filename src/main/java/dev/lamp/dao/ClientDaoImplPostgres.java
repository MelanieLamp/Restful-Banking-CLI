package dev.lamp.dao;

import dev.lamp.models.Client;
import dev.lamp.utilities.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImplPostgres implements ClientDAO
{
    private final Logger logger = Logger.getLogger(ClientDaoImplPostgres.class.getName());

    @Override
    public Client createClient(Client client)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "insert into client (first_name, last_name, email) values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("client_id");
            client.setClientID(key);
            return client;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to create client " +client.getClientID(), e);
            return null;
        }
    }

    @Override
    public List<Client> getAllClients()
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Client> clients = new ArrayList<>();

            while(rs.next()){
                Client client = new Client();
                client.setClientID(rs.getInt("client_id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setEmail(rs.getString("email"));
                clients.add(client);
            }
            return clients;
        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve all clients ", e);
            return null;
        }
    }

    @Override
    public Client getClientById(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Client client = new Client();
            client.setClientID(rs.getInt("client_id"));
            client.setFirstName(rs.getString("first_name"));
            client.setLastName(rs.getString("last_name"));
            client.setEmail(rs.getString("email"));
            return client;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve client " + client_id, e);
            return null;
        }
    }

    @Override
    public Client updateClientById(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "update client set first_name = ?, " +
                    "last_name = ?, email = ? where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);
            Client client = new Client();
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getClientID());
            ps.executeUpdate();
            return client;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to update client" + client_id, e);
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "delete from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);
            ps.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to delete client" + client_id, e);
            return false;
        }
    }
}
