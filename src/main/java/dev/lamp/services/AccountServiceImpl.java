package dev.lamp.services;

import dev.lamp.dao.AccountDAO;
import dev.lamp.models.Account;
import org.apache.log4j.Logger;

import java.util.List;

public class AccountServiceImpl implements AccountService
{
    static Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());
    private final AccountDAO adao;

    public AccountServiceImpl(AccountDAO accountDAO){
        this.adao = accountDAO;
    }
    @Override
    public Account createAccountByClientID(int client_id, Account account)
    {
        try{
            logger.info("Created account: "+account.getAccountId()+ " for client: "+client_id);
            return this.adao.createAccountByClientID(client_id, account);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to create Account" +client_id, e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccountsByClientID(int client_id)
    {
        try{
            logger.info("Retrieved all accounts for client: "+client_id);
            return this.adao.getAllAccountsByClientID(client_id);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Unable to retrieve accounts" +client_id, e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccountsByClientIdBetweenAmount(int client_id)
    {
        try{
            List<Account> accounts = this.adao.getAllAccountsByClientID(client_id);

            logger.info("Retrieved all accounts between balance amounts for client: "+client_id );
            return accounts;
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to retrieve all accounts" +client_id, e);
            return null;
        }
    }

    @Override
    public Account getAccountForClient(int client_id, int account_id)
    {
        try{
            logger.info("Retrieved account: "+account_id+ " for client: "+client_id);
            return this.adao.getAccountForClient(client_id, account_id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to retrieve accounts "
                    +account_id+" for client: " +client_id , e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try{
            return this.adao.getAllAccounts();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to retrieve all accounts", e);
            return null;
        }
    }

    @Override
    public Account updateAccountForClient(int client_id, Account account)
    {
        try{
            logger.info("Updated account: "+account.getAccountId()+ " for client: "+client_id);
            return this.adao.updateAccountForClient(client_id, account);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to update account: "
                    +account.getAccountId()+ " for client: " +client_id, e);
            return null;
        }
    }

    @Override
    public boolean deleteAccountForClient(int account_id) {
        try{
            logger.info("Deleted account: "+account_id);
            return this.adao.deleteAccountForClient(account_id);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Unable to delete account: " +account_id, e);
            return false;
        }
    }
}
