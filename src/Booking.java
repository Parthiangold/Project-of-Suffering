import java.util.*;

public class Booking {
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
        // This was done in no particular order as it originally was edited from a previous iteration
        bookingNo = scanner.nextInt();
        adultNo = scanner.nextInt();
        childNo = scanner.nextInt();
        price = scanner.nextDouble();
        purchaseDate = scanner.nextLine();
        bookingNo = scanner.nextInt();
        seatClassType = scanner.next();
        inFlightWiFi = scanner.nextBoolean();
        inFlightFoodAndDrinks = scanner.nextBoolean();
        bookedSeats = scanner.nextLine().split(" ");
        flightNo = scanner.nextLine();
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
