import java.util.*;

public class Seating {
    private String flightNo, seatClassType;
    private double adultPricing, childPricing;
    private ArrayList<String> availableSeats = new ArrayList<String>();
    private int availableNo;

    // Default constructors initialise class and attributes
    public Seating() {}
    public Seating(String fN, String sCT, double aP, double cP, int aN) {
        this.flightNo = fN;
        this.seatClassType = sCT;
        this.adultPricing = aP;
        this.childPricing = cP;
        this.availableNo = aN;
    }

    // Reads the text file and stores them into attributes
    public void inputData(Scanner reader) {
        flightNo = reader.next();
        seatClassType = reader.next();
        adultPricing = reader.nextDouble();
        childPricing = reader.nextDouble();
        while (! reader.hasNextInt()) {
            availableSeats.add(reader.next());
        }
        availableNo = reader.nextInt();
    }

    // Writes objects to a text file
    public void outputData(Formatter formatter) {
        //e
    }

    // Sets the number of available seats
    public void setAvailableNo(int num) {
        this.availableNo = num;
    }

    // Sets the list of seats to either add or delete a value
    public void setAvailableSeats() {
        //e
    }

    // Gets the flight number of the seating
    public String getFlightNo() {
        return this.flightNo;
    }

    // Gets the class type of the seating
    public String getSeatClassType() {
        return this.seatClassType;
    }

    // Gets the number of available seats
    public int getAvailableNo() {
        return this.availableNo;
    }

    // Gets the adult pricing of the seating
    public double getAdultPricing() {
        return this.adultPricing;
    }

    // Gets the child pricing of the seating
    public double getChildPricing() {
        return this.childPricing;
    }

    // Gets the list of available seats
    public void getAvailableSeats() {
        //e
    }

    // String output of an object
    public String toString() {
        return String.format("\nFlightNo: %s\n" 
        + "Seat Class Type: %s\n"
        + "Available Number of Seats: %d\n"
        + "Adult Price: %.2f\n"
        + "Child Price: %.2f\n"
        + "Available Seats: %s", flightNo, seatClassType, availableNo, adultPricing, childPricing, availableSeats);
    }
}
