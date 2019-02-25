/**
 * Has a listing of flights that come from an origin and leads to a destination airport.
 * All methods interact with Flights in some way. Itineraries may be viewed, but must be
 * reserved with a passenger to be used. An Itinerary must have at least 1 Flight but
 * not more than 3.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */

package Model.ReservationHierarchy;

import Model.Airport;
import Model.AFRSTime;
import Model.Databases.DatabaseItem;

import java.util.ArrayList;
import java.util.List;

public class Itinerary extends DatabaseItem implements FlightChildren {

    // ----------
    // Attributes
    // ----------

    private List<Flight> flights; // Flights Expected to be Ordered as they are
                                  // to be Taken
    // -------
    // Methods
    // -------

    /**
     * Create a new empty Itinerary.
     */
    public Itinerary() {
        this.flights = new ArrayList<>();
    }

    public int getFlightSize(){
        return this.flights.size();
    }

    /**
     * Get the airfare or total cost of this Itinerary.
     * @return float cost of the flight down to pennies.
     */
    public float getAirfare() {
        float airfare = 0;
        for(Flight f : this.flights) {
            airfare += f.getAirfare();
        }
        return airfare;
    }

    /**
     * Get the first Airport that begins the Itinerary.
     * @return Airport that begins the Itinerary.
     */
    public Airport getOrigin() {
        if(this.flights.size() <= 0) {
            return null;
        }
        return this.flights.get(0).getOrigin();
    }

    /**
     * Get the final destination Airport of the Itinerary.
     * @return Airport that the Itinerary leads to.
     */
    public Airport getDestination() {
        if(this.flights.size() <= 0) {
            return null;
        }
        int index = this.flights.size() - 1;
        return this.flights.get(index).getDestination();
    }

    /**
     * Get the Flight at the provided index. The index can be in the reverse order (working from the back).
     * @param i int index of the desired Flight.
     * @return Flight at the provided index; null if the index is out of bounds.
     */
    public Flight getChild(int i) {
        if(i < this.flights.size() && i >= 0) {
            return this.flights.get(i);
        }
        return null;
    }

    /**
     * Determine if this Itinerary is full and can't accept more Flights. An Itinerary is full
     * once it has three Flights.
     * @return True if a Flight can't be added; False otherwise.
     */
    public boolean isFull() {
        return this.flights.size() == 3;
    }

    /**
     * Get the combined travel time of all the Flights within this Itinerary.
     * @return AFRSTime representing the total transpired time in this Itinerary.
     */
    public AFRSTime duration() {
        int minutes = 0;
        for(Flight f : this.flights) {
            minutes += f.duration().inMinutes();
        }
        return new AFRSTime(AFRSTime.Measurement.DURATION, 0, minutes);
    }

    /**
     * Adds a Flight to an Itinerary. Controls the number of Flights allowed within an Itinerary. A Flight will
     * not be added if an this Itinerary already as three Flights.
     * @param flight Flight to be added to the end of the Itinerary.
     */
    public void addFlight(Flight flight) {
        if(!this.isFull()) {
            this.flights.add(flight);
        }
    }

    /**
     * Compares this Itinerary with an Object to see if they are the same. An Itinerary
     * is equal to another Itinerary if they have the same Flights.
     * @param o Object to be compared to.
     * @return True if this Itinerary is equal to the Object; False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Itinerary)) { // Is the Object an Itinerary
            return false;
        }

        int i = 0;
        FlightChildren child;
        while((child = this.getChild(i)) != null) { // Do the Itineraries have the same Flights?
            if(!(child.equals(((Itinerary) o).getChild(i)))) {
                return false;
            }
            i++;
        }

        if( ((Itinerary) o).getChild(i) != null) { // Does the Argument have an extra Flight?
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Create a String to represent the Itinerary.
     * @return String representing the Itinerary.
     */
    @Override
    public String toString(){
        String result = "";
        for(Flight f: this.flights) {
            result += f.toString() + ",";
        }
        return result.substring(0, result.length() - 1) + ", " + Integer.toString(flights.size()) + ", " +
                Float.toString(getAirfare()) + ", " + getOrigin().getName() + ", " +
                flights.get(0).getDeparture().toAmPmString() + ", " + getDestination().getName() + ", " +
                flights.get(flights.size() - 1).getArrival().toAmPmString();
    }
}
