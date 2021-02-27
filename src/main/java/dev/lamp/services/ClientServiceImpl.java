package dev.lamp.services;

import dev.lamp.dao.ClientDAO;
import dev.lamp.models.Client;
import org.apache.log4j.Logger;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    static Logger logger = Logger.getLogger(ClientServiceImpl.class.getName());
    private final ClientDAO cdao;

    public ClientServiceImpl(ClientDAO clientDAO){
        this.cdao = clientDAO;
    }
    @Override
    public Client createClient(Client client)
    {
        try{
            return this.cdao.createClient(client);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to create Client", e);
            return null;
        }
    }

    @Override
    public List<Client> getAllClients()
    {
        try{
            return this.cdao.getAllClients();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to retrieve all clients", e);
            return null;
        }
    }

    @Override
    public Client getClientById(int client_id)
    {
        try{
            return this.cdao.getClientById(client_id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to retrieve client" +client_id, e);
            return null;
        }
    }

    @Override
    public Client updateClientById(int client_id)
    {
        try{
            return this.cdao.updateClientById(client_id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to update client" +client_id, e);
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int client_id)
    {
        try{
            return this.cdao.deleteClientById(client_id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to delete client" +client_id, e);
            return false;
        }
    }
}
