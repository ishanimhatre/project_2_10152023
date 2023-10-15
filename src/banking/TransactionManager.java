package banking;

import java.util.Scanner;

/**
 * User interface class that processes the transactions entered on the terminal and performs all Input/Ouput.
 * @author Ishani Mhatre
 */

public class TransactionManager {

    private AccountDatabase accountDatabase;

    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
    }

    public void run() {
        System.out.println("Transaction Manager running....");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputData = input.split("\\s+");
        System.out.println();

        while (!inputData[0].equals("Q")) {
                switch (inputData[0]) {
                    case "O":
                        System.out.println(openAccount(inputData));
                        break;
                  case "C":
                        System.out.println(closeAccount(inputData));
                        break;
                   case "R":
                        System.out.println(accountDatabase.printUpdatedBalances(inputData));
                        break;
                   case "D":
                        System.out.println(deposit(inputData));
                        break;
                    case "W":
                        System.out.println(withdraw(inputData));
                        break;
                    case "P":
                        System.out.println(accountDatabase.printSorted(inputData));
                        break;
                    case "F":
                        System.out.println(accountDatabase.printFeesAndInterests(inputData));
                        break;
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            input = scanner.nextLine();
            if (input.isEmpty()) {
                input = scanner.nextLine();
            }
            inputData = input.split("\\s+");
        }
        System.out.println("Transaction Manager terminated.");
        scanner.close();
    }

    public String openAccount(String[] inputData) {
        try {

            if (inputData.length < 6) {
                return "Missing data for opening an account.";
            }

            String depositStr = (inputData[5]);

            // Check if the deposit amount is a valid double
            double initialDeposit;
            try {
                initialDeposit = Double.parseDouble(depositStr);
            } catch (NumberFormatException ex) {
                return "Not a valid amount.";
            }

            //Check initial deposit
            if (initialDeposit <= 0) {
                return "Initial deposit cannot be 0 or negative.";
            }

            String accountType = (inputData[1]);
            String firstName = (inputData[2]);
            String lastName = (inputData[3]);
            Date dateOfBirthStr = Date.fromString(inputData[4]);
            int campusCode;
            boolean loyalCustomer = false; // Initialize to an invalid value


            Scanner scanner = null;

            if (accountType.equals("C") || accountType.equals("CC") || accountType.equals("S") || accountType.equals("MM")) {
                if (accountType.equals("CC")) {
                    campusCode = Integer.parseInt(inputData[6]);
                        if (campusCode < 0 || campusCode > 2) {
                            System.out.println("Invalid campus code.");
                        }
                        if (!dateOfBirthStr.isAbove24()) {
                            System.out.println("DOB invalid: " + dateOfBirthStr + " over 24");
                        }
                } else if (accountType.equals("S")) {
                    int loyalCustomerInput = Integer.parseInt(inputData[6]);
                    loyalCustomer = (loyalCustomerInput == 1); // Set loyalCustomer to true if input is 1
                } else if (accountType.equals("MM")) {
                    if(initialDeposit < 2000) {
                        System.out.println("Minimum of $2000 to open a Money Market account.");
                    }
                }



                // Check if the date of birth is valid
                if (!dateOfBirthStr.withinBounds()) { //must check if date isvalid
                    System.out.println("DOB invalid: " + dateOfBirthStr + " not a valid calendar date!");
                }
                if (!dateOfBirthStr.isPresentorFutureDate()) {
                    System.out.println("DOB invalid: " + dateOfBirthStr + " cannot be today or a future day.");
                }
                if (!dateOfBirthStr.isAtLeast16YearsOld()) {
                    System.out.println("DOB invalid: " + dateOfBirthStr + " under 16");
                }


                // Check if the account already exists in the database
                Account account = new Account() {
                    @Override
                    public double monthlyInterest() {
                        return 0;
                    }

                    @Override
                    public double monthlyFee() {
                        return 0;
                    }
                };

                if (accountDatabase.contains(account)) {
                    return firstName + " " + lastName + " " + dateOfBirthStr + "(" + accountType + ") is already in the database.";
                }

                // If all checks pass, add the account to the database
                accountDatabase.open(account);
                return firstName + " " + lastName + " " + dateOfBirthStr + "(" + accountType + ") opened.";
            } else {
            }
        } catch (Exception e) {
            return e.toString();
        }
        return null;
    }


    public boolean closeAccount(String[] inputData) {
        try {
            if (inputData.length < 4) {
                System.out.println("Missing data for closing an account.");
                return false;
            }

            String accountType = inputData[1];
            String firstName = inputData[2];
            String lastName = inputData[3];
            String balance = inputData[4];

            // Find the account in the database
            Account account = accountDatabase.findAccount(accountType, firstName, lastName);

            if (account == null) {
                System.out.println(firstName + " " + lastName + " (" + accountType + ") not found in the database.");
                return false;
            }

            // Close the account
            accountDatabase.close(account);
            System.out.println(firstName + " " + lastName + " (" + accountType + ") closed.");
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }


    public boolean deposit(String[] inputData) {
        if (inputData.length < 4) {
            System.out.println("Invalid input format. Expected: D <AccountType> <Name> <DateOfBirth> <Amount>");
            return false;
        }

        String accountType = inputData[1];
        String name = inputData[2];
        String dateOfBirth = inputData[3];
        double amount = 0.0;

        try {
            amount = Double.parseDouble(inputData[4]);
        } catch (NumberFormatException e) {
            System.out.println("Deposit - amount cannot be 0 or negative.\n");
            return false;
        }

        // Validate the amount
        if (amount <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.\n");
            return false;
        }

        Account account = null;
        // Validate the account holder's information (you may need to implement your own logic for this)
        if (!accountDatabase.findAccount(accountType, name, dateOfBirth)) {
            System.out.println(name + dateOfBirth + "(" + accountType + ")" + "is not in the database." );
            return false;
        }

        // Deposit the money into the account (you need to implement your own logic for this)
        boolean depositSuccess = accountDatabase.deposit(account, amount);

        if (depositSuccess) {
            System.out.println(name + dateOfBirth + "(" + accountType + ")" + "Deposit - balance updated.");
            return true;
        } else {
            System.out.println("Deposit failed. Please check the account information.");
            return false;
        }
    }



    public boolean withdraw(String[] inputData) {
    }



    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        manager.run();
    }
}
