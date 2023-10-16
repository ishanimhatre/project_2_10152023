package banking;

/**
 * Extends Savinds and defines value for MM account
 * @author Ishani Mhatre
 */
public class MoneyMarket extends Savings{

    private int withdrawal; //number of withdrawls
    private static final double INTEREST_RATE = 4.5;
    protected static final double MIN_BALANCE = 2000.0;

    public MoneyMarket(){

    }

    public MoneyMarket(Profile holder, double balance, int withdrawal) {
        super(holder, balance, true);
        this.withdrawal = withdrawal;
    }

    public int getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal){
            double interestRate = INTEREST_RATE+0.25;
            return (balance*(interestRate/100))/12;
        }
        else{
            return (balance*(INTEREST_RATE/100))/12;
        }
    }

    @Override
    public double monthlyFee() {
        if(balance>=MIN_BALANCE){
            return 0;
        }
        else{
            return Savings.MONTHLY_FEE;
        }
    }
    @Override
    public int compareTo(Account account) {
        if(account instanceof MoneyMarket){
            return super.compareTo(account);
        }
        else {
            return account.getClass().getName().compareTo(this.getClass().getName());
        }
    }

    @Override
    public String toString() {
        if (isLoyal)
            return "Money Market: : " + holder.toString() + ": : Balance $" + balance + ": : is loyal";
        else
            return "Money Market: : " + holder.toString() + ": : Balance $" + balance;
    }
}
