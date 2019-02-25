package Model.Requests;


import Model.Airport;
import Model.Databases.ReservationDatabase;
import Model.ReservationHierarchy.Reservation;

import java.util.ArrayList;

/**
 * This class represents a request to delete an existing reservation.
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public class DeleteReservationRequest implements Request{

    private ArrayList<String> requestInfo;
    private ReservationDatabase reservations;
    private Reservation reservation;

    public DeleteReservationRequest(ArrayList request, ReservationDatabase reservations){
        this.requestInfo = request;
        this.reservations = reservations;
    }

    /**
     * Deletes the requested reservation.
     *
     * @return An array list containing a confirmation message or error message.
     */
    public ArrayList execute(){
        ArrayList<String> results = new ArrayList<>();

        //Check the correct number of fields were entered
        if(requestInfo.size() != 4){
            results.add("error");
            results.add("incorrect number of fields");
            return results;
        }

        String passenger = requestInfo.get(1);
        String origin = requestInfo.get(2);
        String destination = requestInfo.get(3);

        //Check that the passenger has any reservations
        if(reservations.hasReservation(passenger)){
            ArrayList<Reservation> res = reservations.getReservations(passenger);
            //Check if any of the existing reservations match the specified reservation
            for(int i = 0; i < res.size(); i++){
                String originCheck = res.get(i).getOrigin().getName();
                String destinationCheck = res.get(i).getDestination().getName();
                if(origin.equals(originCheck) && destination.equals(destinationCheck)){
                    reservations.removeReservations(passenger, res.get(i).getOrigin(), res.get(i).getDestination());
                    this.reservation = res.get(i);
                    results.add("delete");
                    results.add("successful");
                    return results;
                }
            }
            results.add("error");
            results.add("reservation not found");
            return results;
        }
        else{
            results.add("error");
            results.add("reservation not found");
        }
        return results;
    }

    @Override
    public void setRequestInfo(ArrayList<String> requestInfo) {

    }

    /**
     * Undo the deletion by adding the reservation to the reservation database
     * @return An array list containing a success message
     */
    public ArrayList<String> undo(){
        ArrayList<String> results = new ArrayList<>();

        reservations.addReservations(reservation);

        results.add("undo");
        results.add("reserve");
        results.add(reservation.getPassengerName());
        results.add(reservation.getChild(0).toString());

        return results;
    }

    /**
     * Redo the deletion by deleting the reservation from the reservation database
     * @return An array list containing a success message
     */
    public ArrayList<String> redo(){
        ArrayList<String> results = new ArrayList<>();
        String passenger = reservation.getPassengerName();
        Airport origin = reservation.getOrigin();
        Airport destination = reservation.getDestination();

        reservations.removeReservations(passenger, origin, destination);

        results.add("redo");
        results.add("delete");
        results.add(passenger);
        results.add(reservation.getChild(0).toString());

        return results;
    }
}
