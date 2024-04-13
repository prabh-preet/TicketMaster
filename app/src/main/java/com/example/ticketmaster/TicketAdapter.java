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

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.EventViewHolder> {

    private List<Ticket> tickets;

    public void setEvents(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (tickets != null && position < tickets.size()) {
            Ticket ticket = tickets.get(position);
            holder.bind(ticket);
        }
    }

    @Override
    public int getItemCount() {
        return tickets != null ? tickets.size() : 0;
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewEventName;
        private TextView textViewStartDate;
        private TextView textViewPriceRange;
        private ImageView imageViewEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.name_text_view);
            textViewStartDate = itemView.findViewById(R.id.start_date_text_view);
            textViewPriceRange = itemView.findViewById(R.id.price_range_text_view);
            imageViewEvent = itemView.findViewById(R.id.event_image_view);

            // Set OnClickListener for the entire item view
            itemView.setOnClickListener(this);
        }

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

        public void bind(Ticket ticket) {
            textViewEventName.setText(ticket.getName());
            textViewStartDate.setText("Start Date: " + ticket.getStartDate());
            textViewPriceRange.setText("Price Range: " + ticket.getPriceRange());
            // Load image using Picasso
            Picasso.get().load(ticket.getImageUrl()).into(imageViewEvent);
        }
    }
}
