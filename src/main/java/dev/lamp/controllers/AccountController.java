package dev.lamp.controllers;

import com.google.gson.Gson;
import dev.lamp.dao.AccountDaoImplPostgres;
import dev.lamp.models.Account;

import dev.lamp.services.AccountService;
import dev.lamp.services.AccountServiceImpl;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController {
    private AccountService aService = new AccountServiceImpl(new AccountDaoImplPostgres());

    public Handler createAccountByClientIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        String body = ctx.body();
        Gson gson = new Gson();
        Account account = gson.fromJson(body, Account.class);
        this.aService.createAccountByClientID(client_id, account);
        String json = gson.toJson(account);
        ctx.result(json);
        ctx.result("Account has been created"+json);
        ctx.status(201);
    };

    public Handler getAccountByClientIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("idc"));
        int account_id = Integer.parseInt(ctx.pathParam("ida"));

        Gson gson = new Gson();
        Account account = this.aService.getAccountForClient(client_id, account_id);
        String json = gson.toJson(account);

        if(account != null){
            ctx.result("Account has been retrieved"+json);
            ctx.status(200);
        }else{
            ctx.result("Account not found");
            ctx.status(404);
        }
    };

    public Handler getAllAccountsByClientIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        List<Account> allAccounts = this.aService.getAllAccounts();
        List<Account> selectedAccounts = this.aService.getAllAccounts();
        for(Account a : allAccounts){
            if(client_id == a.getClientOwner()){
                selectedAccounts.add(a);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(selectedAccounts);
        if(selectedAccounts.contains(null)){
            ctx.result("Accounts not found");
            ctx.status(404);
        }else{
            ctx.result("Accounts have been retrieved"+json);
            ctx.status(201);
        }
    };

    public Handler getAllAccountsByClientIdBetweenAmountHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("id"));
        String amtLessThan = ctx.queryParam("amountLessThan", "NONE");
        String amtGreaterThan = ctx.queryParam("amountGreaterThan", "NONE");

        //getAllAccounts
        if(amtLessThan.equals("NONE") || amtGreaterThan.equals("NONE"))
        {
            List<Account> allAccts = this.aService.getAllAccountsByClientID(client_id);

            if(allAccts.isEmpty()){
                ctx.result("No Account was found");
                ctx.status(404);
            }
            else{
                Gson gson = new Gson();
                String acctsJson = gson.toJson(allAccts);
                ctx.result(acctsJson);
                ctx.status(200);
            }
        }else{
            List<Account> acctsBetween = this.aService.getAllAccountsByClientIdBetweenAmount(client_id);
            if(acctsBetween!=null)
            {
                Gson gson = new Gson();
                String acctJson = gson.toJson(acctsBetween);
                ctx.result(acctJson);
                ctx.status(200);
            }
        }
    };

    public Handler updateAccountByClientIdHandler = (ctx) -> {
        int client_id = Integer.parseInt(ctx.pathParam("idc"));
        int account_id = Integer.parseInt(ctx.pathParam("ida"));
        String body = ctx.body();
        Gson gson = new Gson();

        Account account = gson.fromJson(body, Account.class);
        account.setClientOwner(client_id);
        this.aService.updateAccountForClient(client_id, account);
        String json = gson.toJson(account);
        if(account == null){
            ctx.result("No such Account exists.");
            ctx.status(404);
        }else{
            ctx.result("Account has been updated"+json);
        }
    };

    public Handler deleteClientByIdHandler = (ctx) -> {
        int account_id = Integer.parseInt(ctx.pathParam("ida"));
        boolean flag = this.aService.deleteAccountForClient(account_id);
        ctx.status(200);
        ctx.result("Account has been deleted");
        if(!flag){
            ctx.result("No such client exists.");
            ctx.status(404);
        }
    };
}
