package dev.lamp.servicetests;

import dev.lamp.dao.ClientDaoImplPostgres;
import dev.lamp.models.Client;
import dev.lamp.services.ClientService;
import dev.lamp.services.ClientServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTests
{
    private static final ClientService cserv = new
            ClientServiceImpl(new ClientDaoImplPostgres());
    private static Client testClient = null;

    @Test
    @Order(1)
    void register_client(){
        Client client = new Client(0, "Tiger", "Woods", "Email");
        cserv.createClient(client);
        Assertions.assertNotEquals(0, client.getClientID());
        testClient = client;
    }

    @Test
    @Order(2)
    void get_all_clients()
    {
        Client c1 = new Client(0, "Mel", "Lamp", "email");
        Client c2 = new Client(0, "Cameron", "Lamp", "email");
        Client c3 = new Client(0, "Mark", "Lamp", "email");

        cserv.createClient(c1);
        cserv.createClient(c2);
        cserv.createClient(c3);

        List<Client> allClients = cserv.getAllClients();
        Assertions.assertTrue(allClients.size() > 2);
    }

    @Test
    @Order(3)
    void get_client_by_id()
    {
        int client_id = testClient.getClientID();
        Client client = cserv.getClientById(client_id);
        cserv.createClient(client);
        Assertions.assertNotEquals(client_id, client.getClientID());
    }

    @Test
    @Order(4)
    void update_client_by_id()
    {
        Client client = new Client(0, "Dukey", "Lamp", "Email");
        client.setFirstName("Cameron");
        cserv.updateClientById(client.getClientID());
        Assertions.assertEquals("Cameron", client.getFirstName());
    }

    @Test
    @Order(5)
    void delete_client_by_id()
    {
        int client_id = testClient.getClientID();
        boolean result = cserv.deleteClientById(client_id);
        Assertions.assertTrue(result);
    }
}
