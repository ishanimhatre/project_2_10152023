package banking;

public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    public static final int SAME_PROFILE = 0;

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
    public int compareTo(Profile profile) {
        if(profile.lname.equalsIgnoreCase(this.lname)){
            if(profile.fname.equalsIgnoreCase(this.fname)){
                if(profile.dob.equals(this.dob)){
                    return SAME_PROFILE;
                }
                else{
                    return profile.dob.compareTo(this.dob);
                }
            }
            else{
                return profile.fname.compareTo(this.fname);
            }
        }
        else{
            return profile.lname.compareTo(this.lname);
        }
    }
    @Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.dob;
    }
}
