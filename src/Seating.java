import java.util.*;

public class Seating {
    private String flightNo, seatClassType;
    private int availableNo;
    private double adultPricing, childPricing;
    private ArrayList<String> availableSeats;

    // Default constructors initialise class and attributes
    public Seating() {}
    public Seating(String fN, String sCT, int aN, double aP, double cP, String[] aS) {
        this.flightNo = fN;
        this.seatClassType = sCT;
        this.availableNo = aN;
        this.adultPricing = aP;
        this.childPricing = cP;
        this.availableSeats = new ArrayList<String>();
    }

    // Reads the text file and stores them into attributes
    public void inputData(Scanner reader) {
        flightNo = reader.next();
        seatClassType = reader.next();
        availableNo = reader.nextInt();
        adultPricing = reader.nextDouble();
        childPricing = reader.nextDouble();
        while (reader.hasNext()) {
            availableSeats.add(reader.next());
        }
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

    // String output of object
    public String toString() {
        return String.format("%s",flightNo);
    }
}
