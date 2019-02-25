package Controller;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Retrieves and sanitizes initial input from the user.
 *
 * @author Lindsey Ferretti - ljf6974@rit.edu
 */
public class Parser {

	/**
	 * Parser constructor.
	 */
	public Parser() {}

	/**
	 * Retrieves a lines of input from the user until a semicolon is encountered.
	 * @return a String containing one full request.
	 */
	public String getLine() {
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();

		if(line.equals("q")) {							// q to quit
			return line;
		}

		while (!line.contains(";")) {                   // continues adding commands until ';' encountered
			line += scanner.nextLine();
		}
		return line;
	}

	/**
	 * Parses a full command from the user. Removes whitespace and trailing semicolon.
	 * @param line a String containing one full request.
	 * @return an ArrayList with elements adding up to one full request.
	 */
	public ArrayList<String> parseLine(String line) {
		ArrayList<String> request = new ArrayList<>();

			String[] temp = line.split(",");            // splits string on commas

			for (String str : temp) {                        // adds every element of list to array list request
				request.add(str.trim().replace(";", ""));            // removes extra whitespace between elements
			}
			return request;

	}
}
