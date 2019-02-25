package Model;

import Model.Requests.Request;
import Model.ReservationHierarchy.Itinerary;
import Model.ReservationHierarchy.Reservation;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Client {
    private int id;
    private ArrayList<Itinerary> newestItinerary;
    private Stack<Request> undo = new Stack<>();
    private Stack<Request> redo = new Stack<>();
    private int weatheridx = 0;

    public Client(int id){
        this.id = id;
    }

    public void setNewestItinerary(ArrayList<Itinerary> newestItinerary){
        this.newestItinerary = newestItinerary;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Itinerary> getNewestItinerary() {
        return newestItinerary;
    }

    public Stack<Request> getUndo() {
        return undo;
    }

    public Stack<Request> getRedo() {
        return redo;
    }

    public void setRedo(Stack<Request> redo) {
        this.redo = redo;
    }

    public int getWeatheridx(){
        return weatheridx;
    }

    public void setWeatheridx(int weatheridx) {
        this.weatheridx = weatheridx;
    }
}
