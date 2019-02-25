package Controller.Factories;

import Model.AFRSTime;
import Model.Airport;
import Model.Databases.AirportDatabase.AirportDatabase;
import Model.Databases.Database;
import Model.Databases.FlightDatabase;
import Model.ReservationHierarchy.Flight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Creates a FlightDatabase using the set text file. This Factory relies on the
 * AirportDatabase. If the AirportDatabase is not instantiated then this Object
 * will not properly instantiate the FlightDatabase.
 *
 * @author Elijah Cantella - edc8230@rit.edu
 */
public class FlightDatabaseFactory implements DatabaseFactory {

    // ----------
    // Attributes
    // ----------

    // Flights
    private String flightFileName = "flights.txt";
    private int originAirportIndex = 0;
    private int destinationAirportIndex = 1;
    private int departureIndex = 2;
    private int arrivalIndex = 3;
    private int flightNumberIndex = 4;
    private int airfareIndex = 5;

    private AirportDatabase airportDatabase;

    // Database that is Made
    private FlightDatabase database;

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

                if (filename.equals(this.flightFileName)) {
                    this.addFlight(elements);
                }
            }

            fileReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a Flight to a FlightDatabase. It is expected that the Airports exist within the AirportDatabase.
     * Adds the Flight to the origin Airport, so that the Airport knows all its outgoing Flights.
     * @param elements String[] where [0] is the origin Airport code, [1] is the destination
     *                 Airport code, [2] is the departure time, [3] is the arrival time,
     *                 [4] is the Flight number, [5] is the airfare.
     */
    private void addFlight(String[] elements) {
        // Get the Airports
        Airport origin = this.airportDatabase.getAirport(elements[this.originAirportIndex]);
        Airport destination = this.airportDatabase.getAirport(elements[this.destinationAirportIndex]);

        // Create the Times
        AFRSTime departure = new AFRSTime(elements[this.departureIndex]);
        AFRSTime arrival = new AFRSTime(elements[this.arrivalIndex]);

        int flightNumber = Integer.parseInt(elements[this.flightNumberIndex]);
        float airfare = Integer.parseInt(elements[this.airfareIndex]);

        // Create the Flight
        Flight f = new Flight(origin, destination, departure, arrival, airfare, flightNumber);
        origin.addFlight(f);
        this.database.addFlight(f);
    }

    /**
     * Create a new FlightDatabaseFactory. It is able to create a FlightDatabase.
     * @param airportDatabase
     */
    public FlightDatabaseFactory(AirportDatabase airportDatabase) {
        this.airportDatabase = airportDatabase;
        this.database = new FlightDatabase();
    }

    /**
     * Make and return a FlightDatabase.
     * @return Database that is instantiated FlightDatabase.
     */
    @Override
    public Database makeDatabase() {
        this.readIn(this.flightFileName);
        this.airportDatabase.toggleActive();
        this.readIn(this.flightFileName);
        this.airportDatabase.toggleActive();
        return this.database;
    }
}
