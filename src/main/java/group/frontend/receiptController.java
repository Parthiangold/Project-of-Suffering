package group.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class receiptController {

    @FXML
    private Label bookingInfo;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button menuButton;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;

    public receiptController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
    }

    // Displays the created booking on initialisation
    @FXML
    public void initialize() {
        Booking bookingObj = bookings.get(bookings.size() - 1);
        bookingInfo.setText(bookingObj.toString());
    }

    // Saves objects to files on exit
    @FXML
    void exitProgram() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Logs out of customer's account and sends the user back to the main menu
    @FXML
    void logout() throws IOException {
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

            // Formats all bookings into "bookings_<CustomerNo>.txt" (passes if no customer is logged in)
            if (cNum != 0) {
                String customerBookingFilename = "src/main/resources/group/bookings_" + cNum;
                formatter = new Formatter(customerBookingFilename + ".txt");
                for (Booking b : bookings) {
                    b.outputData(formatter);
                }
                formatter.close();
            }
            System.out.println("\nData has successfully been saved.");
        }
        // Catches error if any of the files don't exist
        catch (FileNotFoundException e) {
            System.out.println("Error 404: file 'flights.txt', 'seatings.txt' or 'bookings_<CustomerNo>.txt' not found.");
            e.printStackTrace();
        }

        // Following the saving of files, the bookings ArrayList is cleared for use by the different customer that logs in
        bookings.clear();
        cNum = 0;
        MainApplication.getApplicationInstance().setCNum(cNum);

        // Scene changes to login display
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/loginView.fxml", logoutButton.getScene());
    }

    // Switches from receiptView to menuView following the clicking of the "Return to Menu" button
    @FXML
    private void switchToMenuView() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/menuView.fxml", logoutButton.getScene());
    }

}
