package banking;

/**
 * An abstract class that accounts for the general type of accounts
 * @author Ishani Mhatre
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public Account() {

    }

    public Profile getProfile() {
        return holder;
    }


    public abstract double monthlyInterest();

    public abstract double monthlyFee();

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(Account otherAccount) {
        return this.holder.compareTo(otherAccount.holder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        Account otherAccount = (Account) obj;
        return this.holder.equals(otherAccount.holder);
    }


    @Override
    public abstract String toString();

}







