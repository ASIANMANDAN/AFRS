/**
 * Represents a reserved Itinerary. A Reservation is a bought/saved Itinerary that is
 * associated with a passenger. Reservations are stored within a database. A passenger
 * may not reserve the same Itinerary they already have reserved.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
*/

package Model.ReservationHierarchy;

import Model.Airport;
import Model.AFRSTime;
import Model.Databases.DatabaseItem;

public class Reservation extends DatabaseItem implements FlightChildren {

    // ----------
    // Attributes
    // ----------

    private String passengerName;
    private Itinerary itinerary;

    // -------
    // Methods
    // -------

    /**
     * Create a new Reservation. A Reservation behaves like an Itinerary and is
     * associated with a passenger.
     * @param passengerName String name of the passenger reserving the Itinerary.
     * @param itinerary Itinerary that is being reserved.
     */
    public Reservation(String passengerName, Itinerary itinerary) {
        this.passengerName = passengerName;
        this.itinerary = itinerary;
    }

    /**
     * Get the airfare or total cost of this Reservation.
     * @return
     */
    public float getAirfare() {
        return this.itinerary.getAirfare();
    }

    /**
     * Get the first Airport that begins the Reservation.
     * @return Airport that begins the Reservation.
     */
    public Airport getOrigin() {
        return this.itinerary.getOrigin();
    }

    /**
     * Get the final destination Airport of the Reservation.
     * @return Airport that the Reservation leads to.
     */
    public Airport getDestination() {
        return this.itinerary.getDestination();
    }

    /**
     * Get the Itinerary that this Reservation is reserving.
     * @param i int not used to get Itinerary.
     * @return FlightChildren Itinerary that is reserved.
     */
    public FlightChildren getChild(int i) {
        return this.itinerary;
    }

    /**
     * Get the combined travel time of all the Flights within this Reservation.
     * @return AFRSTime representing the duration of this Reservation.
     */
    public AFRSTime duration() {
        return this.itinerary.duration();
    }

    /**
     * Get the name of the passenger this Reservation is for.
     * @return String name of the passenger.
     */
    public String getPassengerName() {
        return this.passengerName;
    }

    /**
     * Compares this Reservation with an Object to see if they are the same. A Reservation
     * is equal to another Reservation if they have the same passenger and Itinerary.
     * @param o Object to be compared to.
     * @return True if this Reservation is equal to the Object; False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Reservation)) { // Is the Object a Reservation
            return false;
        }
        else if(this.passengerName.equals(((Reservation) o).passengerName) && // Does this Reservation and the
                this.itinerary.equals(((Reservation) o).getChild(0))) {    // Argument have the same passenger
            return true;                                                      // and Itinerary
        }
        return false;
    }

    /**
     * Create a String to represent the Reservation.
     * @return String representing the Reservation.
     */
    @Override
    public String toString() {
        return this.passengerName + "," + this.itinerary.toString();
    }
}
