package banking;

/**
 * Class for a Checking account is made with holder, balance, and information for interest rate and monthly fee
 * @author Ishani Mhatre
 */
public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01;
    private static final double MONTHLY_FEE = 12.0;
    private static final double MINIMUM_BALANCE = 1000.0;

    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    @Override
    public double monthlyInterest() {
        if (balance >= MINIMUM_BALANCE) {
            return balance * INTEREST_RATE;
        } else {
            return 0.0;
        }
    }

    @Override
    public double monthlyFee() {
        if (balance >= MINIMUM_BALANCE) {
            return 0.0;
        } else {
            return MONTHLY_FEE;
        }
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof Checking && !(account instanceof CollegeChecking)){
            return super.compareTo(account);
        }
        else {
            return account.getClass().getName().compareTo(this.getClass().getName());
        }
    }
}
