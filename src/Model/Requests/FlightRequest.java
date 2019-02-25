package Model.Requests;

import Controller.Sort.AirfareSort;
import Controller.Sort.ArrivalSort;
import Controller.Sort.DepartureSort;
import Model.Airport;
import Model.Databases.AirportDatabase.AirportDatabase;
import Model.Databases.ItineraryDatabase;
import Model.ReservationHierarchy.Itinerary;

import java.util.ArrayList;

/**
 * This class represents a request for any itinerary between two airports. Users can specify the number of connecting
 * flights (if any) that they would like in the itineraries. The number of connections can be 0, 1, or 2. Itineraries
 * can also be sorted by departure time, arrival time, or airfare. By default, they are sorted by departure time.
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class FlightRequest implements Request {

    private ArrayList<String> requestInfo;
    private AirportDatabase airports;
    private ItineraryDatabase itineraryDatabase;

    public FlightRequest(ArrayList<String> request, AirportDatabase airports, ItineraryDatabase itineraries){
        this.requestInfo = request;
        this.airports = airports;
        this.itineraryDatabase = itineraries;
    }

    /**
     * This methods finds itineraries that fit the input requirements and returns them in an array list.
     * An error message can also be returned if any of the given information is incorrect.
     *
     * @return an array list of itineraries or an error message.
     */
    @Override
    public ArrayList execute(){
        ArrayList<ArrayList> results = new ArrayList<>();
        ArrayList<String> message = new ArrayList<>();
        results.add(message);

        if(requestInfo.size() < 3 || requestInfo.size() > 5){
            message.add("error");
            message.add("incorrect number of fields");
            return results;
        }

        String origin = requestInfo.get(1);
        String destination = requestInfo.get(2);

        //Check the origin airport is valid
        if(!airports.hasAirport(origin)){
            message.add("error");
            message.add("unknown origin");
            return results;
        }
        //Check the destination airport is valid
        else if(!airports.hasAirport(destination)){
            message.add("error");
            message.add("unknown destination");
            return results;
        }
        //Check the connection limit (if entered) is valid
        else if(requestInfo.size() > 3 && !requestInfo.get(3).equals("")){
            if(Integer.parseInt(requestInfo.get(3)) > 2 || Integer.parseInt(requestInfo.get(3)) < 0){
                message.add("error");
                message.add("invalid connection limit");
                return results;
            }
        }
        //Check the sort order (if entered) is valid
        else if(requestInfo.size() == 5){
            String order = requestInfo.get(4);
            if(!order.equals("departure") && !order.equals("arrival") && !order.equals("airfare")){
                message.add("error");
                message.add("invalid sort order");
                return results;
            }
        }
        Airport originAirport = airports.getAirport(origin);
        Airport destinationAirport = airports.getAirport(destination);
        ArrayList<Itinerary> itinerary;

        if(requestInfo.size() == 3){
            itinerary = itineraryDatabase.findItineraries(originAirport, destinationAirport, 0);
        }
        else if(requestInfo.size() == 5 && requestInfo.get(3).equals("")){
            itinerary = itineraryDatabase.findItineraries(originAirport, destinationAirport, 0);
        }
        else{
            itinerary = itineraryDatabase.findItineraries(originAirport, destinationAirport,
                    Integer.parseInt(requestInfo.get(3)));
        }

        //Sort itineraries by airfare
        if(requestInfo.size() == 5 && requestInfo.get(4).equals("airfare")){
            AirfareSort airfareSort = new AirfareSort(itinerary);
            ArrayList<Itinerary> sortedItinerary = airfareSort.sort();
            results.add(sortedItinerary);
            message.add("info");
            message.add(Integer.toString(sortedItinerary.size()));
            if(sortedItinerary.size() == 0){
                return results;
            }
            else {
                int x = 1;
                for (Itinerary i : sortedItinerary) {
                    message.add("\n" + Integer.toString(x));
                    message.add(i.toString());
                    x++;
                }
            }
        }
        //Sort itineraries by arrival time
        else if(requestInfo.size() == 5 && requestInfo.get(4).equals("arrival")){
            ArrivalSort arrivalSort = new ArrivalSort(itinerary);
            ArrayList<Itinerary> sortedItinerary = arrivalSort.sort();
            results.add(sortedItinerary);
            message.add("info");
            message.add(Integer.toString(sortedItinerary.size()));
            if(sortedItinerary.size() == 0){
                return results;
            }
            else {
                int x = 1;
                for (Itinerary i : sortedItinerary) {
                    message.add("\n" + Integer.toString(x));
                    message.add(i.toString());
                    x++;
                }
            }
        }
        //Sort itineraries by departure time
        else{
            DepartureSort departureSort = new DepartureSort(itinerary);
            ArrayList<Itinerary> sortedItinerary = departureSort.sort();
            results.add(sortedItinerary);
            message.add("info");
            message.add(Integer.toString(sortedItinerary.size()));
            if(sortedItinerary.size() == 0){
                return results;
            }
            else {
                int x = 1;
                for (Itinerary i : sortedItinerary) {
                    message.add("\n" + Integer.toString(x));
                    message.add(i.toString());
                    x++;
                }
            }
        }
        return results;
    }

    /**
     * Set the Flight Request info.
     * @param requestInfo ArrayList of Strings representing Request info.
     */
    @Override
    public void setRequestInfo(ArrayList<String> requestInfo) {
        this.requestInfo = requestInfo;
    }
}
