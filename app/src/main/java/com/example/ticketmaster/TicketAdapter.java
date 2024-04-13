package com.example.ticketmaster;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/** This is the  class which is using recycler view from the main class
 * which is processing the list of events by getting it and adding it to the fragment
 * @author Prabhpreet Kaur
 * @version 1.0
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.EventViewHolder> {

    /**
     * This is the variable for list of events
     */
    private List<Ticket> tickets;

    /**
     * This is the method which is setting the list in the recycler view
     * @param tickets object for events
     */
    public void setEvents(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    /**
     * This is the in-built method used
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return layout for the page
     */
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    /**
     * This is also the in-built method
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (tickets != null && position < tickets.size()) {
            Ticket ticket = tickets.get(position);
            holder.bind(ticket);
        }
    }

    /**
     * This is also the in-built method for counting the number of items
     * @return size of tickets
     */
    @Override
    public int getItemCount() {
        return tickets != null ? tickets.size() : 0;
    }

    /**
     * This is the method for implementing the on click listener
     * and setting everything on the screen when this screen appears.
     * This is the fragment for event details when we click on a certain event
     */
    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * This is the event name variable
         */
        private TextView textViewEventName;

        /**
         * This is the event start date variable
         */
        private TextView textViewStartDate;

        /**
         * This is the event price variable
         */
        private TextView textViewPriceRange;

        /**
         * This is the event image variable
         */
        private ImageView imageViewEvent;

        /**
         * This is the method for finding the items from the previous page
         * and store it in a new variable
         * @param itemView object for view
         */
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.name_text_view);
            textViewStartDate = itemView.findViewById(R.id.start_date_text_view);
            //textViewPriceRange = itemView.findViewById(R.id.price_range_text_view);
            imageViewEvent = itemView.findViewById(R.id.event_image_view);

            // Set OnClickListener for the entire item view
            itemView.setOnClickListener(this);
        }

        /**
         * Used in built method to get all the information for event details
         * @param v object for view
         */
        @Override
        public void onClick(View v) {
            Ticket ticket = tickets.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
            intent.putExtra("name", ticket.getName());
            intent.putExtra("startDate", ticket.getStartDate());
            intent.putExtra("time", ticket.getTime());
            intent.putExtra("location", ticket.getLocation());
            intent.putExtra("priceRange", ticket.getPriceRange());
            intent.putExtra("ticketLimit", ticket.getTicketLimit());
            intent.putExtra("ticketInfo", ticket.getTicketInfo());
            intent.putExtra("imageUrl", ticket.getImageUrl());
            intent.putExtra("purchaseTicketUrl", ticket.getTicketPurchaseUrl());
            v.getContext().startActivity(intent);
        }

        /**
         * This is the method for loading the items from the previous page
         * and also the image
         * @param ticket object for ticket
         */
        public void bind(Ticket ticket) {
            textViewEventName.setText(ticket.getName());
            textViewStartDate.setText("Start Date: " + ticket.getStartDate());
            //textViewPriceRange.setText("Price Range: " + ticket.getPriceRange());
            // Load image using Picasso
            Picasso.get().load(ticket.getImageUrl()).into(imageViewEvent);
        }
    }
}
