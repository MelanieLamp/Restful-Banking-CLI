package dev.lamp.main;

import dev.lamp.controllers.AccountController;
import dev.lamp.controllers.ClientController;
import io.javalin.Javalin;

public class App
{
    public static void main(String[] args)
    {
        ClientController clientController = new ClientController();
        Javalin app = Javalin.create();

        app.post("/clients", clientController.createClientHandler);
        app.get("/clients", clientController.getAllClientsHandler);
        app.get("/clients/:id", clientController.getClientsByIdHandler);
        app.put("/clients/:id", clientController.updateClientByIdHandler);
        app.delete("/clients/:id", clientController.deleteClientByIdHandler);

        AccountController accountController = new AccountController();

        app.post("/clients/:id/accounts", accountController.createAccountByClientIdHandler);
        app.get("/clients/:id/accounts", accountController.getAllAccountsByClientIdBetweenAmountHandler);

        app.get("/clients/:idc/accounts/:ida", accountController.getAccountByClientIdHandler);
        app.put("/clients/:idc/accounts/:ida", accountController.updateAccountByClientIdHandler);
        app.delete("/clients/:idc/accounts/:ida", accountController.deleteClientByIdHandler);
        app.start();
    }
}
