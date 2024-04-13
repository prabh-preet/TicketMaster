package com.example.ticketmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This is my main class for the whole application from which all the content is appearing
 * and everything like the display details comes here. I made some methods for displaying the details
 * using JSON and some other in-built methods
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This is my API key variable
     */
    private final String API_KEY = 	"9kahPnTfLwlJrXPQdAD5g06FN55x08jB";

    /**
     *  This is the URL for the ticketMaster
     */
    private final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";

    /**
     * This is the variable to add city name
     */
    private EditText editTextCity;

    /**
     * This is the variable to add radius
     */
    private EditText editTextRadius;

    /**
     * This is the object for RecycleView
     */
    private RecyclerView recyclerViewEvents;

    /**
     * This is the object for my another class
     */
    private TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String cityName = prefs.getString("CityName", "");
        String radiusNumber = prefs.getString("Radius", "");

        editTextCity = findViewById(R.id.cityEditText);
        editTextRadius = findViewById(R.id.RadiusEditText);
        recyclerViewEvents = findViewById(R.id.eventsRecycleView);

        editTextCity.setText(cityName);
        editTextRadius.setText(radiusNumber);

        setSupportActionBar(findViewById(R.id.myToolbar));

        // Set up RecyclerView
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketAdapter();
        recyclerViewEvents.setAdapter(ticketAdapter);

        SharedPreferences.Editor editor = prefs.edit();

        // Set button click listener
        Button buttonSearch = findViewById(R.id.searchButton);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("CityName" , editTextCity.getText().toString());
                editor.putString("Radius", editTextRadius.getText().toString());
                editor.apply();

                searchEvents(v);
            }
        });
    }

    /**
     * This is the method for searching the events
     * and it displays a toast if we are not entering the proper information
     * @param view used for making view
     */
    public void searchEvents(View view) {
        String city = editTextCity.getText().toString();
        String radius = editTextRadius.getText().toString();

        if (city.isEmpty() || radius.isEmpty()) {
            Toast.makeText(this, "Please enter city and radius", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchEvents(city, Integer.parseInt(radius));
    }

    /**
     * This is the method to get the events according to the city name and radius
     * @param city it stores the city name
     * @param radius it stores the radius
     */
    private void fetchEvents(String city, int radius) {
        // Build URL with query parameters
        String url = BASE_URL + "?apikey=" + API_KEY + "&city=" + city + "&radius=" + radius;

        // Create JsonObjectRequest to fetch events
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse JSON response
                            List<Ticket> tickets = parseEvents(response);
                            // Update RecyclerView with events
                            ticketAdapter.setEvents(tickets);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(MainActivity.this, "Error fetching events", Toast.LENGTH_SHORT).show();
            }
        });

        // Add JsonObjectRequest to Volley RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    /**
     * This method makes a list of the events which are getting by using JSON
     * from a web server by their city name and radius
     * @param response object for JSON
     * @return List of events
     * @throws JSONException when there is anything happening because of any exception
     */
    private List<Ticket> parseEvents(JSONObject response) throws JSONException {
        List<Ticket> tickets = new ArrayList<>();

        // Parse JSON response and extract event details
        JSONObject embedded = response.getJSONObject("_embedded");
        JSONArray eventsArray = embedded.getJSONArray("events");

        for (int i = 0; i < eventsArray.length(); i++) {
            try {
                JSONObject eventObject = eventsArray.getJSONObject(i);
                // Extract event details
                String eventName = eventObject.getString("name");
                String startDate = eventObject.getJSONObject("dates").getJSONObject("start").getString("localDate");
                String time = eventObject.getJSONObject("dates").getJSONObject("start").getString("localTime");
                String location = eventObject.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");

                String priceRange = "";
                JSONArray priceRanges = eventObject.optJSONArray("priceRanges");
                if (priceRanges != null && priceRanges.length() > 0) {
                    JSONObject priceObject = priceRanges.getJSONObject(0);
                    double minPrice = priceObject.optDouble("min", 0.0);
                    double maxPrice = priceObject.optDouble("max", 0.0);
                    priceRange = minPrice + " - " + maxPrice;
                }

                String url = eventObject.getString("url");

                String imageUrl = "";
                JSONArray images = eventObject.optJSONArray("images");
                if (images != null && images.length() > 0) {
                    JSONObject imageObject = images.getJSONObject(0);
                    imageUrl = imageObject.getString("url");
                }

                String ticketInfo = "";
                if (eventObject.has("pleaseNote")) {
                    ticketInfo = eventObject.getString("pleaseNote");
                }

                String ticketLimit = "";
                if (eventObject.has("ticketLimit")) {
                    ticketLimit = eventObject.getString("ticketLimit");

                }



                // Create Event object and add to list
                Ticket ticket = new Ticket(eventName, startDate, priceRange, url, imageUrl, time, location, ticketInfo, ticketLimit);
                tickets.add(ticket);
            }
            catch (JSONException e) {
                Log.d("Test", "Error - " + e.toString());
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error Parsing", Toast.LENGTH_SHORT).show();
            }
        }

        return tickets;
    }

    /**
     * This is the in-built method for menu items
     * @param menu takes the menu details
     * @return true if it is true or false when it is false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * This is also the in-built method for menu items
     * @param item takes item from the menu
     * @return true if it finds something otherwise false if it not provide
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );

        if(item.getItemId() == R.id.help) {
            builder.setTitle("Instructions: ")
                    .setMessage("1. Type the city name and radius to get the events which will happen in the future.\n" +
                            "2. Now, click on the search button to find all the events in your country.\n" +
                            "3. After that, check the list of events and click on the picture of the event of that event which you want to know more about.\n" +
                            "4. Moreover, you can find the details on the next page from where you can click on the button for buying your favourite event tickets.\n" +
                            "Note: This is the main page for TicketMaster event search.")
                    .setPositiveButton("OK", null)
                    .show();

        }
        return true;
    }
}
