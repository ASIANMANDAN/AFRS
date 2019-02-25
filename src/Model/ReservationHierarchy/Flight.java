/**
 * A flight connects two airports unidirectionaly. A flight has an associated
 * departure/arrival times, a cost (airfare), and a flight number. Can't have
 * any children within the composite structure.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */

package Model.ReservationHierarchy;

import Model.Airport;
import Model.AFRSTime;
import Model.Databases.DatabaseItem;

public class Flight extends DatabaseItem implements FlightChildren  {

    // ----------
    // Attributes
    // ----------

    private Airport origin;
    private Airport destination;
    private AFRSTime departure;
    private AFRSTime arrival;
    private float airfare;
    private int flightNumber;

    // -------
    // Methods
    // -------

    /**
     * Create a new Flight between two Airports.
     * @param origin Airport that this flight is coming from.
     * @param destination Airport that this flight is leading to.
     * @param departure Time this flight leaves the origin Airport.
     * @param arrival Time this flight arrives at the destination Airport.
     * @param airfare Cost of this flight.
     * @param flightNumber Number associated with this flight.
     */
    public Flight(Airport origin, Airport destination, AFRSTime departure, AFRSTime arrival,
                  float airfare, int flightNumber) {
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.airfare = airfare;
        this.flightNumber = flightNumber;

    }

    /**
     * Get the cost of this flight.
     * @return Cost of the flight down to pennies.
     */
    public float getAirfare() {
        return this.airfare;
    }

    /**
     * Get the origin Airport.
     * @return Airport from where this flight is coming from.
     */
    public Airport getOrigin() {
        return this.origin;
    }

    /**
     * Get the destination Airport.
     * @return Airport that this flight is leading to.
     */
    public Airport getDestination() {
        return this.destination;
    }

    /**
     * Will not return any children.
     * @param i the expected index of the child.
     * @return null
     */
    public FlightChildren getChild(int i) {
        return null;
    }

    /**
     * Get the travel time of this Flight.
     * @return AFRSTime representing transpired time.
     */
    public AFRSTime duration() {
        return AFRSTime.timeBetween(this.departure, this.arrival);
    }

    public int getFlightNumber() {
        return this.flightNumber;
    }

    /**
     * Get the departure time.
     * @return AFRSTime when this Flight leaves.
     */
    public AFRSTime getDeparture() {
        return this.departure;
    }

    /**
     * Get the arrival time.
     * @return AFRSTime when when this Flight arrives.
     */
    public AFRSTime getArrival() {
        return this.arrival;
    }

    /**
     * Compares this Flight with an Object to see if they are the same. A Flight
     * is equal to another Flight if they have the same Flight number.
     * @param o Object to be compared to.
     * @return True if this Flight is equal to the Object; False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Flight)) { // Is the Object a Flight
            return false;
        }
        else if(this.flightNumber != ((Flight) o).flightNumber) { // Do they have the same Flight #
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Create a String to represent the Flight.
     * @return String representing the Flight.
     */
    @Override
    public String toString() {
        return String.valueOf(this.flightNumber);
    }
}
