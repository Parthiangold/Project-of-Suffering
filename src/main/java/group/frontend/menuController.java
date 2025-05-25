package group.frontend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class menuController {

    @FXML
    private TextField adultInput;

    @FXML
    private TextField arrivalInput;

    @FXML
    private TextField bookingInput;

    @FXML
    private TextField childInput;

    @FXML
    private TextField dateInput;

    @FXML
    private TextField departureInput;

    @FXML
    private Button exitButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button searchButton;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;

    public menuController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
    }

    // Debugging method used to test if file exports can save data made within the program
    public void testData() {
        String[] bookingData = {"12B", "12C"};
        Booking bookingObj = new Booking(4, "FDA124", bookingData, 2, 0, 1400.00, "2025-05-12", "Economy", false, false);
        bookings.add(bookingObj);
        Flight flightObj = new Flight("FDA800", "Sydney", "Tokyo", "2025-05-12", "2025-05-12", "07:00 GMT+10", "16:00 GMT+9", "None", "10h", "Boeing 787");
        flights.add(flightObj);
        String[] seatingData = {"24D", "34D", "48D"};
        Seating seatingObj = new Seating("FDA800", "Economy", 320.00, 160.00, seatingData, 3);
        seatings.add(seatingObj);
    }

    // Saves objects to files on exit
    @FXML
    public void exitProgram() {
        try {
            // Formats all flights into "flights.txt"
            Formatter formatter = new Formatter("src/main/resources/group/flights.txt");
            for (Flight f : flights) {
                f.outputData(formatter);
            }
            formatter.close();

            // Formats all seatings into "seatings.txt"
            formatter = new Formatter("src/main/resources/group/seatings.txt");
            for (Seating s : seatings) {
                s.outputData(formatter);
            }
            formatter.close();

            // Formats all bookings into "bookings_<CustomerNo>.txt"
            String customerBookingFilename = "src/main/resources/group/bookings_" + cNum;
            formatter = new Formatter(customerBookingFilename + ".txt");
            for (Booking b : bookings) {
                b.outputData(formatter);
            }
            formatter.close();
            System.out.println("\nData has successfully been saved.");
        }
        // Catches error if any of the files don't exist
        catch (FileNotFoundException e) {
            System.out.println("Error 404: file 'flights.txt', 'seatings.txt' or 'bookings_<CustomerNo>.txt' not found.");
            e.printStackTrace();
        }
        System.out.println("The program will proceed to exit");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
