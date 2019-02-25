package Model.Databases.AirportDatabase;

import java.util.HashMap;

public class Offline implements InternetConnection {

    private AirportDatabase airportDatabase;

    public Offline(AirportDatabase airportDatabase) {
        this.airportDatabase = airportDatabase;
    }

    /**
     * Change the state to Online
     * @param online The FAA database
     * @param offline The local database
     */
    @Override
    public void toggle(HashMap online, HashMap offline) {
        InternetConnection goOnline = new Online(this.airportDatabase);
        this.airportDatabase.setActive(goOnline, online);
    }

    /**
     * Return what state the AirportDatabase is using
     * @return The current InternetConnection being used
     */
    public String getState(){
        return "local";
    }
}
