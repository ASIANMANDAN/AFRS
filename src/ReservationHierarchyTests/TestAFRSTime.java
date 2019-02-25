package ReservationHierarchyTests;

import Model.AFRSTime;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAFRSTime {

    @Test
    public void testCreateAFRSTime() {
        AFRSTime string = new AFRSTime("6:50p");
        AFRSTime noonish = new AFRSTime("12:30p");
        AFRSTime time = new AFRSTime(AFRSTime.Measurement.DATE, 18, 50);

        assertTrue(noonish instanceof AFRSTime);
        assertTrue(string instanceof AFRSTime);
        assertTrue(time instanceof AFRSTime);
    }

    @Test
    public void testInMinutes() {
        AFRSTime afterNoon = new AFRSTime("6:50p");
        AFRSTime midNight = new AFRSTime(AFRSTime.Measurement.DATE, 0, 0);
        AFRSTime morning = new AFRSTime("3:30a");

        assertEquals(1130, afterNoon.inMinutes());
        assertEquals(0, midNight.inMinutes());
        assertEquals(210, morning.inMinutes());
    }

    @Test
    public void testFlightDuration() {

        // Just Hours
        AFRSTime later = new AFRSTime(AFRSTime.Measurement.DATE, 12, 00);
        AFRSTime earlier = new AFRSTime(AFRSTime.Measurement.DATE, 13, 00);

        // 12 -> 1
        AFRSTime duration = AFRSTime.timeBetween(later, earlier);
        assertEquals(60, duration.inMinutes());

        // 1 -> 12 the next day
        duration = AFRSTime.timeBetween(earlier, later);
        assertEquals(1380, duration.inMinutes());

        // Just Minutes
        later = new AFRSTime(AFRSTime.Measurement.DATE, 0, 50);
        earlier = new AFRSTime(AFRSTime.Measurement.DATE, 0, 20);

        // 00:20 -> 00:50
        duration = AFRSTime.timeBetween(earlier, later);
        assertEquals(30, duration.inMinutes());

        // 00:50 -> 00:20 the next day
        duration = AFRSTime.timeBetween(later, earlier);
        assertEquals(1410, duration.inMinutes());

        // Hours and Minutes
        later = new AFRSTime(AFRSTime.Measurement.DATE, 1, 25);
        earlier = new AFRSTime(AFRSTime.Measurement.DATE, 0, 10);

        // 0:10 -> 1:25
        duration = AFRSTime.timeBetween(earlier, later);
        assertEquals(75, duration.inMinutes());

        // 1:25 -> 0:10 the next day
        duration = AFRSTime.timeBetween(later, earlier);
        assertEquals(1365, duration.inMinutes());
    }

    @Test
    public void testAddDuration() {
        AFRSTime base = new AFRSTime(AFRSTime.Measurement.DURATION, 1, 25);
        AFRSTime addition = new AFRSTime(AFRSTime.Measurement.DURATION, 3, 55);
        base.addDuration(addition);

        assertEquals(320, base.inMinutes());
    }

    @Test
    public void testIsValid(){
        AFRSTime unlikely = new AFRSTime(AFRSTime.Measurement.DURATION, 24, 00);
        AFRSTime hour = new AFRSTime(AFRSTime.Measurement.DURATION, 1, 20);
        AFRSTime enough = new AFRSTime(AFRSTime.Measurement.DURATION, 1, 00);
        AFRSTime noNeed = new AFRSTime(AFRSTime.Measurement.DURATION, 0, 00);

        // Test when the Flights are the Same Day
        AFRSTime arrivalSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 3, 00);
        AFRSTime departSameDay = new AFRSTime(AFRSTime.Measurement.DATE, 4, 00);

        assertFalse(AFRSTime.isValid(arrivalSameDay, hour, departSameDay));
        assertTrue(AFRSTime.isValid(arrivalSameDay, enough, departSameDay));
        assertTrue(AFRSTime.isValid(arrivalSameDay, noNeed, departSameDay));

        // Test when the Flights are on different Days
        AFRSTime arrivalOneDay = new AFRSTime(AFRSTime.Measurement.DATE, 19, 50);
        AFRSTime departNextDay = new AFRSTime(AFRSTime.Measurement.DATE, 5, 00);

        assertFalse(AFRSTime.isValid(arrivalOneDay, unlikely, departNextDay));
        assertTrue(AFRSTime.isValid(arrivalOneDay, hour, departNextDay));
        assertTrue(AFRSTime.isValid(arrivalOneDay, enough, departNextDay));
        assertTrue(AFRSTime.isValid(arrivalOneDay, noNeed, departNextDay));
    }
}
