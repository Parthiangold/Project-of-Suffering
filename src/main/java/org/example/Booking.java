package org.example;

import java.util.*;

public class Booking {
    private int adultAmount;
    private int childAmount;
    private double price;
    private String bookingDate;
    private String dateTime;
    private String email;
    private int bookingNo;
    private int customerNo;
    private Flight bookedFlight;
    private String[] bookedSeats;
    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;

    //Empty constructor class
    public Booking () {}

    // Constructor class
    public Booking(int adultAmount, int childAmount, double price, String bookingDate, String dateTime, String email, int bookingNo, int customerNo, Flight bookedFlight, String[] bookedSeats, boolean inFlightWiFi, boolean inFlightFoodAndDrinks) {
        this.adultAmount = adultAmount;
        this.childAmount = childAmount;
        this.price = price;
        this.bookingDate = bookingDate;
        this.dateTime = dateTime;
        this.email = email;
        this.bookingNo = bookingNo;
        this.customerNo = customerNo;
        this.bookedFlight = bookedFlight;
        this.bookedSeats = bookedSeats;
        this.inFlightWiFi = inFlightWiFi;
        this.inFlightFoodAndDrinks = inFlightFoodAndDrinks;
    }

    // Get Booking number method
    public int getBookingNo() {
        return bookingNo;
    }
    // Get email method
    public String getEmail() {
        return email;
    }
    public String toString(Formatter formatter) {
        return String.valueOf(formatter.format(String.valueOf(this)));

    }

}
