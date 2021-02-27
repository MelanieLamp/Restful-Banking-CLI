package dev.lamp.dao;

import dev.lamp.models.Account;

import java.util.List;

public interface AccountDAO
{
    Account createAccountByClientID(int client_id, Account account);
    List<Account> getAllAccountsByClientID(int client_id);
    List<Account> getAllAccounts();
    List<Account> getAllAccountsByClientIdBetweenAmount(int client_id);

    Account getAccountForClient(int client_id, int account_id);
    Account updateAccountForClient(int client_id, Account account);
    boolean deleteAccountForClient(int account_id);
}
