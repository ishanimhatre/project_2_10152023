package banking;

/**
 * Creates Savinds class to extend Account with specifications
 * @author Ishani Mhatre
 */
public class Savings extends Account{
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 4.0;
    protected static final int MONTHLY_FEE = 25;

    public Savings(){

    }

    public Savings(Profile holder, double balance, boolean isLoyal){
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    public boolean isLoyal() {
        return isLoyal;
    }
    public void setLoyal(boolean loyal) {
        isLoyal = loyal;
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal()){
            double interestRate = INTEREST_RATE+0.25;
            return (balance*(interestRate/100))/12;
        }
        else
            return (balance*(INTEREST_RATE/100))/12;
    }

    @Override
    public double monthlyFee() {
        if(balance>=500){
            return 0;
        }
        else{
            return MONTHLY_FEE;
        }
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof Savings){
            return super.compareTo(account);
        }
        else {
            //System.out.println("Comparing " + account.getClass().getName() + " and " + this.getClass().getName());
            return account.getClass().getName().compareTo(this.getClass().getName()); //this should be caleld
        }
    }

    @Override
    public String toString(){
        if(isLoyal)
            return "Savings: : " +holder.toString() + ": : Balance $" + balance + ": : is loyal";
        else
            return "Savings: : " +holder.toString() + ": : Balance $" + balance;
    }
}
