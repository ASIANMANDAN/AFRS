package Model.Requests;

import Model.Databases.AirportDatabase.AirportDatabase;

import java.util.ArrayList;

/**
 *
 */
public class ServerRequest implements Request{

    private ArrayList<String> requestInfo;
    private AirportDatabase airportDatabase;

    public ServerRequest(ArrayList<String> request, AirportDatabase airportDatabase){
        requestInfo = request;
        this.airportDatabase = airportDatabase;
    }

    /**
     * Switch which database AirportDatabase is using
     * @return
     */
    @Override
    public ArrayList execute() {
        ArrayList<String> results = new ArrayList<>();
        String server = requestInfo.get(1);

        //Check the client is not already using the requested server
        if(airportDatabase.getActive().equals(server)){
            results.add("error");
            results.add("already using this server");
        }
        //Check the client has entered a valid server
        else if(!server.equals("local") && !server.equals("FAA")){
            results.add("error");
            results.add("unknown information server");
        }
        //Switch which database is being used by AirportDatabase
        else{
            airportDatabase.toggleActive();
            results.add("server");
            results.add("successful");
        }
        return results;
    }

    @Override
    public void setRequestInfo(ArrayList<String> requestInfo) {
        this.requestInfo = requestInfo;
    }
}
