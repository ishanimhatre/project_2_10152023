package banking;

import java.util.Calendar;

public class Date implements Comparable<Date> {

    /**
     * default constructor
     */
    public Date () {
    }

    // days in a month for the whole year
    public static final int DAYSINMONTH_1 = 30; //max days first value
    public static final int DAYSINMONTH_2 = 31; //max days second value
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

    public static final int LOWER_BOUND = 0; //nothing negative

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
            case JAN: return DAYSINMONTH_2;
            case FEB: return isLeapYear() ? FEBLEAPYEAR : FEBREGULAR;
            case MARCH: return DAYSINMONTH_2;
            case APRIL: return DAYSINMONTH_1;
            case MAY: return DAYSINMONTH_2;
            case JUNE: return DAYSINMONTH_1;
            case JULY: return DAYSINMONTH_2;
            case AUGUST: return DAYSINMONTH_2;
            case SEPT: return DAYSINMONTH_1;
            case OCT: return DAYSINMONTH_2;
            case NOV: return DAYSINMONTH_1;
            case DEC: return DAYSINMONTH_2;
            default: return 0;
        }
    }

    /**
     * Method for checking date if lear year
     * @return true if Leap Year and false is not
     */
    private boolean isLeapYear() {
        return (year % QUADRENNIAL == LOWER_BOUND && year % CENTENNIAL != LOWER_BOUND) || (year % QUATERCENTENNIAL == LOWER_BOUND);
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
     * checking  date object with current year, month, day
     * @param date for date object
     * @return less than 0, equal to, or greater than 0 based on compareTo
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return Integer.compare(this.year, date.year);
        }
        if (this.month != date.month) {
            return Integer.compare(this.month, date.month);
        }
        return Integer.compare(this.day, date.day);
    }

    /**
     * Check if object equal to date instance
     * @param obj created  object to compare instance of date object
     * @return true if Leap Year and false is not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format("%-1d/%-1d/%-1d", month, day, year);
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
