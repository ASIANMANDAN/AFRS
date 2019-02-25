/**
 * Holds all the Databases within the AFRS system. Does not alter the Databases in any way.
 * Ideally prevents the instantiation of individual Databases, which could result in
 * miss communication between classes.
 *
 * @author Elijah Cantella - edc8230@g.rit.edu
 */

package Model.Databases;

import Controller.AllDatabasePopulator;
import Model.Databases.AirportDatabase.AirportDatabase;


public final class AllDatabases {

    // ----------
    // Attributes
    // ----------

    private AirportDatabase airportDatabase;
    private ReservationDatabase reservationDatabase;
    private FlightDatabase flightDatabase;
    private ItineraryDatabase itineraryDatabase;

    private AllDatabasePopulator populator;

    // -------
    // Methods
    // -------

    /**
     * Create AllDatabases within the AFRS system.
     */
    public AllDatabases() {
        this.airportDatabase = new AirportDatabase();
        this.reservationDatabase = new ReservationDatabase();
        this.flightDatabase = new FlightDatabase();
        this.itineraryDatabase = new ItineraryDatabase();


        this.populator = new AllDatabasePopulator(this);
        this.populator.makeAllDatabases();
    }

    /**
     * Get the AirportDatabase.
     * @return AirportDatabase.
     */
    public AirportDatabase getAirportDatabase() {
        return this.airportDatabase;
    }

    /**
     * Get the ReservationDatabase.
     * @return ReservationDatabase.
     */
    public ReservationDatabase getReservationDatabase() {
        return this.reservationDatabase;
    }

    /**
     * Get the FlightDatabase.
     * @return FlightDatabase.
     */
    public FlightDatabase getFlightDatabase() {
        return this.flightDatabase;
    }

    /**
     * Get the ItineraryDatabase.
     * @return ItineraryDatabase.
     */
    public ItineraryDatabase getItineraryDatabase() {
        return this.itineraryDatabase;
    }

    public void updateDatabase(Database database) {
        if(database instanceof AirportDatabase) {
            this.airportDatabase = (AirportDatabase) database;
        }
        else if(database instanceof ReservationDatabase) {
            this.reservationDatabase = (ReservationDatabase) database;
        }
        else if(database instanceof FlightDatabase) {
            this.flightDatabase = (FlightDatabase) database;
        }
        else if(database instanceof ItineraryDatabase) {
            this.itineraryDatabase = (ItineraryDatabase) database;
        }
    }
}
