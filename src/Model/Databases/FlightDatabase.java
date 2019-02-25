package Model.Databases;

import Model.ReservationHierarchy.Flight;
import java.util.ArrayList;
import java.util.Collection;

/**
 * All the possible Flights within the AFRS system.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */
public class FlightDatabase implements Database {

    // ----------
    // Attributes
    // ----------

    private Collection<Flight> flights;

    // -------
    // Methods
    // -------

    /**
     * Create a new FlightDatabase.
     */
    public FlightDatabase(){
        this.flights = new ArrayList<>();
    }

    /**
     * Add a Flight to this FlightDatabase.
     * @param flight Flight to be added to this FlightDatabase.
     */
    public void addFlight(Flight flight){
        if(!this.flights.contains(flight)) {
            flights.add(flight);
        }
    }

    /**
     * Get a Flight when provided with its corresponding Flight number.
     * @param flightNumber int Flight number.
     * @return Flight corresponding to the Flight number.
     */
    public Flight getFlight(Integer flightNumber){
        for(Flight f : this.flights) {
            if(f.getFlightNumber() == flightNumber) {
                return f;
            }
        }
        return null;
    }
}
