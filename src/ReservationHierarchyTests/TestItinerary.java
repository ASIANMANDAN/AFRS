package ReservationHierarchyTests;

import Model.AFRSTime;
import Model.Airport;
import Model.ReservationHierarchy.Flight;
import Model.ReservationHierarchy.Itinerary;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestItinerary {

    Airport PHX = new Airport("PHX", "Phoenix");
    Airport PIT = new Airport("PIT", "Pittsburgh");

    // Flight In One Day
    AFRSTime arrivalSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 4, 00);
    AFRSTime departSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 3, 00);
    Flight oneDay = new Flight(PIT, PHX, departSameDay, arrivalSameDay, 1000, 1);

    // Flight in Two Days
    AFRSTime arrivalNextDay = new AFRSTime(AFRSTime.Measurement.DATE, 5, 00);
    AFRSTime departOneDay = new AFRSTime(AFRSTime.Measurement.DATE, 7, 50);
    Flight twoDay = new Flight(PHX, PIT, departOneDay, arrivalNextDay, 1500, 2);

    AFRSTime noTime = new AFRSTime(AFRSTime.Measurement.DURATION, 0, 0);

    @Test
    public void testCreateItinerary() {
        Itinerary i = new Itinerary();
        assertTrue(0 == i.getAirfare());
        assertEquals(null, i.getOrigin());
        assertEquals(null, i.getDestination());
        assertEquals(null, i.getChild(0));
        assertFalse(i.isFull());
        assertEquals(noTime.inMinutes(), i.duration().inMinutes());
    }

    @Test
    public void testOneFlight() {
        Itinerary i = new Itinerary();
        i.addFlight(oneDay);

        assertTrue(1000 == i.getAirfare());
        assertEquals(PIT, i.getOrigin());
        assertEquals(PHX, i.getDestination());
        assertEquals(oneDay, i.getChild(0));
        assertEquals(null, i.getChild(1));
        assertFalse(i.isFull());
        assertTrue(oneDay.duration().inMinutes() == i.duration().inMinutes());
    }

    @Test
    public void testTwoSameFlights() {
        Itinerary i = new Itinerary();
        i.addFlight(oneDay);
        i.addFlight(oneDay);

        assertTrue(2000 == i.getAirfare());
        assertEquals(PIT, i.getOrigin());
        assertEquals(PHX, i.getDestination());
        assertEquals(oneDay, i.getChild(0));
        assertEquals(oneDay, i.getChild(1));
        assertEquals(null, i.getChild(2));
        assertFalse(i.isFull());
        assertTrue(oneDay.duration().inMinutes() * 2 == i.duration().inMinutes());
    }
}
