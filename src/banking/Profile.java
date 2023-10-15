package banking;


/** This class initializes Profile with values for first name, last name, and date of both. Compares if two profiles are identical.
 * @author Ishani Mhatre
 */

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    @Override
    public int compareTo(Profile profile) {
        int lastNameComparison = this.lname.compareToIgnoreCase(profile.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        } else {
            int firstNameComparison = this.fname.compareToIgnoreCase(profile.fname);
            if (firstNameComparison != 0) {
                return firstNameComparison;
            } else {
                return this.dob.compareTo(profile.dob);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Profile)) {
            return false;
        }
        Profile otherProfile = (Profile) obj;
        return this.fname.equalsIgnoreCase(otherProfile.fname) &&
                this.lname.equalsIgnoreCase(otherProfile.lname) &&
                this.dob.equals(otherProfile.dob);
    }

    @Override
    public String toString() {
        String formattedDob = dob.toString();  // You can format the Date object as needed
        return fname + " " + lname + " " + formattedDob;
    }

    public Object getFirstName() {
        return this.fname;
    }

    public Object getLastName() {
        return this.lname;
    }
}
