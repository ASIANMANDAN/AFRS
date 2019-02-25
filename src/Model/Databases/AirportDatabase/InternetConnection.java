package Model.Databases.AirportDatabase;

import Model.AFRSTime;
import Model.Airport;

import java.util.Collection;
import java.util.HashMap;

public interface InternetConnection {

    void toggle(HashMap online, HashMap offline);
    String getState();
}
