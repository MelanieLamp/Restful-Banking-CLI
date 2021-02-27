package dev.lamp.controllers;

import com.google.gson.Gson;
import dev.lamp.dao.ClientDaoImplPostgres;
import dev.lamp.models.Client;
import dev.lamp.services.ClientService;
import dev.lamp.services.ClientServiceImpl;
import io.javalin.http.Handler;

import java.util.List;

public class ClientController {
    private ClientService cService = new ClientServiceImpl(new ClientDaoImplPostgres());

    public Handler createClientHandler = (ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Client client = gson.fromJson(body, Client.class);
        System.out.println(client);
        this.cService.createClient(client);
        String json = gson.toJson(client);
        ctx.result("Client was created"+json);
        ctx.status(201);
    };

    public Handler getAllClientsHandler = (ctx) -> {
        List<Client> allClients = this.cService.getAllClients();
        Gson gson = new Gson();
        String clientsJson = gson.toJson(allClients);
        ctx.result("Client were retrieved"+clientsJson);
        ctx.status(200);
    };

    public Handler getClientsByIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.cService.getClientById(client_id);

        if(client == null){
            ctx.result("No such client exists.");
            ctx.status(404);
        }else{
            Gson gson = new Gson();
            String clientsJson = gson.toJson(client);
            ctx.result("Client was retrieved"+clientsJson);
            ctx.status(200);
        }
    };

    public Handler updateClientByIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        String body = ctx.body();
        Gson gson = new Gson();
        Client client = gson.fromJson(body, Client.class);

        if(client == null){
            ctx.result("No such client exists.");
            ctx.status(404);
        }else{
            String clientsJson = gson.toJson(client);
            ctx.result("Client has been updated"+clientsJson);
            ctx.status(200);
        }
        client.setClientID(client_id);
        this.cService.updateClientById(client_id);
    };

    public Handler deleteClientByIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        boolean flag = this.cService.deleteClientById(client_id);
        ctx.result("Client has been deleted");
        ctx.status(200);
        if(!flag){
            ctx.result("No such client exists.");
            ctx.status(404);
        }
    };
}
