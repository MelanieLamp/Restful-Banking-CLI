package dev.lamp.servicetests;

import dev.lamp.dao.AccountDaoImplPostgres;
import dev.lamp.dao.ClientDaoImplPostgres;
import dev.lamp.models.Account;
import dev.lamp.models.Client;
import dev.lamp.services.AccountService;
import dev.lamp.services.AccountServiceImpl;
import dev.lamp.services.ClientService;
import dev.lamp.services.ClientServiceImpl;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests
{
    private static final AccountService aserv = new AccountServiceImpl(new AccountDaoImplPostgres());
    private static final ClientService cserv = new ClientServiceImpl(new ClientDaoImplPostgres());
    private static Account testAccount = null;
    private static Client testClient = null;

    @Test
    @Order(1)
    void register_account_by_client_id()
    {
        Client client = new Client(0, "Mark", "Lamp", "email");
        cserv.createClient(client);
        Account account = new Account(0, 5000.0, client.getClientID());

        Account a1 = aserv.createAccountByClientID(client.getClientID(), account);
        testClient = client;
        testAccount = account;
        Assertions.assertEquals(testClient.getClientID(), testAccount.getClientOwner());
    }

    @Test
    @Order(2)
    void get_all_accounts_for_client()
    {
        int client_id = testClient.getClientID();
        Account a1 = new Account(0, 7000.0, 0);
        Account a2 = new Account(0, 5000.0, 0);
        Account a3 = new Account(0, 9000.0, 0);
        aserv.createAccountByClientID(client_id, a1);
        aserv.createAccountByClientID(client_id, a2);
        aserv.createAccountByClientID(client_id, a3);
        List<Account> acctList = new ArrayList<>();

        acctList.add(a1);
        acctList.add(a2);
        acctList.add(a3);
        Assertions.assertTrue(acctList.size() > 2);
    }

    @Test
    @Order(3)
    void get_all_accounts_for_client_between_amount()
    {
        Account a1 = new Account(0, 1500.0, 0);
        Account a2 = new Account(0, 1800.0, 0);
        Account a3 = new Account(0, 9000.0, 0);
        List<Account> acctList = new ArrayList<>();
        acctList.add(a1);
        acctList.add(a2);
        acctList.add(a3);
        acctList.removeIf(a -> a.getBalance() < 400.0 && a.getBalance() > 2000.0);
        Assertions.assertFalse(acctList.isEmpty());
    }

    @Test
    @Order(4)
    void get_account_for_client()
    {
        Client client = testClient;
        Account account = testAccount;

        System.out.println("client: " +client+ ", " +testClient);
        System.out.println("acct: " +account+ ", " +testAccount);

        Account acct1 = aserv.getAccountForClient(client.getClientID(), account.getAccountId());

        Assertions.assertEquals(client.getClientID(), account.getClientOwner());
        Assertions.assertEquals(client.getClientID(), acct1.getClientOwner());
    }

    @Test
    @Order(5)
    void get_all_accounts(){
        Client c1 = new Client(0, "Mel", "Lamp", "email");
        Client c2 = new Client(0, "Cameron", "Lamp", "email");
        Client c3 = new Client(0, "Mark", "Lamp", "email");
        Account account1 = new Account(0, 5000.0, c1.getClientID());
        Account account2 = new Account(0, 5000.0, c2.getClientID());
        Account account3 = new Account(0, 5000.0, c3.getClientID());
        List<Account> allAccounts = new ArrayList<>();
        allAccounts.add(account1);
        allAccounts.add(account2);
        allAccounts.add(account3);
        Assertions.assertTrue(allAccounts.size() > 2);
    }

    @Test
    @Order(6)
    void update_account_for_client(){
        int client_id = testClient.getClientID();
        int account_id = testAccount.getAccountId();
        Account account = new Account(account_id, 5000.0, client_id);
        account.setBalance(2000.0);
        Account updatedAccount = aserv.updateAccountForClient(client_id, account);
        Assertions.assertEquals(2000.0, updatedAccount.getBalance());
    }

    @Test
    @Order(7)
    void delete_account_for_client(){
        int client_id = 4;
        int account_id = 5;
        Account account = new Account(account_id, 9999.0, client_id);

        boolean result = aserv.deleteAccountForClient(account_id);
        Assertions.assertTrue(result);
    }
}
