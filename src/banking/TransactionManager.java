package banking;

import java.util.Scanner;

/** Represents the User Interface
 * @author Ishani Mhatre
 * @author Keerthana Talla
 */

public class TransactionManager {

    public static final int MAX_AGE = 24; //maximum age to open College Checking account
    public static final int MIN_AGE = 16;
    public static final int MM_MIN_VAL = 2000; //minimum value to open MM account
    public static final int LOWER_BOUND = 0; //lower bound to check no negative numbers
    public static final int UPPER_BOUND_CAMPUS_CODE = 2;
    public static final int UPPER_BOUND_LOYALTY_CODE = 1;

    private AccountDatabase accountDatabase;

    /**
     * Default constructor
     */
    public TransactionManager() {
        accountDatabase = new AccountDatabase();
    }

    /**
     * User Interface method -- handles user's input
     */
    public void run() {
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputData = input.split("\\s+");
//        for(int i=0; i<inputData.length; i++){
//            System.out.println(inputData[i]);
//        }
        while (!inputData[0].equals("Q")) {
            switch (inputData[0]) {
                case "O":
                    System.out.println(handleCommandO(inputData));
                    break;
                case "C":
                    System.out.println(handleCommandC(inputData));
                    break;
                case "UB":
                    accountDatabase.printUpdatedBalances();
                    break;
                case "D":
                    System.out.println(handleCommandD(inputData));
                    break;
                case "W":
                    System.out.println(handleCommandW(inputData));
                    break;
                case "P":
                    accountDatabase.printSorted(inputData);
                    break;
                case "PI":
                    accountDatabase.printFeesAndInterests();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
            input = scanner.nextLine();
            while(input.isEmpty())
                input = scanner.nextLine();
            inputData = input.split("\\s+");
        }
        System.out.println("Transaction Manager terminated.");
        scanner.close();
    }

    private String handleCommandD(String[] inputData) {
        if (inputData.length < 5) {
            return "Missing date for depositing into account.";
        }

        try {
            String accountType = inputData[1];
            String firstName = inputData[2];
            String lastName = inputData[3];
            Date dob = Date.fromString(inputData[4]);
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            double amount = Double.parseDouble(inputData[5]);

            if (amount <= LOWER_BOUND) {
                return "Deposit - amount cannot be 0 or negative.";
            }

            Account account = null;

            switch (accountType) {
                case "C":
                    account = new Checking(profile, amount);
                    break;
                case "CC":
                    account = new CollegeChecking(profile, 0, null);
                    break;
                // Add more cases for other account types if needed
                default:
                    return "Invalid account type.";
            }

            if (accountDatabase.open(account)) {
                return profile.toString() + "(" + accountType + ") Deposit - balance updated.";
            } else if (accountDatabase.contains(account)) {
                accountDatabase.deposit(account, amount);
                return profile.toString() + "(" + accountType + ") Deposit - balance updated.";
            } else {
                return profile.toString() + "(" + accountType + ") is not in the database.";
            }
        } catch (Exception e) {
            return "Not a valid amount.";
        }
    }



    private String handleCommandC(String[] inputData) {
        try {
            if (inputData.length < 5) {
               return("Missing data for closing an account.");
            }
            String accountType = inputData[1];
            String firstName = inputData[2];
            String lastName = inputData[3];
            Date dob = Date.fromString(inputData[4]);

            if (!dob.isValid()) {return "DOB invalid: " + dob + " not a valid calendar date!";}
            if (!dob.isPresentorFutureDate()) { return "DOB invalid: " + dob + " cannot be today or a future day.";}
            if (dob.getAge() < MIN_AGE) {return "DOB invalid: " + dob + " under 16.";}
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            // Find the account in the database
            Account account = createDummyAccount(accountType, profile);

            if (accountDatabase.contains(account)) {
                accountDatabase.close(account);
                return profile.toString() + " (" + accountType + ") has been closed.";
            } else
                return profile.toString() + " (" + accountType + ") is not in the database.";
        } catch (Exception e) {
            return (e.toString());
        }
    }



    /**
     *
     * @param inputData String array with each cell having an input value
     * @return String with error message if input is incorrect, otherwise returns empty string
     */
    public String inputCheckForO(String[] inputData) {
        try {
            if ((inputData[1].equals("CC") || inputData[1].equals("S")) && inputData.length < 7) { //CollegeChecking and Savings requires 7 pieces of information
                return "Missing Data for opening an account.";
            } else if (inputData.length < 6) {
                return "Missing Data for opening an account."; //other accounts require 6 pieces of information
            } else {
                String accountType = inputData[1];
                String firstName = inputData[2];
                String lastName = inputData[3];
                Date date = Date.fromString(inputData[4]);
                double balance = Double.parseDouble(inputData[5]); //check if 0 or negative
                if (balance <= LOWER_BOUND) {
                    return "Initial deposit cannot be 0 or negative.";
                }
                if (!date.isValid()) {
                    return "DOB invalid: " + date + " not a valid calendar date!";
                }
                if (!date.isPresentorFutureDate()) {
                    return "DOB invalid: " + date + " cannot be today or a future day.";
                }
                if (date.getAge() < MIN_AGE) {
                    return "DOB invalid: " + date + " under 16.";
                }
                if (accountType.equals("CC") && (Integer.parseInt(inputData[6])< LOWER_BOUND || Integer.parseInt(inputData[6])> UPPER_BOUND_CAMPUS_CODE)) {
                    return "Invalid campus code.";
                }
                if (accountType.equals("S") && (Integer.parseInt(inputData[6]) < LOWER_BOUND || Integer.parseInt(inputData[6]) > UPPER_BOUND_LOYALTY_CODE)) {
                    return "Invalid loyalty code.";
                }
                return "";
            }
        }
        catch(NumberFormatException e){
            return "Not a valid amount.";
        }
    }



    public String openCheckingAccount(Profile profile, double balance){
        Checking checkingAcc = new Checking(profile, balance);
        if(accountDatabase.open(checkingAcc)){
            return profile.toString() + "(C) opened.";
        }
        else{
            return profile.toString() + "(C) is already in the database.";
        }
    }

    public String openCCAccount(Profile profile, double balance, int campusCode, Date dob){
        if(dob.getAge()< MAX_AGE) {
            Campus campus = Campus.fromCode(campusCode);
            CollegeChecking ccAccount = new CollegeChecking(profile, balance, campus);
            if (accountDatabase.open(ccAccount)) {
                return profile.toString() + "(CC) opened.";
            } else {
                return profile.toString() + "(CC) is already in the database."; //already is a c acc and ur trynna make a cc
            }
        }
        else{
            System.out.print(dob.getAge());
            return "DOB invalid: " + dob + " over 24.";
        }
    }
    public String openSavingsAccount(Profile profile, double balance, int loyaltyCode){
        boolean loyalty; //try catch here
        if(loyaltyCode == 1){
            loyalty = true;
        }
        else {
            loyalty = false;
        }
        Savings savingsAcc = new Savings(profile, balance, loyalty);
        if(accountDatabase.open(savingsAcc)){
            return profile.toString() + "(S) opened.";
        }
        else{
            return profile.toString() + "(S) is already in the database.";
        }
    }
    public String openMMAccount(Profile profile, double balance){
        if(balance>= MM_MIN_VAL) {
            MoneyMarket moneyMarket = new MoneyMarket(profile, balance, 0);
            if(accountDatabase.open(moneyMarket)){
                return profile.toString() + "(MM) opened.";
            }
            else{
                return profile.toString() + "(MM) is already in the database.";
            }
        }
        else{
            return "Minimum of $2000 to open a Money Market account.";
        }
    }
    public String handleCommandO(String[] inputData) {
        String error = inputCheckForO(inputData);
        if (error.isEmpty()) {
            String accountType = inputData[1];
            Date dob = Date.fromString(inputData[4]);
            double balance = Double.parseDouble(inputData[5]);
            Profile profile = new Profile(inputData[2], inputData[3], dob);
            switch(accountType){
                case "C":
                    return openCheckingAccount(profile, balance);
                case "CC":
                    int campusCode = Integer.parseInt(inputData[6]);
                    return openCCAccount(profile, balance, campusCode, dob);
                case "S":
                    int loyaltyCode = Integer.parseInt(inputData[6]);
                    return openSavingsAccount(profile, balance, loyaltyCode);
                case "MM":
                    return openMMAccount(profile, balance);
            }
        }
        return error;
    }

    /**
     *
     * @param inputData String array with each cell having an input value
     * @return String with error message if input is incorrect, otherwise returns empty string
     */
//    public String inputCheckForC(String[] inputData) {
//        try {
//            if(inputData.length!=4)
//                return "Missing data for closing an account.";
//
//            if (!date.withinBounds()){ //must check if date isvalid
//
//                return date + ": Invalid calendar date!";
//            }
//            if(!date.isFutureDate()){
//                return date + ": Event date must be a future date!";
//            }
//            if(!date.notMoreThanSixMonths()){
//                return date + ": Event date must be within 6 months!";
//            }
//            if (ts == null) {
//                return "Invalid time slot!";
//            }
//            if (loc == null) {
//                return "Invalid location!";
//            }
//            return "";
//        } catch (Exception e) {
//            return e.toString();
//        }
    // }

    private String handleCommandW(String[] inputData) {
        String accountType = inputData[1];
        Date dob = Date.fromString(inputData[4]);
        Profile profile = new Profile(inputData[2], inputData[3], dob);

        try {
            double withdrawalAmount = Double.parseDouble(inputData[5]);
            if (withdrawalAmount <= 0) {
                return "Withdraw - amount cannot be 0 or negative.";
            } else {
                Account account = createDummyAccount(accountType, profile);
                if (accountDatabase.contains(account)) {
                    double currentBalance = accountDatabase.getBalance(account); // Assuming you have a method to get the account balance
                    if (withdrawalAmount > currentBalance) {
                        return profile.toString() + "(" + accountType + ") Withdraw - insufficient funds.";
                    } else {
                        accountDatabase.withdraw(account, withdrawalAmount);
                        return profile.toString() + "(" + accountType + ") Withdraw - balance updated.";
                    }
                } else {
                    return profile.toString() + "(" + accountType + ") is not in the database.";
                }
            }
        } catch (NumberFormatException e) {
            return "Not a valid amount.";
        }
    }



    public Account createDummyAccount(String accountType, Profile profile){
        switch(accountType){
            case "C":
                return new Checking(profile, 0);
            case "CC":
                return new CollegeChecking(profile, 0, null);
            case "S":
                return new Savings(profile, 0, true);
            case "MM":
                return new MoneyMarket(profile, 0, 0);
            default:
                return null;
        }
    }


}
