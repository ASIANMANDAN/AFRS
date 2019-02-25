package Controller;

import Model.ReservationHierarchy.Reservation;
import Model.Databases.ReservationDatabase;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/**
 * Saves the Reservations in a Reservation Database. Stores the information into a CSV file with the format of:
 *  [PassengerName],[f1],[f2],[f3] where [f1,f2,f3] are flights. There needs to be at least one flight per
 *  Reservation. Only responsible for storing information when notified, does not read from a file.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */
public class ReservationSaver implements Observer {

    // ----------
    // Attributes
    // ----------

    String reservationFileName;

    // -------
    // Methods
    // -------

    /**
     * Create a Collection of Strings to represent the provided ReservationDatabase.
     * @param database ReservationDatabase that is being represented by a Collection of Strings.
     * @return Collection Strings in the format [PassengerName],[f1],[f2],[f3]
     */
    private Collection<String> createStringReservations(ReservationDatabase database) {
        Collection<String> reservations = new ArrayList<>();
        for(Reservation r : database.getAllReservations()) {
            reservations.add(r.toString());
        }
        return reservations;
    }

    /**
     * Create a new ReservationSaver.
     */
    public ReservationSaver(String reservationFileName) {
        this.reservationFileName = reservationFileName;
    }

    /**
     * Save the notified Observable object if that object is a ReservationDatabase.
     * @param o Observable object notifying this Observer.
     * @param arg Argument passed by the Observable; not needed in this instance.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ReservationDatabase) { // Is this Observable a ReservationDatabase
            try(PrintWriter out = new PrintWriter("src/Input/" + this.reservationFileName)) { // Try Opening to a File
                Collection<String> reservations = this.createStringReservations((ReservationDatabase)o);
                for(String s : reservations) { // Print out the Reservations to the File
                    out.println(s);
                }
                out.close();
            }
            catch(Exception e) {
                System.out.println("'reservations.txt' not found.");
            }
        }
    }
}
