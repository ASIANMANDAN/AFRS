package Controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Elijah Cantella - edc8230@rit.edu
 * @author Dan Wang - dcw2772@rit.edu
 */
public class WebsiteWeather {

    public static void main(String[] args) {
        String urlPreface =
                "https://soa.smext.faa.gov/asws/api/airport/status/";
        String url;

        try {        // Create a URL and open a connection
            url = urlPreface + args[0];
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

            // response is the contents of the XML
            System.out.println(response);
            System.out.println();
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
}
