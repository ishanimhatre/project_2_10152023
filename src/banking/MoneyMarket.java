package banking;

/**
 * Class that extends Savings and defines interest rate, monthly fee, and minimum balance for Money Market account
 * @author Ishani Mhatre
 */
public class MoneyMarket extends Savings {
    private static final double INTEREST_RATE = 0.0475;
    private static final double MONTHLY_FEE = 25.0;
    private static final double MINIMUM_BALANCE = 2000.0;
    private int withdrawals;

    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true); // Money Market accounts are considered loyal by default
        this.withdrawals = 0;
    }

    @Override
    public double monthlyInterest() {
        if (balance >= MINIMUM_BALANCE) {
            return balance * INTEREST_RATE / 12;
        } else {
            return super.monthlyInterest(); // Use the base savings interest rate
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
}
