package banking;

import java.util.Calendar;
import java.util.Objects;

/** This class initializes Date object with year, month, and day. Checks if input date is valid
 * @author Ishani Mhatre
 */


public class Date implements Comparable<Date> {

    /**
     * default constructor
     */
    public Date () {
    }

    // days in a month for the whole year
    public static final int DAYSINMONTH_ONE = 30; //max days first value
    public static final int DAYSINMONTH_TWO = 31; //max days second value
    public static final int FEBREGULAR = 28; //max for non leap year
    public static final int FEBLEAPYEAR = 29; //max for leap year

    // 12 cases for each month
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPT = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;

    // integer values for checking Leap Year
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    // variables for upper and lower bounds of valid Dates
    public static final int LOWERBOUND = 1; //cannot be negative
    public static final int COUNTOFMONTHSINYEAR = 12; //total months

    //No remainder
    public static final int NODAYSLEFTOVER = 0; //Holds true for leap year

    //Min & Max ages
    public static final int MIN_AGE = 16; //minimum age to open bank account
    public static final int MAX_AGE = 24; //maximum age to open College Checking account

    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Calls upon three methods including withinBounds, isFutureDate, and notMoreThanSixMonths to test if Date input is valid
     * @return true if valid, false is not
     */
    public boolean isValid() {

        if (!withinBounds()) {
            return false;
        }
        return true;
    }


    /**
     * Finding the upper bound for max days in a month
     * @return int for Max days in a month
     */
    private int calculateMaxDaysInMonth() {
        switch (month) {
            case JAN: return DAYSINMONTH_TWO;
            case FEB: return isLeapYear() ? FEBLEAPYEAR : FEBREGULAR;
            case MARCH: return DAYSINMONTH_TWO;
            case APRIL: return DAYSINMONTH_ONE;
            case MAY: return DAYSINMONTH_TWO;
            case JUNE: return DAYSINMONTH_ONE;
            case JULY: return DAYSINMONTH_TWO;
            case AUGUST: return DAYSINMONTH_TWO;
            case SEPT: return DAYSINMONTH_ONE;
            case OCT: return DAYSINMONTH_TWO;
            case NOV: return DAYSINMONTH_ONE;
            case DEC: return DAYSINMONTH_TWO;
            default: return NODAYSLEFTOVER;
        }
    }

    /**
     * Method for checking date if lear year
     * @return true if Leap Year and false is not
     */
    private boolean isLeapYear() {
        return (year % QUADRENNIAL == NODAYSLEFTOVER && year % CENTENNIAL != NODAYSLEFTOVER) || (year % QUATERCENTENNIAL == NODAYSLEFTOVER);
    }

    /**
     * Method to convert from string to date object
     * @param str input string for Date
     * @return Date object with year, month, and day
     */
    public static Date fromString(String str) {
        String[] parts = str.split("/");
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        return new Date(year, month, day);
    }

    /**
     * Method to check if Date input is not negative and month is not more than 12
     * @return true if valid and false if not
     */
    public boolean withinBounds() {
        if (year < LOWERBOUND || month < LOWERBOUND || month > COUNTOFMONTHSINYEAR || day < LOWERBOUND) {
            return false;
        }
        int maxDaysInMonth = calculateMaxDaysInMonth();
        if (day > maxDaysInMonth) {
            return false;
        }
        return true;
    }


    /**
     * Check that Date is in the future, not past
     * @return true if Date set in the future, false if not
     */
    boolean isPresentorFutureDate() {
        Calendar today = Calendar.getInstance();
        Calendar eventDate = Calendar.getInstance();
        eventDate.set(this.getYear(), this.getMonth(), this.getDay());

        if(eventDate.after(today) || (eventDate.equals(today))) {
            return false;
        }

        return true;

    }


    /**
     * Checks miniimum age requirements for opening account
     * @return true if over sixteen years and false if not
     */
//    boolean isAtLeast16YearsOld() {
//        // Calculate today's date
//        Calendar currentDate = Calendar.getInstance();
//
//        // Calculate a date 16 years ago
//        Calendar eighteenYearsAgo = Calendar.getInstance();
//        eighteenYearsAgo.add(Calendar.YEAR, -MIN_AGE);
//
//        // Check if the date of birth is on or before the date 16 years ago
//        Calendar today = Calendar.getInstance();
//        Calendar eventDate = Calendar.getInstance();
//        eventDate.set(this.getYear(), this.getMonth(), this.getDay());
//
//        if(eventDate.before(isAtLeast16YearsOld()) || (eventDate.equals(isAtLeast16YearsOld()))) {
//            return true;
//        }
//
//        return false;
//    }

    /**
     * Checks age is valid for College Checking account
     * @return true if age between range and false if not
     */
//    boolean isAbove24() {
//        // Calculate today's date
//        Calendar currentDate = Calendar.getInstance();
//
//        // Calculate a date 24 years ago from today (upper bound)
//        Calendar twentyFourYearsAgo = Calendar.getInstance();
//        twentyFourYearsAgo.add(Calendar.YEAR, -MAX_AGE);
//
//        // Calculate a date of birth
//        Calendar birthDate = Calendar.getInstance();
//        birthDate.set(this.getYear(), this.getMonth() - 1, this.getDay()); // Adjust month to be 0-based
//
//        // Check if the date of birth is after 24 years ago
//        return birthDate.after(twentyFourYearsAgo);
//    }


    public int getAge(){
        Calendar birthDateCalendar = Calendar.getInstance();
        birthDateCalendar.set(this.getYear(), this.getMonth() - 1, this.getDay());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birthDateCalendar.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthDateCalendar.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < birthDateCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }




    /**
     * checking  date object with current year, month, day
     * @param date for date object
     * @return less than zero, equal to, or greater than zero based on compareTo
     */
    @Override
    public int compareTo(Date date) {
        if(this.equals(date)) {
            return NODAYSLEFTOVER;
        }else {

            if (this.year != date.year) {
                return Integer.compare(this.year, date.year);
            }
            if (this.month != date.month) {
                return Integer.compare(this.month, date.month);
            }
            return Integer.compare(this.day, date.day);
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Date other = (Date) obj;
        return day == other.day && month == other.month && year == other.year;
    }
    @Override
    public String toString() {
        return String.format("%-1d/%-1d/%-1d", month, day, year);
    }

    /**
     * Method that converts the date to a string for valid or not valid and prints result. Assert to check if expected is the same as actual
     * @param date valid date input
     * @param expectedOutput expected date output
     * @param actualOutput actual output
     */
    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput) {
        String result = date.toString() + " is " + (actualOutput ? "valid" : "invalid");
        System.out.println(result);
        assert expectedOutput == actualOutput : "Test failed for " + date.toString();
    }

    /**
     * Getter method for year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method for month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for Date
     * @return day
     */
    public int getDay() {
        return day;
    }
}


