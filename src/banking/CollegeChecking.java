package banking;

/**
 * Class for a College Checking account which extends Checking and defines interest rate and monthly fee
 */
public class CollegeChecking extends Checking {
    private Campus campus;

    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }


}
