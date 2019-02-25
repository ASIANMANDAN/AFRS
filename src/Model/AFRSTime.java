package Model;

/**
 * Time within the AFRS database. Time has both an hour (0 - 24) and a minute value (0 - 59). Provided a String,
 * in the format #:#[a|b], is able to create an instance of time.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */
public class AFRSTime {

    public enum Measurement {
        DURATION,
        DATE,
    }

    // ----------
    // Attributes
    // ----------

    private int hour;
    private int minutes;
    private Measurement measurement;

    // -------
    // Methods
    // -------

    /**
     * Determines if the provided time is in the afternoon.
     * @param time String time from the provided CSV database. Expected format:
     *             #:#[a|p].
     * @return True if the time is in the afternoon; false otherwise.
     */
    private boolean isAfterNoon(String time) {
        if(time.charAt(time.length() - 1) == 'a') {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Turn extra minutes (60+) into hours. Can be used on Duration or Date AFRSTimes.
     */
    private void minuteToHours() {
        while(this.minutes >= 60) {
            this.hour += 1;
            this.minutes -= 60;
        }
    }

    private int getIntMinutes(String s) {
        return Integer.parseInt(s.substring(0, s.length() - 1));
    }

    /**
     * Create a new AFRSTime when provided with time in numerical format.
     * @param hour int hour in military time.
     * @param minutes int minutes .
     */
    public AFRSTime(AFRSTime.Measurement measurement, int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
        this.measurement = measurement;
    }

    /**
     * Create a new AFRSTime when provided with time in String format (from CSV database).
     * @param time String time in the format #:#[a|b].
     */
    public AFRSTime(String time) {
        String[] numbers = time.split(":"); // Split the time, Hours and Minutes
        this.hour = Integer.parseInt(numbers[0]);
        this.minutes = this.getIntMinutes(numbers[1]);
        this.measurement = Measurement.DATE;

        if(this.isAfterNoon(time) && this.hour < 12) { // Turn it into Military Time
            this.hour = this.hour + 12;
            if((this.hour == 24) && (this.minutes > 0)) {
                this.hour = 0;
            }
        }
    }

    /**
     * Get the time represented by this AFRSTime.
     * @return int time represented in minutes.
     */
    public int inMinutes() {
        return (this.hour * 60) + this.minutes;
    }

    /**
     * Calculate the duration between the two times. It is assumed that if the arrival time
     * is earlier than the departure time they exist in two separate adjacent days.
     * @param start AFRSTime starting time.
     * @param end AFRSTime ending time.
     * @return AFRSTime representing the time that has passed.
     */
    public static AFRSTime timeBetween(AFRSTime start, AFRSTime end) {
        AFRSTime duration;

        if(start.inMinutes() < end.inMinutes()) { // Same Day
            duration = new AFRSTime(Measurement.DURATION, 0 , end.inMinutes() - start.inMinutes());
        }
        else {
            int totalMinutes = 1440 - start.inMinutes() + end.inMinutes();
            duration = new AFRSTime(Measurement.DURATION, 0, totalMinutes); // Different Days
        }

        duration.minuteToHours();
        return duration;
    }

    /**
     * Add a duration to this AFRSTime. If this AFRSTime is not a Duration, then
     * nothing will be added.
     * @param duration AFRSTime to be added. If this is not a Duration nothing will be added.
     */
    public void addDuration(AFRSTime duration) {
        if(duration.measurement == Measurement.DURATION) {
            this.hour += duration.hour;
            this.minutes += duration.minutes;
            this.minuteToHours();
        }
        else {
            System.out.println("Trying to add a Time (Date) to a Time (Duration).");
        }
    }

    /**
     * Determine if there is enough time between the arrival time and the depart time. If the depart time is earlier
     * than the arrival time it is assumed to be the next day. It is expected that the arrival time is the arrival time
     * of one Flight and the departing time is the departure time of another Flight.
     * @param arrival AFRSTime starting time.
     * @param inBetween AFRSTime that must exist between arrival and depart.
     * @param depart AFRSTime ending time.
     * @return True if there is enough time; False otherwise.
     */
    public static boolean isValid(AFRSTime arrival, AFRSTime inBetween, AFRSTime depart) {
        AFRSTime available = timeBetween(arrival, depart);
        if(available.inMinutes() >= inBetween.inMinutes()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Create and return a String representing the AFRSTime in AMPM time.
     * @return String representing the AFRSTime.
     */
    public String toAmPmString() {
        int hour = this.hour;
        String timeOfDay = "a";
        if(hour == 24){
            hour = 12;
        }
        else if(hour == 12){
            timeOfDay = "p";
        }
        else if(hour > 12 && hour < 24){
            hour -= 12;
            timeOfDay = "p";
        }
        String currentHour = Integer.toString(hour);
        String minutes = Integer.toString(this.minutes);
        String time = currentHour + ":" + minutes + timeOfDay;
        return time;
    }

    @Override
    public String toString() {
        return Integer.toString(inMinutes());
    }
}
