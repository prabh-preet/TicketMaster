package com.example.ticketmaster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

/**
 * This is the class generating the details of an event on the page
 * by getting it from the first page
 * @author Prabhpreet Kaur
 * @version 1.0
 */
public class EventDetailActivity extends AppCompatActivity {

    /**
     * This is the in-built create method which is necessary to start an application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ImageView imageViewEvent = findViewById(R.id.image_banner);
        TextView textViewEventName = findViewById(R.id.text_event_name);
        TextView textViewDate = findViewById(R.id.text_date);
        TextView textViewTime = findViewById(R.id.text_time);
        TextView textViewLocation = findViewById(R.id.text_venue);
        TextView textViewPrice = findViewById(R.id.text_price);
        TextView textViewTicketLimit = findViewById(R.id.text_ticket_limit);
        TextView textViewTicketInfo = findViewById(R.id.text_note);
        Button buttonBuyTickets = findViewById(R.id.btn_buy_tickets);

        // Retrieve data from intent or wherever you store your JSON response
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String startDate = intent.getStringExtra("startDate");
            String time = intent.getStringExtra("time");
            String location = intent.getStringExtra("location");
            String price = intent.getStringExtra("priceRange");
            String ticketLimit = intent.getStringExtra("ticketLimit");
            String ticketInfo = intent.getStringExtra("ticketInfo");
            String imageUrl = intent.getStringExtra("imageUrl");

            // Set the data to corresponding views
            textViewEventName.setText(name);
            textViewDate.setText(startDate);
            textViewLocation.setText(location);
            textViewTicketInfo.setText(ticketInfo);
            textViewTime.setText(time);
            textViewPrice.setText(price);
            textViewTicketLimit.setText(ticketLimit);

            // Use Picasso to load the image into the ImageView
            Picasso.get().load(imageUrl).into(imageViewEvent);
        }

        buttonBuyTickets.setOnClickListener(v -> {
            // Handle button click event
            Log.d("Test", "Url for Purchasing Ticket - " + intent.getStringExtra("purchaseTicketUrl"));
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("purchaseTicketUrl")));
            v.getContext().startActivity(i);
        });
    }
}
