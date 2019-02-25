/**
 * ArrivalSort sorts the itineraries requested from earliest to latest Arrival Time
 *
 * @author Meghan Johnson - mrj9235@rit.edu
 */

package Controller.Sort;

import Model.ReservationHierarchy.Flight;
import Model.ReservationHierarchy.Itinerary;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrivalSort implements Order, Comparator<Itinerary>{
    private ArrayList<Itinerary> itineraries;

    public ArrivalSort(ArrayList<Itinerary> itineraries){
        this.itineraries = itineraries;
    }

    /**
     * The method that sorts the itineraries by Arrival time
     * @return an ArrayList of itineraries sorted by ArrivalTime
     */
    @Override
    public ArrayList<Itinerary> sort() {
        ArrayList<Itinerary> sortedItineraries = new ArrayList<>();
        for(Itinerary itn : itineraries){

            //places the first itinerary in, doesn't have to be sorted
            if(sortedItineraries.size() == 0){
                sortedItineraries.add(itn);
            }
            else{
                int idx = 0;
                while(idx < sortedItineraries.size() && !sortedItineraries.contains(itn)){
                    int result = compare(itn, sortedItineraries.get(idx));
                    //checks to see if itn is less than the selected itinerary
                    if(result == -1 || result == 0 && !sortedItineraries.contains(itn)){
                        //places it in at the current index
                        sortedItineraries.add(idx, itn);
                        break;
                    }
                    else{
                        //places the itinerary in the last spot if it is the largest
                        if(idx == sortedItineraries.size() - 1 && !sortedItineraries.contains(itn)){
                            sortedItineraries.add(idx + 1, itn);
                        }
                        else idx++;
                    }
                }
            }
        }
        return sortedItineraries;
    }
    /**
     * compare takes in two objects and compares the arrival time of each of the objects.
     * @param o1 an itinerary object
     * @param o2 the itinerary object o1 is being compared to
     * @return -1 if o1 arrives earlier than o2
     *          1 if o2 arrives earlier than o1
     *          0 if they arrive at the same time
     */
    @Override
    public int compare(Itinerary o1, Itinerary o2) {
        Flight f1 = o1.getChild(o1.getFlightSize()-1);
        Flight f2 = o2.getChild(o1.getFlightSize()-1);
        int arrival1 = f1.getArrival().inMinutes();
        int arrival2 = f2.getArrival().inMinutes();
        if(arrival1 < arrival2){
            return -1;
        }
        else if(arrival1 > arrival2){
            return 1;
        }
        else{
            return 0;
        }
    }
}
