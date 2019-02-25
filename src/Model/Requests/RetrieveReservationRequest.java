package Model.Requests;

import Model.ReservationHierarchy.Reservation;
import Model.Databases.ReservationDatabase;

import java.util.ArrayList;

/**
 * This class represents a request for information about specified reservations. The reservations can be specified by
 * passenger name, origin airport, and destination airport.
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * ]
 * 
 *
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class RetrieveReservationRequest implements Request {

    private ArrayList<String> requestInfo;
    private ReservationDatabase reservations;

    public RetrieveReservationRequest(ArrayList request, ReservationDatabase reservations){
        this.requestInfo = request;
        this.reservations = reservations;
    }

    /**
     * Gets all reservations that meet the specified parameters. If any parameter is incorrect, an error message will
     * be returned.
     *
     * @return an array list containing the specified reservations or an error message
     */
    @Override
    public ArrayList execute() {
        ArrayList<String> info = new ArrayList<>();

        //Check the number of fields entered is correct
        if(requestInfo.size() < 2 || requestInfo.size() > 4){
            info.add("error");
            info.add("incorrect number of fields");
            return info;
        }

        ArrayList<Reservation> reservation = reservations.getReservations(requestInfo.get(1));

        if(requestInfo.size() == 2){
            info.add("retrieve");
            int size = reservation.size();
            info.add(Integer.toString(size));
            //Return an empty list if the passenger has no reservations.
            if(size == 0){
                return info;
            }
            else{
                for(Reservation r : reservation){
                    info.add("\n");
                    info.add(r.toString());
                }
                return info;
            }
        }
        else if(requestInfo.size() == 3){
            String origin = requestInfo.get(2);
            info.add("retrieve");
            for(Reservation r : reservation){
                if(r.getOrigin().getName().equals(origin)){
                    info.add("\n");
                    info.add(r.toString());
                }
            }
            //Check that the origin is valid.
            if(info.size() == 1){
                info.clear();
                info.add("error");
                info.add("unknown origin");
            }
            return info;
        }
        else{
            String origin = requestInfo.get(2);
            String destination = requestInfo.get(3);

            //Check if an origin is not entered.
            if(origin.equals("")){
                for(Reservation r : reservation){
                    if(r.getDestination().getName().equals(destination)){
                        info.add("\n");
                        info.add(r.toString());
                    }
                }
                //Check if the destination is valid
                if(info.size() == 1){
                    info.clear();
                    info.add("error");
                    info.add("unknown destination");
                }
            }
            else{
                for(Reservation r : reservation){
                    if(r.getOrigin().getName().equals(origin) && r.getDestination().getName().equals(destination)){
                        info.add("\n");
                        info.add(r.toString());
                    }
                }
                //Check that both origin and destination are valid.
                if(info.size() == 1) {
                    info.clear();
                    info.add("error");
                    info.add("unknown origin or destination");
                }
            }
        }
        return info;
    }

    /**
     * Set the Retrieve Reservation Request info.
     * @param requestInfo ArrayList of Strings representing Request info.
     */
    @Override
    public void setRequestInfo(ArrayList<String> requestInfo) {
        this.requestInfo = requestInfo;
    }
}
