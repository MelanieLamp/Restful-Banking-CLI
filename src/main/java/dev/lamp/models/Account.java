package dev.lamp.models;

public class Account {
    private int accountId;
    private double balance;
    private int clientOwner;

    public Account() {
    }

    public Account(int accountId, double balance, int clientOwner)
    {
        this.accountId = accountId;
        this.balance = balance;
        this.clientOwner = clientOwner;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClientOwner() {
        return clientOwner;
    }

    public void setClientOwner(int clientOwner) {
        this.clientOwner = clientOwner;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", clientOwner=" + clientOwner +
                '}';
    }
}
