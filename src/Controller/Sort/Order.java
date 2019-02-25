/**
 * Interface that all the orders implement
 *
 * @author Meghan Johnson - @mrj9235@rit.edu
 */
package Controller.Sort;

import Model.ReservationHierarchy.Itinerary;

import java.util.ArrayList;

public interface Order {
    ArrayList<Itinerary> sort();
}
