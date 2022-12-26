package com.company;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String firstname;
    private String lastname;
    private String uuid; //user's ID number
    private byte pinHash[]; //MD5 hash of user's pin #
    private ArrayList<Account> accounts; //list of user's accounts

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstname = firstName;
        this.lastname = lastName;

        //pin's MD5 hash instead of pin for greater security
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuch AlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //uuid
        this.uuid = theBank.getNewUserUUID();
        //
        this.accounts = new ArrayList<Account>();
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }
    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.err.println("error, caught NoSuchAlgorithException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstname() {
        return this.firstname;
    }
    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts summary\n", this.firstname);
        for(int a = 0; a<this.accounts.size(); a++){
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    public int numAccounts(){
        return this.accounts.size();
    }
    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransHistory();
    }
    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }
    public String getAcctUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction(int acctIdx, double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
