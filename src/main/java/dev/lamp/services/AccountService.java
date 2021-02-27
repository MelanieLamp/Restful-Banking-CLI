package dev.lamp.services;

import dev.lamp.models.Account;

import java.util.List;

public interface AccountService
{
    Account createAccountByClientID(int client_id, Account account);
    List<Account> getAllAccountsByClientIdBetweenAmount(int client_id);

    List<Account> getAllAccountsByClientID(int client_id);
    List<Account> getAllAccounts();

    Account getAccountForClient(int client_id, int account_id);
    Account updateAccountForClient(int client_id, Account account);
    boolean deleteAccountForClient(int account_id);
}
