package Model.Databases;

import Model.Airport;
import Model.ReservationHierarchy.Itinerary;
import java.util.ArrayList;
import java.util.Collection;

/**
 * All the possible Itineraries within the AFRS system.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */
public class ItineraryDatabase implements Database{

    // ----------
    // Attributes
    // ----------

    private Collection<Itinerary> itineraries;

    // -------
    // Methods
    // -------

    /**
     * Create a new ItineraryDatabase.
     */
    public ItineraryDatabase(){
        this.itineraries = new ArrayList<>();
    }

    /**
     * Using the ItineraryGenerator, populate this ItineraryDatabase
     * with all possible Itineraries between various Airports.
     * @param itinerary Itineraries
     */
    public void addItinerary(Itinerary itinerary) {
        this.itineraries.add(itinerary);
    }

    /**
     * Find Itineraries that lead from an origin Airport to a destination Airport.
     * @param origin Airport the Itinerary leaves from.
     * @param destination Airport the Itinerary leads to.
     * @param connections number of connecting flights
     * @return Arraylist Itineraries that are valid.
     */
    public ArrayList<Itinerary> findItineraries(Airport origin, Airport destination, int connections){
        ArrayList<Itinerary> results = new ArrayList<>();

        for(Itinerary i : itineraries){ // For all Itineraries
            if(i.getOrigin().equals(origin) && i.getDestination().equals(destination) &&
                    i.getFlightSize() == connections + 1){
                results.add(i);
            }
        }
        return results;
    }
}
