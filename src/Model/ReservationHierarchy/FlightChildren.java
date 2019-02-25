package Model.ReservationHierarchy;

import Model.Airport;
import Model.AFRSTime;

public interface FlightChildren {

    float getAirfare();
    AFRSTime duration();
    Airport getOrigin();
    Airport getDestination();
    FlightChildren getChild(int i);
}
