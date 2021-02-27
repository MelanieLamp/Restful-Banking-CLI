package dev.lamp.daotests;

import dev.lamp.dao.ClientDAO;
import dev.lamp.dao.ClientDaoImplPostgres;
import dev.lamp.models.Client;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTests
{
    private static final ClientDAO cdao = new ClientDaoImplPostgres();
    private static Client testClient = null;
    @Test
    @Order(1)
    void create_client()
    {
        Client client = new Client(0, "Susie", "Lamp", "email");
        cdao.createClient(client);
        testClient = client;
        Assertions.assertEquals(client.getClientID(), testClient.getClientID());
    }

    @Test
    @Order(2)
    void get_all_clients()
    {
        Client c1 = new Client(0, "Mel", "Lamp", "email");
        Client c2 = new Client(0, "Cameron", "Lamp", "email");
        Client c3 = new Client(0, "Mark", "Lamp", "email");
        List<Client> allClients = new ArrayList<>();

        allClients.add(c1);
        allClients.add(c2);
        allClients.add(c3);
        Assertions.assertTrue(allClients.size() > 2);
    }

    @Test
    @Order(3)
    void get_client_by_id(){
        Client client = new Client(0, "Mark", "Lamp", "email");
        cdao.createClient(client);
        Client c2 = cdao.getClientById(client.getClientID());
        System.out.println(c2.getClientID());

        Assertions.assertEquals(client.getClientID(), c2.getClientID());
        testClient = client;
    }

    @Test
    @Order(4)
    void update_client_by_id(){
        int client_id = testClient.getClientID();
        Client client = new Client(client_id, "Mark", "Lamp", "email");
        client.setFirstName("Susie");

        cdao.updateClientById(client.getClientID());
        Assertions.assertEquals("Susie", client.getFirstName());
    }

    @Test
    @Order(5)
    void delete_client_by_id(){
        int client_id = testClient.getClientID();

        boolean result = cdao.deleteClientById(client_id);
        Assertions.assertTrue(result);
    }
}