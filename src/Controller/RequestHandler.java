package Controller;

import Model.Client;
import Model.Databases.AirportDatabase.AirportDatabase;
import Model.Databases.ItineraryDatabase;
import Model.Requests.*;
import Model.Databases.ReservationDatabase;
import Model.ReservationHierarchy.Itinerary;
import Model.ReservationHierarchy.Reservation;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Handles the request input from the user. A proxy that prevents the user from directly interacting
 * with the various types of requests directly.
 *
 * @author Dan Wang - dcw2772@rit.edu
 * @author Elijah Cantella - edc8230@rit.edu
 */
public class RequestHandler implements Request {

    // ----------
    // Attributes
    // ----------

    private ArrayList<String> request;
    private AirportDatabase airportDatabase;
    private ReservationDatabase reservationDatabase;
    private ItineraryDatabase itineraryDatabase;
    private ArrayList<Client> clients;

    // -------
    // Methods
    // -------

    /**
     * Create a new RequestHandler that executes requests. Returns the result of an execution in
     * an ArrayList of Strings.
     * @param airportDatabase AirportDatabase the Requests will interact with.
     * @param reservationDatabase ReservationDatabase the Requests will interact with.
     * @param itineraryDatabase ItineraryDatabase the Requests will interact with.
     */
    public RequestHandler(AirportDatabase airportDatabase,
                          ReservationDatabase reservationDatabase, ItineraryDatabase itineraryDatabase){
        this.request = null;
        this.airportDatabase = airportDatabase;
        this.reservationDatabase = reservationDatabase;
        this.itineraryDatabase = itineraryDatabase;
        this.clients = new ArrayList<>();
    }

    /**
     * Make and execute an Info Request.
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> infoRequest(Client client) {
        FlightRequest flightRequest = new FlightRequest(request, airportDatabase, itineraryDatabase);
        ArrayList<ArrayList> results = flightRequest.execute();
        if(results.size() == 2){
           client.setNewestItinerary(results.get(1));
        }
        return results.get(0);
    }

    /**
     * Make and execute a Reserve Request.
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> reserveRequest(Client client) {
        Stack undoRequests = client.getUndo();
        Stack redoRequests = client.getRedo();
        MakeReservationRequest makeReservation = new MakeReservationRequest(request, reservationDatabase,
                client.getNewestItinerary());
        ArrayList<String> reserve = makeReservation.execute();
        if(reserve.get(0).equals("reserve")){
            undoRequests.push(makeReservation);
        }
        if(!redoRequests.empty()){
            client.setRedo(new Stack<>());
        }
        return reserve;
    }

    /**
     * Make and execute a Retrieve Request.
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> retrieveRequest() {
        RetrieveReservationRequest retrieveReservation = new RetrieveReservationRequest(request, reservationDatabase);
        return retrieveReservation.execute();
    }

    /**
     * Make and execute a Delete Request.
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> deleteRequest(Client client) {
        Stack undoRequest = client.getUndo();
        Stack redoRequest = client.getRedo();
        DeleteReservationRequest deleteReservation = new DeleteReservationRequest(request, reservationDatabase);
        ArrayList<String> delete = deleteReservation.execute();
        if(delete.get(0).equals("delete")){
            undoRequest.push(deleteReservation);
        }
        if(!redoRequest.empty()){
            client.setRedo(new Stack<>());
        }
        return delete;
    }

    /**
     * Takes the last make or delete and undoes the action.  Adds item into the redo stack
     * @param client client passed in to access their undo and redo stacks
     * @return a message to be printed in GUI or in Command line.
     */
    private ArrayList<String> undo(Client client){
        Stack undoRequests = client.getUndo();
        Stack redoRequests = client.getRedo();
        ArrayList<String> undo;
        if(!undoRequests.empty()){

            Request request = (Request) undoRequests.pop();
            redoRequests.push(request);
            if(request instanceof MakeReservationRequest){
                undo = ((MakeReservationRequest) request).undo();
            }
            else {
                undo = ((DeleteReservationRequest) request).undo();
            }
            System.out.println(undo);
            return undo;
        }
        else{
            undo = new ArrayList<>();
            undo.add("error, no request available");
        }
        return undo;
    }

    private ArrayList<String> redo(Client client){
        Stack undoRequests = client.getUndo();
        Stack redoRequests = client.getRedo();
        ArrayList<String> redo;
        if(!redoRequests.empty()){

            Request request = (Request)redoRequests.pop();
            undoRequests.push(request);
            if(request instanceof MakeReservationRequest){
                redo = ((MakeReservationRequest) request).redo();
            }
            else{
                redo = ((DeleteReservationRequest) request).redo();
            }
            System.out.println(redo);
            return redo;
        }
        else{
            redo = new ArrayList<>();
            redo.add("error, no request available");
        }
        return redo;
    }

    /**
     * Make and execute an Airport Request.
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> airportRequest(Client client) {
        int weatheridx = client.getWeatheridx();
        AirportRequest airportRequest = new AirportRequest(request, airportDatabase, weatheridx);
        client.setWeatheridx(weatheridx + 1);
        return airportRequest.execute();
    }

    /**
     * Make and execute a Server Request
     * @return ArrayList of Strings representing the result.
     */
    private ArrayList<String> serverRequest(){
        ServerRequest serverRequest = new ServerRequest(request, airportDatabase);
        return serverRequest.execute();
    }

    /**
     * Determine what type of Request the user is asking for, create and execute the Request.
     * The information about the Request is set using setRequestInfo().
     * @return ArrayList of Strings representing the result.
     */
    public ArrayList<String> execute(int id){
        Client client = null;
        int i = 0;
        //gets the proper client to work with
        while(i< clients.size()){
            if(clients.get(i).getId() == id){
                client = clients.get(i);
            }
            i++;
        }
        switch (request.get(0)) {// coordinates commands with appropriate responses
            case "info":
                return this.infoRequest(client);
            case "reserve":
                return this.reserveRequest(client);
            case "retrieve":
                return this.retrieveRequest();
            case "delete":
                return this.deleteRequest(client);
            case "airport":
                return this.airportRequest(client);
            case "undo":
                return undo(client);
            case "redo":
                return redo(client);
            case "server":
                return serverRequest();
            case "connect":
                break;
            case "disconnect":
                break;
            default:
                return null;
        }
        return null;
    }

    @Override
    public ArrayList execute() {
        return null;
    }

    /**
     * Set the Request information for this Object. This Object will determine the type of Request,
     * if it exists, and will execute it upon the execute().
     * @param request ArrayList of Strings representing the Request (user input).
     */
    public void setRequestInfo(ArrayList<String> request){
        this.request = request;
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public ArrayList<Client> getClients(){
        return clients;
    }

}
