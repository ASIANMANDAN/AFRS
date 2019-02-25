package Controller.Factories;

import Controller.ReservationSaver;
import Model.Databases.Database;
import Model.Databases.FlightDatabase;
import Model.Databases.ReservationDatabase;
import Model.ReservationHierarchy.Itinerary;
import Model.ReservationHierarchy.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Creates a ReservationDatabase using the reservation text file. This Factory only
 * relies on the FlightDatabase. If the FlightDatabase is not populated then the
 * ReservationDatabase will not be instantiated. Sets up the ReservationDatabase to
 * be observed by ReservationSaver.
 *
 * @author Elijah Cantella - edc8230@rit.edu
 */
public class ReservationDatabaseFactory implements DatabaseFactory {

    // Reservations
    private String reservationFileName = "reservations.txt";
    private int firstFlightIndex = 1;
    private int flightIndexDisplacement = 7;

    // Database to be Created
    private ReservationDatabase database;

    private FlightDatabase flightDatabase;

    /**
     * Add a Reservation to the ReservationDatabase.
     * @param elements String[] with parsed elements.
     */
    private void addReservation(String[] elements) {
        String passengerName = elements[0];
        int i = this.firstFlightIndex; // Index of the First Flight
        Itinerary itinerary = new Itinerary();
        while(i < elements.length) { // Get all the Flights
            itinerary.addFlight(this.flightDatabase.getFlight(Integer.parseInt(elements[i])));
            i += this.flightIndexDisplacement;
        }
        Reservation r = new Reservation(passengerName, itinerary);
        this.database.addReservations(r);
    }

    /**
     * Main method for populating a Database. Opens the specified filename and iterates through the lines
     * to populate the corresponding Database.
     * @param filename String filename of the file to be opened.
     */
    private void readIn(String filename) {
        String line;
        String[] elements;

        try {
            FileReader fileReader = new FileReader("src/Input/" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {

                elements = line.split(",");

                if (filename.equals(this.reservationFileName)) {
                    this.addReservation(elements);
                }
            }

            fileReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new ReservationDatabaseFactory. This Object will create a ReservationDatabase and
     * populate it with the information from the set text file. Sets up the ReservationDatabase to
     * be saved by the ReservationSaver.
     * @param flightDatabase FlightDatabase used to access Flights.
     */
    public ReservationDatabaseFactory(FlightDatabase flightDatabase) {
        this.flightDatabase = flightDatabase;
        this.database = new ReservationDatabase();
        this.database.addObserver(new ReservationSaver(this.reservationFileName));
    }

    /**
     * Make a Database, in this case a ReservationDatabase using the set text files.
     * @return ReservationDatabase as a Database.
     */
    @Override
    public Database makeDatabase() {
        this.readIn(this.reservationFileName);
        return this.database;
    }
}
