package dev.lamp.daotests;

import dev.lamp.dao.AccountDAO;
import dev.lamp.dao.AccountDaoImplPostgres;
import dev.lamp.dao.ClientDAO;
import dev.lamp.dao.ClientDaoImplPostgres;
import dev.lamp.models.Account;

import dev.lamp.models.Client;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest
{
    private static final ClientDAO cdao = new ClientDaoImplPostgres();
    private static final AccountDAO adao = new AccountDaoImplPostgres();
    private static Account testAccount = null;
    private static Client testClient = null;

    @Test
    @Order(1)
    void create_account_by_client_id()
    {
        Client client = new Client(0, "Melanie", "Lamp", "Email");
        Client testClient2 = cdao.createClient(client);
        int client_id = testClient2.getClientID();

        Account account = new Account(0, 444.9, client_id);
        adao.createAccountByClientID(testClient2.getClientID(), account);

        Assertions.assertEquals(testClient2.getClientID(), account.getClientOwner());
        testAccount = account;
        testClient = testClient2;
    }

    @Test
    @Order(2)
    void get_all_accounts_by_client_id()
    {
        List<Account> accountList = new ArrayList<>();

        accountList.add(adao.createAccountByClientID(testClient.getClientID(), testAccount));
        Assertions.assertTrue(!accountList.isEmpty());
    }

    @Test
    @Order(3)
    void get_all_accounts(){
        Account a1 = new Account(0, 1000.0, 0);
        Account a2 = new Account(0, 2000.0, 0);
        Account a3 = new Account(0, 500.0, 0);
        List<Account> allAccounts = new ArrayList<>();
        allAccounts.add(a1);
        allAccounts.add(a2);
        allAccounts.add(a3);
        Assertions.assertTrue(allAccounts.size() > 2);
    }

    @Test
    @Order(4)
    void get_account_for_client()
    {
        int client_id = testClient.getClientID();
        int account_id = testAccount.getAccountId();
        Account account = new Account(account_id, 7000.0, client_id);
        adao.createAccountByClientID(client_id, account);
        adao.getAccountForClient(client_id, account_id);
        Client client = new Client(client_id, "Nicky", "Lamp", "email");
        Assertions.assertEquals(account.getClientOwner(), client.getClientID());
    }

    @Test
    @Order(5)
    void update_account_for_client(){
        int client_id = testClient.getClientID();
        int account_id = testAccount.getAccountId();
        Account account = new Account(account_id, 5000.0, client_id);
        account.setBalance(4000.0);
        Assertions.assertEquals(4000.0, account.getBalance());
    }

    @Test
    @Order(6)
    void delete_account_for_client(){
        boolean result = adao.deleteAccountForClient(testAccount.getClientOwner());
        Assertions.assertTrue(result);
    }
}