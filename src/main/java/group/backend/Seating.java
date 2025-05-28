package group.backend;

import java.util.*;

public class Seating implements MyFileIO {
    private String flightNo, seatClassType;
    private double adultPricing, childPricing;
    private ArrayList<String> availableSeats = new ArrayList<String>();
    private int availableNo;

    // Default constructors initialise class and attributes
    public Seating() {}
    public Seating(String fN, String sCT, double aP, double cP, String[] aS, int aN) {
        this.flightNo = fN;
        this.seatClassType = sCT;
        this.adultPricing = aP;
        this.childPricing = cP;
        availableSeats = new ArrayList<String>();
        for (int i = 0; i < aS.length; i++) {
            availableSeats.add(aS[i]);
        }
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
        String seatString = "";
        for (int i = 0; i < availableSeats.size(); i++) {
            seatString += availableSeats.get(i) + ",";
        }
        formatter.format("%s,%s,%.2f,%.2f,%s%d\n", flightNo, seatClassType, adultPricing, childPricing, seatString, availableNo);
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
    public List<String> getAvailableSeats() {
        return availableSeats;
    }

    // String output of an object
    public String toString() {
        return String.format("\nFlightNo: %s\n" 
        + "Seat Class Type: %s\n"
        + "Adult Price: %.2f\n"
        + "Child Price: %.2f\n"
        + "Available Number of Seats: %d\n"
        + "Available Seats: %s", flightNo, seatClassType, adultPricing, childPricing, availableNo, availableSeats);
    }

    // Marks a seat as booked by removing it from the availableSeats list
    public void markSeatAsBooked(String seat) {
        if (availableSeats.contains(seat)) {
            availableSeats.remove(seat);
            availableNo--; // Decrement available seat count
        } else {
            System.out.println("Seat " + seat + " is already booked or does not exist.");
        }
    }

    // Marks a seat as available by adding it back to the availableSeats list
    public void markSeatAsAvailable(String seat) {
        if (!availableSeats.contains(seat)) {
            availableSeats.add(seat);
            availableNo++; // Increment available seat count
        } else {
            System.out.println("Seat " + seat + " is already available.");
        }
    }
}