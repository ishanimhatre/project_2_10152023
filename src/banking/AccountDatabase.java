package banking;

/**
 * Class to hold accounts of different types
 * @author Ishani Mhatre
 */
public class AccountDatabase {
    private Account[] accounts; // List of various types of accounts
    private int numAcct; // Number of accounts in the array

    private final int NOT_FOUND = -1;

    public AccountDatabase() {
        accounts = new Account[4]; // Initial capacity is 4
        numAcct = 0;
    }

    // Increase the capacity by 4
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        for(int i =0; i<numAcct; i++){
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    // Find an account in the array
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (account.compareTo(accounts[i])==0) {
                return i;
            }
        }
        return NOT_FOUND; // Account not found
    }

    // Check if the account is already in the database
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }

    // Add a new account to the database
    public boolean open(Account account) {
        if (contains(account)) {
            return false; // Account already exists
        }
        accounts[numAcct] = account;
        numAcct++;
        if (numAcct == accounts.length) {
            grow();
        }
        return true;
    }


    // Remove the given account from the database
    public boolean close(Account account) {
        int index = find(account);
        if (index == NOT_FOUND) {
            return false; // Account not found
        }
        // Move the last account to the removed account's position
        accounts[index] = accounts[numAcct - 1];
        accounts[numAcct - 1] = null;
        numAcct--;
        return true;
    }

    // Withdraw an amount from the account (false if insufficient funds)
    public boolean withdraw(Account account, double amount) { //check if moneymarket withdrawls are less than three
        int index = find(account);
        if (index == NOT_FOUND) {
            return false; // Account not found
        }
        if (accounts[index].balance < amount) {
            return false; // Insufficient funds
        }
        accounts[index].balance -= amount;
        return true;
    }

    // Deposit an amount into the account
    public boolean deposit(Account account, double amount) {
        int index = find(account);
        if (index == NOT_FOUND) {
            return false;
        }
        accounts[index].balance += amount;
        return true;
    }

    public boolean printSorted(String[] inputData) {
        // Create a copy of the accounts array to avoid modifying the original array
        Account[] sortedAccounts = new Account[numAcct];
        for (int i = 0; i < numAcct; i++) {
            sortedAccounts[i] = accounts[i];
        }

        // Sort the accounts using bubble sort
        bubbleSort(sortedAccounts);

        // Print the sorted accounts
        for (Account account : sortedAccounts) {
            System.out.println(account); // You can customize the toString() method for the Account class
        }
        return false;
    }

    // Bubble sort algorithm
    private void bubbleSort(Account[] accounts) {
        int n = accounts.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                int typeComparison = accounts[i].getClass().getSimpleName().compareTo(accounts[i + 1].getClass().getSimpleName());

                // Check if either of the profiles (holder) is null
                if (accounts[i].holder == null || accounts[i + 1].holder == null) {
                    // Handle the case where a profile is null (you can define your logic here)
                } else {
                    int profileComparison = accounts[i].holder.compareTo(accounts[i + 1].holder);
                    if (typeComparison > 0 || (typeComparison == 0 && profileComparison > 0)) {
                        // Swap accounts[i] and accounts[i+1]
                        Account temp = accounts[i];
                        accounts[i] = accounts[i + 1];
                        accounts[i + 1] = temp;
                        swapped = true;
                    }
                }
            }
        } while (swapped);
    }




    // Calculate interests and fees
    public boolean printFeesAndInterests(String[] inputData) {
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            double monthlyInterest = account.monthlyInterest();
            double monthlyFee = account.monthlyFee();
            String accountType = account.getClass().getSimpleName(); // Get the account type

            // Print the interest and fee for each account
            System.out.printf("%s - Interest: $%.2f, Monthly Fee: $%.2f%n", accountType, monthlyInterest, monthlyFee);
        }
        return false;
    }


    // Apply interests and fees to update balances
    public boolean printUpdatedBalances(String[] inputData) {
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            double monthlyInterest = account.monthlyInterest();
            double monthlyFee = account.monthlyFee();

            // Update the account balance based on the interest and fees
            double updatedBalance = account.getBalance() + monthlyInterest - monthlyFee;
            account.setBalance(updatedBalance);

            String accountType = account.getClass().getSimpleName(); // Get the account type

            // Print the updated balance for each account
            System.out.printf("%s - Updated Balance: $%.2f%n", accountType, updatedBalance);
        }
        return false;
    }

    public boolean findAccount(String accountType, String firstName, String lastName) {
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            if (account.getClass().getSimpleName().equals(accountType)
                    && account.holder.getFirstName().equals(firstName)
                    && account.holder.getLastName().equals(lastName)) {
                return account;
            }
        }
        return null; // Account not found
    }

}
