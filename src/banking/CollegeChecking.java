package banking;

/**
 * Class for a College Checking account which extends Checking and defines interest rate and monthly fee
 * @author Keerthana Talla
 */
public class CollegeChecking extends Checking{

    private Campus campus; //campus code
    public CollegeChecking(Profile holder, double balance, Campus campus){
        super(holder, balance);
        this.campus = campus;
    }

    @Override
    public double monthlyFee() {
        return 0.0;
    }

    @Override
    public int compareTo(Account account) {
        if(account instanceof CollegeChecking){
            //System.out.println(account.getClass().getName());
            return super.compareTo(account);
        }
        else {
            return account.getClass().getName().compareTo(this.getClass().getName());
        }
    }

    @Override
    public String toString(){
        return "College Checking: : " +holder.toString() + ": : Balance $" + balance + ": :" + campus.name();
    }
}
