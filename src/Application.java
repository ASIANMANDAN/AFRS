import Controller.AllDatabasePopulator;
import Controller.Parser;

import Controller.RequestHandler;
import Model.Client;
import Model.Databases.AllDatabases;
import Model.Requests.Request;
import View.GUI;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The entry point for the Airline Flight Reservation System (AFRS) application.
 *
 * @author Lindsey Ferretti - ljf6974@rit.edu
 */
public class Application {

	// ----------
	// Attributes
	// ----------


	/**
	 * Create and execute the request. Prints out AFRS system text for user. It is assumed that the input is from the
	 * input line.
	 * @param request Request Object that will be executed.
	 * @param input ArrayList of Strings that the Request will use.
	 */
	private static void passRequest(RequestHandler request, ArrayList<String> input, int idx) {
		request.setRequestInfo(input);
		ArrayList<String> result = request.execute(idx);
		if(result == null) {
			System.out.println("> This is not a valid command. Did you mean one of these?:");
			System.out.println("> info, reserve, retrieve, delete, airport");
		}
		else {
			System.out.println(result);
			System.out.println("\n> Enter another query below (\"q\" to quit):");
		}
	}

	public static void main(String[] args) {


		// Create and Populate the Databases
		AllDatabases allDatabases = new AllDatabases();

		String line;							// input from user
		Parser parser = new Parser();			// parser object to retrieve and sanitize input
		ArrayList<String> input;				// a full query in ArrayList format

		// Application Begins
		System.out.println("> Welcome to the Airline Flight Reservation Server(AFRS)");
		System.out.println("> Developed by Better Than United Airlines™\n");
		System.out.println("> Enter your query below (\"q\" to quit):");

		line = parser.getLine();	// retrieves input until ';' is found

		RequestHandler handler = new RequestHandler(allDatabases.getAirportDatabase(),
				allDatabases.getReservationDatabase(), allDatabases.getItineraryDatabase());
		Client c = new Client(0);
		handler.addClient(c);



		while(!line.equals("q")) {							// quits if user enters "q"

			input = parser.parseLine(line);				// returns listified query

			passRequest(handler, input, c.getId()); // Create and Execute the Request

			line = parser.getLine();						// retrieves the next line of input
			input.clear();								// empties the query ArrayList
		}
	}
}
