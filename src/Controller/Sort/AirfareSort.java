/**
 * AirfareSort orders the itineraries requested from lowest to greatest airfare
 *
 * @author Meghan Johnson - mrj9235@rit.edu
 */
package Controller.Sort;

import Model.ReservationHierarchy.Itinerary;

import java.util.ArrayList;
import java.util.Comparator;

public class AirfareSort implements Order, Comparator<Itinerary> {
    private ArrayList<Itinerary> itineraries;

    public AirfareSort(ArrayList<Itinerary> itineraries){
        this.itineraries = itineraries;
    }

    public ArrayList<Itinerary> sort() {
        ArrayList<Itinerary> sortedItineraries = new ArrayList<>();
        for(Itinerary itn : itineraries){
            //automatically places the first itinerary in the list
            if(sortedItineraries.size() == 0){
                sortedItineraries.add(itn);
            }
            else{
                int idx = 0;
                while(idx < sortedItineraries.size()){
                    int result = compare(itn, sortedItineraries.get(idx));
                    if(result == -1 || result == 0 && !sortedItineraries.contains(itn)){
                        sortedItineraries.add(idx, itn);
                        break;
                    }
                    else{
                        if( idx == sortedItineraries.size() - 1 && !sortedItineraries.contains(itn)){
                            sortedItineraries.add(idx + 1, itn);
                        }
                        idx++;
                    }
                }
            }
        }
        return sortedItineraries;
    }

    /**
     * Compares the airfare between two itineraries passed into the function
     * @param o1 The unordered itinerary
     * @param o2 The itinerary the unordered itinerary is being compared to
     * @return -1 if o1 is cheaper than o2
     *          1 if o2 is cheaper than o1
     *          0 if they cost the same amount
     */
    @Override
    public int compare(Itinerary o1, Itinerary o2) {
        if(o1.getAirfare() < o1.getAirfare()){
            return -1;
        }
        else if( o1.getAirfare() > o2.getAirfare()){
            return 1;
        }
        else{
            return 0;
        }
    }



}
