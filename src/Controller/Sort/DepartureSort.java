/**
 * DepartureSort sorts the itineraries requested from earliest to latest departure time
 *
 * @author Meghan Johnson - @mrj9235@rit.edu
 */
package Controller.Sort;

import Model.ReservationHierarchy.Flight;
import Model.ReservationHierarchy.Itinerary;

import java.util.ArrayList;
import java.util.Comparator;

public class DepartureSort implements Order, Comparator<Itinerary> {
    /**
     * the list of itineraries that is going to be sorted
     */
    private ArrayList<Itinerary> itineraries;


    public DepartureSort(ArrayList<Itinerary> itineraries){
        this.itineraries = itineraries;
    }

    /**
     * The sorting method to sort the itineraries by the departure time
     * @return the ArrayList of sorted itineraries
     */
    public ArrayList<Itinerary> sort() {
        ArrayList<Itinerary> sortedItineraries = new ArrayList<>();
        for(Itinerary itn : itineraries){

            //automatically places the first itinerary into the new ArrayList
            if(sortedItineraries.size() == 0){
                sortedItineraries.add(itn);
            }
            else{
                int idx = 0;
                while(idx < sortedItineraries.size()){
                    int result = compare(itn, sortedItineraries.get(idx));
                    //checks to see if the result is less than the itinerary at the index
                    if(result == -1 || result == 0 && !sortedItineraries.contains(itn)){
                        //places the itinerary at that index
                        sortedItineraries.add(idx, itn);
                        break;
                    }
                    //if the departure time is greater
                    else{
                        //if it is the last item in the list, it gets added in
                        if(idx == sortedItineraries.size()-1 && !sortedItineraries.contains(itn)){
                            sortedItineraries.add(idx+1, itn);
                        }
                        else idx++;
                    }
                }
            }
        }
        return sortedItineraries;
    }

    /**
     * compare takes in two objects and compares the departure time of each of the objects.
     * @param o1 an itinerary object
     * @param o2 the itinerary object o1 is being compared to
     * @return -1 if o1 leaves earlier than o2
     *          1 if o2 leaves earlier than o1
     *          0 if they leave at the same time
     */
    @Override
    public int compare(Itinerary o1, Itinerary o2) {
        Flight f1 = o1.getChild(0);
        Flight f2 = o2.getChild(0);
        int depart1 = f1.getDeparture().inMinutes();
        int depart2 = f2.getDeparture().inMinutes();
        if(depart1 < depart2){
            return -1;
        }
        else if(depart1 > depart2){
            return 1;
        }
        else{
            return 0;
        }
    }
}
