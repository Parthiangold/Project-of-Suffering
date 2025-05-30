package group.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class flightController {

    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label flightDisplay;

    @FXML
    private Button logoutButton;

    @FXML
    private Label seatingDisplay;

    @FXML
    private Button addSeatButton;

    @FXML
    private Button removeSeatButton;

    @FXML
    private Button payBookingButton;

    @FXML
    private CheckBox wifiCheckbox;

    @FXML
    private CheckBox fndCheckbox;

    private ObservableList<String> aSeatList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> availableSeatList = new ListView<String>(aSeatList);

    private ObservableList<String> sSeatList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> selectedSeatList = new ListView<String>(sSeatList);

    @FXML
    private Label errorMessage;

    @FXML
    private Label numberMessage;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;
    private ArrayList<Flight> flightResults;
    private ArrayList<Seating> seatingResults;
    private int adult;
    private int child;
    private Flight flightObj;
    private Seating seatingObj;

    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;

    public flightController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating>seatingResults, int adult, int child, Flight flightObj, Seating seatingObj) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        this.flightResults = flightResults;
        this.seatingResults = seatingResults;
        this.adult = adult;
        this.child = child;
        this.flightObj = flightObj;
        this.seatingObj = seatingObj;
        this.inFlightWiFi = false;
        this.inFlightFoodAndDrinks = false;
    }

    // Shows details of the flight and sets label text on controller initialisation
    @FXML
    public void initialize() {
        displayFlightDetails();
        numberMessage.setText("# of Seats to Add: " + (adult + child - sSeatList.size()));
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

    // Returns to the results view
    @FXML
    public void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child);
		selector.selectScene("/group/resultsView.fxml", logoutButton.getScene());
    }

    // Displays further details about the flight and seating
    @FXML
    public void displayFlightDetails() {
        // Sets labels to display flight and seating info
        flightDisplay.setText(flightObj.overviewString());
        seatingDisplay.setText(seatingObj.overviewString());

        // Adds the available seats listed in the seatingObj into the ListView for available seats
        for (int i = 0; i < seatingObj.getAvailableSeats().size(); i++) {
            String seat = seatingObj.getAvailableSeats().get(i);
            aSeatList.add(seat);
            availableSeatList.getItems().add(seat);
        }
    }

    // Adds the selected seat to the selected seats list
    @FXML
    public void addSeat() {
        // Error message appears if user has already selected the max amount of seats
        if ((adult + child - sSeatList.size()) == 0) {
            errorMessage.setText("No more seats can be selected.");
        } else {
            String selectedSeat = availableSeatList.getSelectionModel().getSelectedItem();
            int selectedID = availableSeatList.getSelectionModel().getSelectedIndex();
            // If user hasn't selected a seat, then an error message appears prompting the user to do so
            if (selectedSeat == null) {
                errorMessage.setText("Select a seat in the list first.");
            } else {
                // Selected seat is added to the ListView for selected seats and removed from the ListView for available seats
                selectedSeatList.getItems().add(selectedSeat);
                sSeatList.add(selectedSeat);
                availableSeatList.getItems().remove(selectedID);
                aSeatList.remove(selectedID);
            }
        }
        // Label for showing remaining seats to add is updated
        numberMessage.setText("# of Seats to Add: " + (adult + child - sSeatList.size()));
    }

    // Removes the selected seat from the selected seats list
    @FXML
    public void removeSeat() {
        // Error message appears if user doesn't have seats left in the seating list
        if ((sSeatList.size() == 0)) {
            errorMessage.setText("No more seats can be removed.");
        } else {
            String selectedSeat = selectedSeatList.getSelectionModel().getSelectedItem();
            int selectedID = selectedSeatList.getSelectionModel().getSelectedIndex();
            // If user hasn't selected a seat, then an error message appears prompting the user to do so
            if (selectedSeat == null) {
                errorMessage.setText("Select a seat in the list first.");
            } else {
                // Selected seat is added to the ListView for available seats and removed from the ListView for selected seats
                availableSeatList.getItems().add(selectedSeat);
                aSeatList.add(selectedSeat);
                selectedSeatList.getItems().remove(selectedID);
                sSeatList.remove(selectedID);
            }
        }
        // Label for showing remaining seats to add is updated
        numberMessage.setText("# of Seats to Add: " + (adult + child - sSeatList.size()));
    }

    // Sets In-Flight WiFi to true or false depending on if the checkbox of the same name is checked or not
    @FXML
    public void checkWifi() {
        if (wifiCheckbox.isSelected()) {
            inFlightWiFi = true;
        } else {
            inFlightWiFi = false;
        }
    }

    // Sets In-Flight Food and Drinks to true or false depending on if the checkbox of the same name is checked or not
    @FXML
    public void checkFnd() {
        if (fndCheckbox.isSelected()) {
            inFlightFoodAndDrinks = true;
        } else {
            inFlightFoodAndDrinks = false;
        }
    }

    // Pay booking method checks to see if it is valid to proceed with the transaction part of the booking (i.e. switch to transactionView)
    @FXML
    public void payBooking() throws IOException {
        // Transfers from ObservableList to Array for use in the backend
        String[] bookedSeats = new String[adult + child];
        if (sSeatList.size() == adult + child) {
            for (int i = 0; i < sSeatList.size(); i++) {
                bookedSeats[i] = sSeatList.get(i);
            }
            
            // Adds to the pricing if one or both services are checked
            double price = (adult * seatingObj.getAdultPricing()) + (child * seatingObj.getChildPricing());
            if (inFlightWiFi) {
                price += 5;
            }
            if (inFlightFoodAndDrinks) {
                price += 10;
            }
            switchToTransactionView(bookedSeats, price);
        } 
        // If the user hasn't selected the number of seats equal to the inputted number of people boarding, an error message returns
        else {
            errorMessage.setText("You must select enough seats.");
        }
    }

    // Switches from flightView to transactionView following the activation of the "Pay Booking" button
    @FXML
    private void switchToTransactionView(String[] bookedSeats, double price) throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj, bookedSeats, inFlightWiFi, inFlightFoodAndDrinks, price);
		selector.selectScene("/group/transactionView.fxml", logoutButton.getScene());
    }

}
