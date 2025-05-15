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
        bookedSeats = tokens[5].split(",");
        inFlightWiFi = Boolean.parseBoolean(tokens[6]);
        inFlightFoodAndDrinks = Boolean.parseBoolean(tokens[7]);
        price = Double.parseDouble(tokens[8]);
        purchaseDate = tokens[9];

    }

    @Override
    public String toString(Formatter formatter) {
        return "BookingNo: " + bookingNo;


    }

    // Output for display and/or debugging
    public void outputData(Formatter formatter) {
        formatter.format("Booking #: %d\n adults: %d\n children: %d \n price: %.2f \n date: %s%n", bookingNo, adultNo, childNo, price, purchaseDate);
        formatter.format("Flight: %s\n Booked Seats: %s\n Seat Class: %s\n WiFi: %b\n Food and Drinks: %b%n", flightNo, bookedSeats,seatClassType, inFlightWiFi, inFlightFoodAndDrinks);
    }
    

    // Get Booking number method
    public int getBookingNo() {
        return bookingNo;
    }

}
