package Model.Databases.AirportDatabase;

import java.util.HashMap;

public class Online implements InternetConnection {

    private AirportDatabase airportDatabase;

    public Online(AirportDatabase airportDatabase) {
        this.airportDatabase  = airportDatabase;
    }

    /**
     * Change the state to Offline
     * @param online The FAA database
     * @param offline The local database
     */
    @Override
    public void toggle(HashMap online, HashMap offline) {
        InternetConnection goOffline = new Offline(this.airportDatabase);
        this.airportDatabase.setActive(goOffline, offline);
    }

    /**
     * Return what state the AirportDatabase is using
     * @return The current InternetConnection being used
     */
    public String getState(){
        return "FAA";
    }
}
