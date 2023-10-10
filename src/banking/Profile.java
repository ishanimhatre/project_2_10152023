package banking;

import java.util.Date;

/** This class initializes Profile with values for first name, last name, and date of both. Compares if two profiles are identical.
 * @author Ishani Mhatre
 */

public class Profile implements Comparable<Profile> {


    private String fname; //account holder first name
    private String lname; //account holder last name
    private Date dob; //account holder birthdate

    /**
     * Default constructor
     */
    public Profile() {
        // Leave values as null
    }

    /**
     * Constructor to access properties of Profile
     * @param fname
     * @param lname
     * @param dob
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter method for first name
     * @return account holder first name
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * Getter method for last name
     * @return account holder last name
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Getter method for date of birth
     * @return account holder date of birth
     */
    public Date getDateOfBirth() {
        return dob;
    }

    /**
     * Implement CompareTo method for comparable interface
     * @return result of comparison of two Profiles
     */
    @Override
    public int compareTo(Profile otherProfile) {
        // Compare based on first name, last name, and DOB

        int firstNameComparison = this.fname.compareTo(otherProfile.fname);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        int lastNameComparison = this.lname.compareTo(otherProfile.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        return this.dob.compareTo(otherProfile.dob);
    }

}
