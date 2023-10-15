package banking;

/**
 * Class that defines Savings account and initializes interest rate, monthly fee, and minimum balance
 * @author Ishani Mhatre
 */
public class Savings extends Account {
    private static final double INTEREST_RATE = 0.04;
    private static final double MONTHLY_FEE = 25.0;
    private boolean isLoyal;

    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    @Override
    public double monthlyInterest() {
        double interestRate = INTEREST_RATE;
        if (isLoyal) {
            interestRate += 0.0025; // Additional interest for loyal customers
        }
        return balance * interestRate / 12;
    }

    @Override
    public double monthlyFee() {
        if (balance >= 500) {
            return 0.0;
        } else {
            return MONTHLY_FEE;
        }
    }
}
