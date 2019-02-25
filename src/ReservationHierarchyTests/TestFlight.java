package ReservationHierarchyTests;

import Model.AFRSTime;
import Model.Airport;
import Model.ReservationHierarchy.Flight;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFlight {

    Airport ORD = new Airport("ORD", "Chicago");
    Airport PHX = new Airport("PHX", "Phoenix");
    Airport PIT = new Airport("PIT", "Pittsburgh");

    AFRSTime unlikely = new AFRSTime(AFRSTime.Measurement.DURATION, 24, 00);
    AFRSTime hour = new AFRSTime(AFRSTime.Measurement.DURATION, 1, 20);
    AFRSTime enough = new AFRSTime(AFRSTime.Measurement.DURATION, 1, 00);
    AFRSTime noNeed = new AFRSTime(AFRSTime.Measurement.DURATION, 0, 00);

    // Flight In One Day
    AFRSTime arrivalSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 4, 00);
    AFRSTime departSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 3, 00);

    // Flight in Two Days
    AFRSTime arrivalNextDay = new AFRSTime(AFRSTime.Measurement.DATE, 5, 00);
    AFRSTime departOneDay = new AFRSTime(AFRSTime.Measurement.DATE, 7, 50);

    @Test
    public void testCreateFlight() {
        Flight f1 = new Flight(ORD, PHX, departSameDay, arrivalSameDay, 100, 1);
        assertEquals(ORD, f1.getOrigin());
        assertEquals(PHX, f1.getDestination());
        assertEquals(departSameDay, f1.getDeparture());
        assertEquals(arrivalSameDay, f1.getArrival());
        // assertEquals(airfare, f1.getAirfare());
        assertEquals(1, f1.getFlightNumber());
    }

    @Test
    public void testDuration() {
        Flight f1 = new Flight(ORD, PHX, departSameDay, arrivalSameDay, 1000, 1);
        Flight f2 = new Flight(PIT, PHX, departOneDay, arrivalNextDay, 1000, 2);

        assertEquals(60, f1.duration().inMinutes());
        assertEquals(1270, f2.duration().inMinutes());
    }

    @Test
    public void testEquals() {
        Flight f1 = new Flight(ORD, PHX, departSameDay, arrivalSameDay, 1000, 1);
        Flight f2 = new Flight(ORD, PHX, departSameDay, arrivalSameDay, 1000, 1);

        Flight f3 = new Flight(ORD, PHX, departSameDay, arrivalSameDay, 1000, 3);

        assertTrue(f1.equals(f2));
        assertFalse(f1.equals(f3));
    }
}
