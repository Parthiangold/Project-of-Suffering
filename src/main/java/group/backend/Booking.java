package group.backend;

import java.util.*;

public class Booking implements MyFileIO {
    private int adultNo;
    private int childNo;
    private double price;
    private String purchaseDate;
    private int bookingNo;
    public String seatClassType;
    public String flightNo;
    public String[] bookedSeats;
    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;

    //Constructors
    public Booking() {
    }

    /* FlightNo will be referencing the attribute in the flight class in the future
    the main focus is on getting a baseline complete in the first build of the program
     */

    public Booking(int bookingNo, String flightNo, String[] bookedSeats, int adultNo, int childNo, double price, String purchaseDate, String seatClassType, boolean inFlightWiFi, boolean inFlightFoodAndDrinks) {
        this.bookingNo = bookingNo;//generated
        this.flightNo = flightNo;
        this.bookedSeats = bookedSeats;
        this.adultNo = adultNo;
        this.childNo = childNo;
        this.price = price;
        this.purchaseDate = purchaseDate;//generated
        this.seatClassType = seatClassType;
        this.inFlightWiFi = inFlightWiFi;
        this.inFlightFoodAndDrinks = inFlightFoodAndDrinks;
    }

    // Had to change how input was handled from a token-based system to the .nextline() standard used by other classes
    @Override
    public void inputData(Scanner scanner) {
        bookingNo = scanner.nextInt();
        flightNo = scanner.next();
        bookedSeats = scanner.next().split(";");
        adultNo = scanner.nextInt();
        childNo = scanner.nextInt();
        price = scanner.nextDouble();
        purchaseDate = scanner.next();
        seatClassType = scanner.next();
        inFlightWiFi = scanner.nextBoolean();
        inFlightFoodAndDrinks = scanner.nextBoolean();
    }


    // Output for display and/or debugging
    @Override
    public void outputData(Formatter formatter) {
        formatter.format("%d,%s,%s,%d,%d,%.2f,%s,%s,%b,%b\n",
                bookingNo,
                flightNo,
                String.join(";", bookedSeats),
                adultNo,
                childNo,
                price,
                purchaseDate,
                seatClassType,
                inFlightWiFi,
                inFlightFoodAndDrinks);
    }

    // toString has been debugged and now is displaying the correct data
    @Override
    public String toString() {
        String seats = String.join(";", bookedSeats);
        return String.format(
                "Booking No: %d\nFlight No: %s\nBooked Seats: %s\nAdults: %d\nChildren: %d\nPrice: %.2f\nPurchase Date: %s\nSeat Class: %s\nIn Flight WiFi: %b\nIn Flight Food and Drinks: %b\n",
                bookingNo,
                flightNo,
                seats,
                adultNo,
                childNo,
                price,
                purchaseDate,
                seatClassType,
                inFlightWiFi,
                inFlightFoodAndDrinks);
    }

    // Get Booking number method
    public int getBookingNo() {
        return bookingNo;
    }

    // Gets the class type of the seating
    public String getFlightNo() {
        return this.flightNo;
    }

    // Get adult number
    public int getAdult() {
        return adultNo;
    }

    // Get child number
    public int getChild() {
        return childNo;
    }

    // Getter method for seatClassType
    public String getSeatClassType() {
        return seatClassType;
    }

    // Get booked seats
    public String[] getBookedSeats() {
        return bookedSeats;
    }

    // Get price
    public double getPrice() {
        return price;
    }

    // Getter method for in-flight Wi-Fi
    public boolean isInFlightWiFi() {
        return inFlightWiFi;
    }

    // Getter method for in-flight food and drinks
    public boolean isInFlightFoodAndDrinks() {
        return inFlightFoodAndDrinks;
    }

    //Setter method for seat listing
    public void setBookedSeats(String[] seats) {
        bookedSeats = seats;
    }

    //Setter method for seat classifications
    public void setSeatClassType(String seatType) {
        seatClassType = seatType;
    }

    //Setter method for whether a customer purchased Wi-Fi perks
    public void setInFlightWiFi(boolean WiFi) {
        inFlightWiFi = WiFi;
    }

    //Setter method for food and drinks
    public void setInFlightFoodAndDrinks(boolean FoodAndDrinks) {
        inFlightFoodAndDrinks = FoodAndDrinks;
    }

    //temporary skeleton for set price (Perhaps dynamic pricing is a job for the main method?) -N
    public void setPrice(double updatedPrice) {
        price = updatedPrice;
    }


}
