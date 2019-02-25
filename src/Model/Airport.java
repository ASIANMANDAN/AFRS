package Model;

import Model.Databases.DatabaseItem;
import Model.ReservationHierarchy.Flight;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents an Airport in the AFRS system.
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class Airport extends DatabaseItem {

    private ArrayList<String> weather;
    private ArrayList<String> temperature;
    private ArrayList<Flight> flights;
    private String city;
    private String name;
    private AFRSTime minConnectionTime;
    private AFRSTime delay;

    public Airport(String name, String city){
        this.city = city;
        this.name = name;
        this.weather = new ArrayList();
        this.temperature = new ArrayList();
        this.flights = new ArrayList<>();
    }

    /**
     * Associate a flight to an airport if the airport is the flight's origin
     * @param flight the flight to be added to the airport
     */
    public void addFlight(Flight flight) {
        if(this.equals(flight.getOrigin())) {
            this.flights.add(flight);
        }
    }

    public Collection<Flight> getFlights() {
        return this.flights;
    }

    /**
     * Add a weather condition that the airport can have
     * @param condition the weather condition to add
     */
    public void addWeather(String condition){
        weather.add(condition);
    }

    /**
     * Add a temperature that the airport can be
     * @param temp the temperature to add
     */
    public void addTemperature(String temp){
        temperature.add(temp);
    }

    public void setMinConnectionTime(AFRSTime time){
        this.minConnectionTime = time;
    }

    public void setDelay(AFRSTime delay) {
        this.delay = delay;
    }

    /**
     * Get the current weather condition. The next time this method is called, the weather will change.
     * @param idx int
     * @return the weather condition
     */
    public String getWeather(int idx) {
        return weather.get(idx % weather.size());
    }

    /**
     * Get the current temperature. The next time this method is called, the temperature will have changed.
     * @return the current temperature
     */
    public String getTemperature(int idx) {
        return temperature.get(idx % temperature.size());
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public AFRSTime getMinConnectionTime() {
        return minConnectionTime;
    }

    public AFRSTime getDelay() {
        return this.delay;
    }

    /**
     * Check if this Airport and another object are equal
     * @param o The object to be compared to
     * @return True if the objects are equal, False if not
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Airport)) {
            return false;
        }
        Airport x = (Airport) o;
        if(this.name.equals(x.name)) {
            return true;
        }
        else {
            return false;
        }
    }
}
