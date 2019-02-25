package Controller;

import Model.Airport;
import Model.ReservationHierarchy.Flight;
import Model.ReservationHierarchy.Itinerary;
import Model.AFRSTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Generates a Collection of Itineraries for the ItineraryDatabase. Eliminates any invalid Itineraries based on
 * Flight and AFRSTime conflict.
 *
 * @author Elijah Cantella
 */
public class ItineraryGenerator {

    /**
     * Create a copy of the provided Itinerary. Adds Flights from one Itinerary into a new one.
     * It is possible to copy a blank Itinerary.
     * @param itinerary Itinerary to be copied.
     * @return Itinerary that has been copied.
     */
    private static Itinerary copyItinerary(Itinerary itinerary) {
        int i = 0; // Index of the Flight in Itinerary
        Flight f = itinerary.getChild(i); // First Flight in Itinerary
        Itinerary copy = new Itinerary();

        while(f != null) { // For Every Existing Flight
            copy.addFlight(f);
            i++;
            f = itinerary.getChild(i);
        }
        return copy;
    }

    /**
     * Create multiple copies of a provided Itinerary. These Itineraries are all equal
     * but are different instances.
     * @param itinerary Itinerary to be copied.
     * @param i int number of desired copies.
     * @return Collection of copied Itineraries.
     */
    private static Collection<Itinerary> makeCopies(Itinerary itinerary, int i) {
        Collection<Itinerary> copies = new ArrayList<>();
        while(i > 0) {
            copies.add(copyItinerary(itinerary));
            i--;
        }
        return copies;
    }

    /**
     * Determine the valid Flights for a given Itinerary. The provided Itinerary is expected to have
     * at least one existing Flight; without an existing Flight conflicts would be impossible.
     * @param itinerary Itinerary that led to an Airport.
     * @param flights Collection of outgoing Flights at said Airport.
     * @return Collection of valid Flights.
     */
    private static Collection<Flight> validFlights(Itinerary itinerary, Collection<Flight> flights) {
        Collection<Flight> valid = new ArrayList<>();
        int i = 0;
        while(itinerary.getChild(i) != null) {
            i++;
        }
        Flight lastFlight = itinerary.getChild(i - 1);

        if(lastFlight == null) { // This Itinerary did not Have a Previous Flight - All Flights Valid
            return null;
        }
        else {
            Airport airport = lastFlight.getDestination();

            // Associated Times in AFRSTime
            AFRSTime timeNeeded = airport.getMinConnectionTime();
            timeNeeded.addDuration(airport.getDelay());
            AFRSTime arriveAirport = lastFlight.getArrival();
            AFRSTime leaveAirport;

            for(Flight f : flights) { // For all Outgoing Flights
                leaveAirport = f.getDeparture();
                if(AFRSTime.isValid(arriveAirport, timeNeeded, leaveAirport)) {
                    valid.add(f);
                }
            }
            return valid;
        }
    }

    /**
     * Find all possible final Itineraries based on a provided Itinerary and an Airport. Expects there to be
     * at least one Flight within the Itinerary. Recursive function that adds to a Collection of Itineraries.
     * @param allPossible Collection of Itineraries to hold all the possible finalized Itineraries.
     * @param itinerary Itinerary that will be expanded upon.
     * @param airport Airport that should corresponds with the Itinerary, holds all the Flights.
     */
    private static void findJumps(Collection<Itinerary> allPossible, Itinerary itinerary, Airport airport) {
        if(itinerary.getDestination().equals(airport)) { // The Itinerary matches the Airport
            Collection<Flight> possibleOutGoing = validFlights(itinerary, airport.getFlights());

            // Make a Copy of the Itinerary for each Flight
            int copies = possibleOutGoing.size();
            Iterator itineraries = makeCopies(itinerary, copies).iterator();
            Iterator flights = possibleOutGoing.iterator();

            Itinerary newItinerary;
            Flight f;

            while(flights.hasNext()) { // Create a new Itinerary, Add it to the Possible Itineraries
                f = (Flight) flights.next();
                newItinerary = (Itinerary) itineraries.next();
                newItinerary.addFlight(f);
                allPossible.add(newItinerary);
                if(!newItinerary.isFull()) { // Recursive call if able to Expand
                    findJumps(allPossible, newItinerary, f.getDestination());
                }
            }
        }
    }

    /**
     * Create a Collection of Itineraries when provided a Collection of Airports.
     * @param airports Collection of Airports with Flights, allowing the creation of Itineraries.
     * @return Collection of all possible valid Itineraries.
     */
    public static Collection<Itinerary> generateItineraries(Collection<Airport> airports) {
        Collection<Itinerary> start = new ArrayList<>();
        Collection<Itinerary> allPossible = new ArrayList<>();
        for(Airport a : airports) {
            for(Flight f : a.getFlights()) {
                Itinerary i = new Itinerary();
                i.addFlight(f);
                start.add(i);
            }
        }
        for(Itinerary itinerary : start) {
            allPossible.add(itinerary);
            findJumps(allPossible, itinerary, itinerary.getDestination());
        }
        return allPossible;
    }
}
