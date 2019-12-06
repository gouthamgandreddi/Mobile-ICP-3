package com.example.vijaya.earthquakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        try {
            //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
            URL earthQuakeData = new URL(requestUrl);
            URLConnection yc = earthQuakeData.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()
            ));
            String str = "";
            String inputLine;
            while((inputLine = in.readLine()) != null){
                str = str.concat(inputLine);
            }
            //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)

            /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                   "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                    Add each earthquake object to the list(earthquakes) and return it.
             */
            JSONObject reader = new JSONObject(str);
            JSONArray features = reader.getJSONArray("features");
            for(int i=0; i < features.length() ; i++){
                JSONObject round = features.getJSONObject(i);
                JSONObject properties = round.getJSONObject("properties");
                Double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                Long time = properties.getLong("time");
                String eUrl = properties.getString("url");
                System.out.println(" *********** "+mag+" - "+place+" - "+time+" - "+eUrl);
                earthquakes.add(new Earthquake(mag, place, time, eUrl));
            }
            // Return the list of earthquakes
            in.close();


        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        System.out.println("earthquakes in QueryUtils - "+earthquakes);
        // Return the list of earthquakes
        return earthquakes;
    }
}
