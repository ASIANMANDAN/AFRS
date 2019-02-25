package Controller;

import Controller.Factories.*;
import Model.Databases.AllDatabases;
import Model.Databases.Database;


/**
 * Populates the AllDatabases Object and all of its Databases by using their corresponding
 * Factory Objects. Does not automatically populate the databases, an Object needs to call
 * a method to instantiate all the Databases.
 *
 * @author Lindsey Ferretti - ljf6974@rit.edu
 * @author Elijah Cantella - edc8230@g.rit.edu
 */
public class AllDatabasePopulator {


	// ----------
	// Attributes
	// ----------

	private AllDatabases allDatabases;

	private DatabaseFactory airportDatabaseFactory;
	private DatabaseFactory flightDatabaseFactory;
	private DatabaseFactory itineraryDatabaseFactory;
	private DatabaseFactory reservationDatabaseFactory;


	// -------
	// Methods
	// -------

	/**
	 * Create a new AllDatabasePopulator. This Object will populate the AllDatabases Object
	 * provided upon instantiation.
	 * @param allDatabases AllDatabases to be populated.
	 */
	public AllDatabasePopulator(AllDatabases allDatabases) {
		this.allDatabases = allDatabases;
	}

	/**
	 * Populates all the Databases when the Aggregated AllDatabases is ready to be populated.
	 * Population has a specific order as some Factories depend on some Databases already
	 * being populated.
	 */
	public void makeAllDatabases() {
		this.airportDatabaseFactory = new AirportDatabaseFactory();

		Database airportDatabase = this.airportDatabaseFactory.makeDatabase();
		allDatabases.updateDatabase(airportDatabase);

		this.flightDatabaseFactory = new FlightDatabaseFactory(allDatabases.getAirportDatabase());

		Database flightDatabase = this.flightDatabaseFactory.makeDatabase();
		allDatabases.updateDatabase(flightDatabase);

		this.itineraryDatabaseFactory = new ItineraryDatabaseFactory(allDatabases.getAirportDatabase());
		Database itineraryDatabase = this.itineraryDatabaseFactory.makeDatabase();
		allDatabases.updateDatabase(itineraryDatabase);

		this.reservationDatabaseFactory = new ReservationDatabaseFactory(allDatabases.getFlightDatabase());
		Database reservationDatabase = this.reservationDatabaseFactory.makeDatabase();
		allDatabases.updateDatabase(reservationDatabase);
	}
}
