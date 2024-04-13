package com.example.ticketmaster;

public class Ticket {
    private String name;
    private String startDate;
    private String time;
    private String location;
    private String ticketInfo;
    private String ticketLimit;
    private String priceRange;

    private String ticketPurchaseUrl;
    private String imageUrl;

    public Ticket(String name, String startDate, String priceRange, String ticketPurchaseUrl, String imageUrl, String time, String location, String ticketInfo, String ticketLimit) {
        this.name = name;
        this.startDate = startDate;
        this.priceRange = priceRange;
        this.ticketPurchaseUrl = ticketPurchaseUrl;
        this.imageUrl = imageUrl;
        this.time = time;
        this.location = location;
        this.ticketInfo = ticketInfo;
        this.ticketLimit = ticketLimit;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }


    public String getPriceRange() {
        return priceRange;
    }


    public String getTicketPurchaseUrl() {
        return ticketPurchaseUrl;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public String getTicketLimit() {
        return ticketLimit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
