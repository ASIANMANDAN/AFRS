package Model.Requests;

import Model.Airport;
import Model.Databases.AirportDatabase.AirportDatabase;

import java.util.ArrayList;

/**
 * This class represents a request for information about a specified Airport
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class AirportRequest implements Request {

    private ArrayList<String> requestInfo;
    private AirportDatabase airports;
    private int weatheridx;

    public AirportRequest(ArrayList request, AirportDatabase database, int weatheridx){
        this.requestInfo = request;
        this.airports = database;
        this.weatheridx = weatheridx;
    }

    /**
     * Gets the current information about a specified airport. If the airport code is incorrect, gives an error message.
     *
     * @return An array list of the airport's information or an error message
     */
    public ArrayList execute() {
        ArrayList<String> info = new ArrayList<>();

        if(requestInfo.size() != 2){
            info.add("error");
            info.add("incorrect number of fields");
            return info;
        }

        String code = requestInfo.get(1);

        //Check if the airport code exists
        if(airports.hasAirport(code)){
            Airport airport = airports.getAirport(code);
            info.add("airport");
            info.add(airport.getCity());
            info.add(airport.getWeather(this.weatheridx));
            info.add(airport.getTemperature(this.weatheridx));
            info.add(airport.getDelay().toString());
            return info;
        }
        else{
            info.add("error");
            info.add("unknown airport");
            return info;
        }
    }

    /**
     * Set the Airport Request info.
     * @param requestInfo ArrayList of Strings representing Request info.
     */
    @Override
    public void setRequestInfo(ArrayList<String> requestInfo) {
        this.requestInfo = requestInfo;
    }


}
