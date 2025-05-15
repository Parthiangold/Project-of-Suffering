import java.util.*;

public class Booking implements MyFileIO {
    private int adultNo;
    private int childNo;
    private double price;
    private String purchaseDate;
    private int bookingNo;
    private String seatClassType;
    private String flightNo;
    private String[] bookedSeats;
    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;

    //Constructors
    public Booking () {}

    /* FlightNo will be referencing the attribute in the flight class in the future
    the main focus is on getting a baseline complete in the first build of the program
     */

    public Booking(int adultNo, int childNo, double price, String purchaseDate, int bookingNo, String seatClassType, String flightNo, String[] bookedSeats, boolean inFlightWiFi, boolean inFlightFoodAndDrinks) {
        this.adultNo = adultNo;
        this.childNo = childNo;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.bookingNo = bookingNo;
        this.flightNo = flightNo;
        this.bookedSeats = bookedSeats;
        this.seatClassType = seatClassType;
        this.inFlightWiFi = inFlightWiFi;
        this.inFlightFoodAndDrinks = inFlightFoodAndDrinks;
    }
    // Parses input from file (frontend loads inputs to file this method unloads to backend)
    @Override
    public void inputData(Scanner scanner) {
        /* Decided to change how input was handled to a token based system.
        this is to help automate the collection of values for the bookedSeats attribute
         */
        if (!scanner.hasNextLine()) return;
        String line = scanner.nextLine();
        String[] tokens = line.split(",");

        parseTokens(tokens);
    }
    // Adding new parseTokens method to Bookings
    private void parseTokens(String[] tokens) {
        bookingNo = Integer.parseInt(tokens[0]);
        adultNo  = Integer.parseInt(tokens[1]);
        childNo = Integer.parseInt(tokens[2]);
        flightNo = tokens[3];
        seatClassType = tokens[4];
        bookedSeats = tokens[5].split(";");
        inFlightWiFi = Boolean.parseBoolean(tokens[6]);
        inFlightFoodAndDrinks = Boolean.parseBoolean(tokens[7]);
        price = Double.parseDouble(tokens[8]);
        purchaseDate = tokens[9];

    }

    // Output for display and/or debugging
    @Override
    public void outputData(Formatter formatter) {
        formatter.format("%d, %d, % d, %s, %s, %s, %b, %b, %.2f, %s",
                bookingNo, adultNo, childNo, flightNo, seatClassType,
                String.join(";", bookedSeats), inFlightWiFi,
                inFlightFoodAndDrinks, price, purchaseDate);
    }
    //Finally was able to fix the toString method
    @Override
    public String toString(Formatter formatter) {
        String seats = String.join(",", bookedSeats);
        return String.format(
                "Booking No: %d\nFlight No: %s\nBooked Seats: %s\nAdults: %d\nChildren: %d\nPrice: %.2f\nPurchase Date: %s\nSeat Class: %s\n In Flight WiFi: %b\nIn Flight Food and Drinks: %b\n",
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
    //Finally I can start working on getters and setters
    

    // Get Booking number method
    public int getBookingNo() {
        return bookingNo;
    }

    //Setter method for seat listing
    public void setBookedSeats(String[] seats) {
        bookedSeats = seats;
    }

    //Setter method for seat classifications
    public void setSeatClassType(String seatType) {
        seatClassType = seatType;
    }

    //Setter method for whether customer purchased Wi-Fi perks
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
