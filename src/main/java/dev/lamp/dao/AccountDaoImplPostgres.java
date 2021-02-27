package dev.lamp.dao;

import dev.lamp.models.Account;
import dev.lamp.utilities.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImplPostgres implements AccountDAO
{
    private final Logger logger = Logger.getLogger(AccountDaoImplPostgres.class.getName());

    @Override
    public Account createAccountByClientID(int client_id, Account account)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "insert into account (balance, client_owner) values (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, account.getBalance());
            ps.setDouble(2, client_id);
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("account_id");
            account.setAccountId(key);
            logger.info("Created account id: " +account.getAccountId() + " for client: " +client_id);
            return account;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to create account", e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccountsByClientID(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from account where client_owner = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);

            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();

            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setClientOwner(rs.getInt("client_owner"));
                accounts.add(account);
            }
            return accounts;
        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve all accounts", e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from account inner join client on " +
                    "client.client_id = account.client_owner";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();

            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setClientOwner(rs.getInt("client_owner"));
                accounts.add(account);
            }
            return accounts;
        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve all accounts", e);
            return null;
        }
    }

    @Override
    public List<Account> getAllAccountsByClientIdBetweenAmount(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from account where client_owner = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Account> accounts = new ArrayList<>();

            while(rs.next()){
                Account account = new Account();
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setClientOwner(rs.getInt("client_owner"));
                accounts.add(account);
            }
            return accounts;
        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve all accounts between amount", e);
            return null;
        }
    }

    @Override
    public Account getAccountForClient(int client_id, int account_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "select * from account where client_owner = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Account account = new Account();
            account.setAccountId(rs.getInt("account_id"));
            account.setBalance(rs.getDouble("balance"));
            account.setClientOwner(rs.getInt("client_owner"));
            return account;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to retrieve account "+account_id, e);
            return null;
        }
    }

    @Override
    public Account updateAccountForClient(int client_id, Account account)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "update account set balance = ? where client_owner = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, client_id);
            ps.executeUpdate();
            return account;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to update account"
                    + account.getAccountId()+ " for client: " +client_id, e);
            return null;
        }
    }

    @Override
    public boolean deleteAccountForClient(int client_id)
    {
        try(Connection conn = ConnectionFactory.createConnection()){
            final String sql = "delete from account where client_owner= ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client_id);
            ps.execute();
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            logger.error("Unable to delete account for client: "+client_id, e);
            return false;
        }
    }
}
