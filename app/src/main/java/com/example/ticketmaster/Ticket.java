package com.example.ticketmaster;

/** This is the class for ticketmaster event details which we are taking from the web server
 * and storing it for displaying them later on
 */
public class Ticket {
    /**
     * This is the variable for event name
     */
    private String name;

    /**
     * This is the variable for event date
     */
    private String startDate;

    /**
     * This is the variable for event time
     */
    private String time;

    /**
     * This is the variable for event location
     */
    private String location;

    /**
     * This is the variable for event info
     */
    private String ticketInfo;

    /**
     * This is the variable for price limit
     */
    private String ticketLimit;

    /**
     * This is the variable for event price
     */
    private String priceRange;

    /**
     * This is the variable for event url to book the tickets
     */
    private String ticketPurchaseUrl;

    /**
     * This is the variable for event image
     */
    private String imageUrl;

    /**
     * The basic constructor to add everything to our page
     * @param name object for event name
     * @param startDate object for event date
     * @param priceRange object for event price
     * @param ticketPurchaseUrl object for event url for booking
     * @param imageUrl object for event image
     * @param time object for event time
     * @param location object for event location
     * @param ticketInfo object for event information
     * @param ticketLimit object for price limit
     */
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

    /**
     * Getter for getting the event name
     * @return event name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for setting the event name
     * @param name object for event name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for getting the event date
     * @return event date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Getter for getting the event price
     * @return event price
     */
    public String getPriceRange() {
        return priceRange;
    }


    /**
     * Getter for getting the event url to book ticket
     * @return event url to book ticket
     */
    public String getTicketPurchaseUrl() {
        return ticketPurchaseUrl;
    }

    /**
     * Getter for getting the event time
     * @return event time
     */
    public String getTime() {
        return time;
    }

    /**
     * Getter for getting the event location
     * @return event location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for getting the event information
     * @return event information
     */
    public String getTicketInfo() {
        return ticketInfo;
    }

    /**
     * Getter for getting the price limit
     * @return price limit
     */
    public String getTicketLimit() {
        return ticketLimit;
    }

    /**
     * Getter for getting the event image
     * @return event image
     */
    public String getImageUrl() {
        return imageUrl;
    }

}
