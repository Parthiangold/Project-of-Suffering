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
    public Booking() {
    }

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
    public String toString(Formatter formatter) {
        String seats = String.join(";", bookedSeats);
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

    // Fixes the testing issue where java wouldn't recognise parameterised toString for System.out.println
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

    //Finally, I can start working on getters and setters


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
