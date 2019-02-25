package Controller.Factories;

import Model.AFRSTime;
import Model.Airport;
import Model.Databases.Database;
import Model.Databases.AirportDatabase.AirportDatabase;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Creates an AirportDatabase using 4 set text files. This Factory does not rely on
 * any Databases to create the AirportDatabase.
 *
 * @author Elijah Cantella - edc8230@rit.edu
 */
public class AirportDatabaseFactory implements DatabaseFactory {

    // ----------
    // Attributes
    // ----------

    // Website Response Indexes
    private String urlPreface =
            "https://soa.smext.faa.gov/asws/api/airport/status/";
    private int websiteAirportNameIndex = 0;
    private int websiteCityNameIndex = 1;

    private int delayBooleanIndex = 6;
    private int delayBooleanValueIndex = 1;
    private int delayCountIndex = 7;
    private int delayCountValueIndex = 1;

    private int websiteWeatherIndex = 9;
    private int websiteWeatherValue = 3;
    private int temperatureIndex = 15;
    private int temperatureValue = 1;
    // Airports
    private String airportFileName = "airports.txt";
    private int airportNameIndex = 0;
    private int airportCityIndex = 1;

    // Weather
    private String weatherFileName = "weather.txt";
    private int weatherAirportIndex = 0;

    // Airport Delays
    private String delayFileName = "delays.txt";
    private int delayAirportIndex = 0;

    // Connection Times
    private String connectionFileName = "connections.txt";
    private int connectionAirportIndex = 0;
    private int durationIndex = 1;

    // Database that is Made
    private AirportDatabase database;

    // -------
    // Methods
    // -------

    private void readFromWebsite() {
        String line;
        String[] elements;

        try {
            FileReader fileReader = new FileReader("src/Input/" + this.airportFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {

                elements = line.split(",");
                this.WebsiteAirport(elements[airportNameIndex]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WebsiteAirport(String code) {
        String url;

        try {        // Create a URL and open a connection
            url = this.urlPreface + code;
            URL FAAURL = new URL(url);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) FAAURL.openConnection();

            // Set the paramters for the HTTP Request
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Accept", "application/json");

            // Create an input stream and wrap in a BufferedReader to read the
            // response.
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // MAKE SURE TO CLOSE YOUR CONNECTION!
            in.close();

            this.addWebsiteAirport(code, response);
        }
        catch (FileNotFoundException e) {
            System.out.print("URL not found: ");
            System.out.println(e.getMessage());
        }
        catch (MalformedURLException e) {
            System.out.print("Malformed URL: ");
            System.out.println(e.getMessage());
        }
        catch (ProtocolException e) {
            System.out.print("Unsupported protocol: ");
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addWebsiteAirport(String code, StringBuilder response) {
        String[] values = response.toString().split(",");
        String airportName = values[this.websiteAirportNameIndex].split(":")[this.websiteCityNameIndex];
        airportName = airportName.substring(1, airportName.length() -1);

        String hasDelay = values[this.delayBooleanIndex].split(":")[this.delayBooleanValueIndex];
        AFRSTime delay;
        if(hasDelay.equals("true")) {
            String delayCount = values[this.delayCountIndex].split(":")[this.delayCountValueIndex];
            delay = new AFRSTime(AFRSTime.Measurement.DURATION, 0, Integer.parseInt(delayCount));
        }
        else {
            delay = new AFRSTime(AFRSTime.Measurement.DURATION, 0,0);
        }

        String weather = values[this.websiteWeatherIndex].split(":")[this.websiteWeatherValue];
        weather = weather.substring(2, weather.length() - 4);

        String temperature = values[this.temperatureIndex].split(":")[this.temperatureValue];
        temperature = temperature.substring(2, temperature.length() - 2);

        Airport airport = new Airport(code, airportName);
        airport.setDelay(delay);
        airport.addWeather(weather);
        airport.addTemperature(temperature);

        this.database.addAirport(code, airport);
    }

    /**
     * Read in the information from the provided text file. Depending on the file being
     * read in, will add the corresponding attribute/object in the Database.
     * @param filename String name of the file to be read.
     */
    private void readIn(String filename) {
        String line;
        String[] elements;

        try {
            FileReader fileReader = new FileReader("src/Input/" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {

                elements = line.split(",");

                if (filename.equals(this.airportFileName)) {
                    this.addAirport(elements);
                }
                else if (filename.equals(this.weatherFileName)) {
                    this.addWeather(elements);
                }
                else if (filename.equals(this.delayFileName)) {
                    this.addDelay(elements);
                }
                else if (filename.equals(this.connectionFileName)) {
                    this.addConnectionTime(elements);
                }
            }

            fileReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add an Airport to the AirportDatabase.
     * @param elements String[] where [0] is the Code and [1] is the City.
     */
    private void addAirport(String[] elements) {
        Airport airport = new Airport(elements[this.airportNameIndex], elements[this.airportCityIndex]);
       this.database.addAirport(elements[this.airportNameIndex], airport);
    }

    /**
     * Add Weather patterns to an AirportDatabase. The Database will add the Weather to the
     * corresponding Airport. It is expected that the AirportDatabase has Airports.
     * @param elements String[] where [0] is the Airport Code and [i > 0] is a Weather pattern.
     */
    private void addWeather(String[] elements) {
        for(int i = 1; i < elements.length; i+=2){
            this.database.addWeather(elements[this.weatherAirportIndex], elements[i]);
        }
        for(int i = 2; i < elements.length; i+=2){
            this.database.addTemperature(elements[this.weatherAirportIndex], elements[i]);
        }
    }

    /**
     * Add a Delays to an AirportDatabase. The Database will add the Delay to the corresponding
     * Airport. It is expected that the AirportDatabase has Airports.
     * @param elements String[] where [0] is the Airport Code and [i > 0] is a Delay.
     */
    private void addDelay(String[] elements) {
        for(int i = 1; i < elements.length; i++){
            this.database.addDelay(elements[this.delayAirportIndex], elements[i]);
        }
    }

    /**
     * Add a connection time to the AirportDatabase. The Database will add the connection time to
     * the corresponding Airport. It is expected that the AirportDatabase has Airports.
     * @param elements String[] where each index is a part of the connection time.
     */
    private void addConnectionTime(String[] elements) {
        Airport airport = this.database.getAirport(elements[this.connectionAirportIndex]);
        AFRSTime time = new AFRSTime(AFRSTime.Measurement.DURATION,0,Integer.parseInt(elements[this.durationIndex]));
        airport.setMinConnectionTime(time);
    }

    /**
     * Create a new AirportDatabaseFactory. This Object will create an AirportDatabase and populate
     * it with information from the set text files.
     */
    public AirportDatabaseFactory() {
        this.database = new AirportDatabase();
    }

    /**
     * Make a Database, in this case an AirportDatabase using a series of set text files.
     * @return AirportDatabase as a Database.
     */
    @Override
    public Database makeDatabase() {
        this.readIn(this.airportFileName);
        this.readIn(weatherFileName);
        this.readIn(delayFileName);
        this.readIn(connectionFileName);

        this.database.toggleActive();
        this.readFromWebsite();
        this.database.toggleActive();

        return this.database;
    }
}
