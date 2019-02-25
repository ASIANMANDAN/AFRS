package Model.Databases.AirportDatabase;

import Model.AFRSTime;
import Model.Airport;
import Model.Databases.Database;

import java.util.Collection;
import java.util.HashMap;

/**
 * A database of all the active currently in the AFRS
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class AirportDatabase implements Database {

    private HashMap<String, Airport> active;
    private HashMap<String, Airport> offlineAirports;
    private HashMap<String, Airport> onlineAirports;
    private InternetConnection state;

    public AirportDatabase(){
        this.onlineAirports = new HashMap<>();
        this.offlineAirports = new HashMap<>();
        this.state = new Offline(this);
        this.active = offlineAirports;
    }

    /**
     * Add an airport to the current database of airports
     * @param code String code of the Airport.
     * @param airport
     */
    public void addAirport(String code, Airport airport){
        this.active.put(code, airport);
    }

    /**
     * Add a weather condition to the list of weather conditions an aiport can have
     * @param code the airport to add a condition to
     * @param weather the weather condition
     */
    public void addWeather(String code, String weather){
        this.active.get(code).addWeather(weather);
    }

    /**
     * Add a current temperature of the airport
     * @param code the airport to add a temperature to
     * @param temp the temperature
     */
    public void addTemperature(String code, String temp){
        this.active.get(code).addTemperature(temp);
    }

    /**
     * Set the current delay for a given airport
     * @param code the airport to set the delay for
     * @param temp the delay time
     */
    public void addDelay(String code, String temp) {
        this.active.get(code).setDelay(new AFRSTime(AFRSTime.Measurement.DURATION, 0, Integer.parseInt(temp)));
    }

    /**
     * Set the current minimum connection time
     * @param code The airport to set the connection time for
     * @param time the connection time to be set
     */
    public void setConnectionTime(String code, AFRSTime time){
        this.active.get(code).setMinConnectionTime(time);
    }

    /**
     * Get an airport from the current database using a three letter airport code
     * @param code the airport code of the desired airport
     * @return the desired airport
     */
    public Airport getAirport(String code){
        return this.active.get(code);
    }

    /**
     * Check that the active database has the input airport
     * @param code The airport to look for
     * @return a boolean telling whether or not the database has the desired airport
     */
    public boolean hasAirport(String code){
        return this.active.containsKey(code);
    }

    /**
     * Get the active database as a collection
     * @return the active HashMap as a collection
     */
    public Collection<Airport> ToCollection(){
        return this.active.values();
    }

    /**
     * Switch between the FAA database and the local database
     */
    public void toggleActive() {
        this.state.toggle(this.onlineAirports, this.offlineAirports);
    }

    /**
     * Change the internet connection being used
     * @param state the new connection to switch to
     * @param newActive the corresponding database to use
     */
    protected void setActive(InternetConnection state, HashMap<String, Airport> newActive) {
        this.state = state;
        this.active = newActive;
    }

    /**
     * Check which internet connection is being used
     * @return a string containing the current internet connection status
     */
    public String getActive(){
        return state.getState();
    }
}
