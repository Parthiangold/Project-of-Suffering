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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class bookingController {

    @FXML
    private Label bookingDisplay;

    @FXML
    private Button backButton;

    @FXML
    private Button cancelBookingButton;

    @FXML
    private Button changeSeatButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button updateClassButton;

    @FXML
    private Button updateServicesButton;

    @FXML
    private VBox updateBox;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;
    private Booking bookingObj;

    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;

    public bookingController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, 
    ArrayList<Booking> bookings, int cNum, Booking bookingObj) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        this.bookingObj = bookingObj;
    }

    // Displays the booking info on initialisation
    @FXML
    public void initialize() {
        displayBooking();
    }

    // Logs out of the customer's account and sends the user back to the main menu
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
        selector.selectScene("/group/loginView.fxml", updateClassButton.getScene());
    }

    // Displays the selected booking the end user inputs from the main menu.
    @FXML
    public void displayBooking() {
        bookings.stream()
                .filter(b -> b.getBookingNo() == bookingObj.getBookingNo())
                .findFirst()
                .ifPresentOrElse(
                        b -> bookingDisplay.setText(b.toString()),
                        () -> bookingDisplay.setText("Booking not found")
                );
    }

    // Navigates to change seat scene
    @FXML
    void changeSeat() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, bookingObj);
        selector.selectScene("/group/changeSeatView.fxml", logoutButton.getScene());
    }

    // Displays the options for the in-flight services
    @FXML
    public void updateServices() {
        // New Label titled infoLabel
        Label infoLabel = new Label("Update In-Flight Services:");
        infoLabel.setStyle("-fx-font-size: 14; -fx-underline: true");

        // Adds the infoLabel to the VBox
        updateBox.getChildren().clear();
        updateBox.getChildren().add(infoLabel);

        // Creates 2 checkboxes each for selecting an in-flight service
        CheckBox wifiCheckbox = new CheckBox("In-Flight Wifi: $5");
        CheckBox fndCheckbox = new CheckBox("In-Flight Food & Drinks: $10");

        // Adds actions to the checkboxes
        wifiCheckbox.setOnAction(e -> checkWifi(wifiCheckbox));
        fndCheckbox.setOnAction(e -> checkFnd(fndCheckbox));

        // Presets the checks on the checkboxes if the user has already bought for some services
        if (bookingObj.isInFlightWiFi()) {
            wifiCheckbox.setSelected(true);
        }
        if (bookingObj.isInFlightFoodAndDrinks()) {
            fndCheckbox.setSelected(true);
        }

        // Adds the newly created checkboxes to the VBox
        updateBox.getChildren().add(wifiCheckbox);
        updateBox.getChildren().add(fndCheckbox);

        // Creates save button
        Button saveServices = new Button("Save");
        saveServices.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #1f8bff; -fx-text-fill: #ffffff");
        saveServices.setOnAction(e -> changeServices(wifiCheckbox, fndCheckbox));

        // Creates cancel button
        Button cancelServices = new Button("Cancel");
        cancelServices.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #ff5656; -fx-text-fill: #ffffff");
        cancelServices.setOnAction(e -> cancel());

        // Adds the newly created buttons to the VBox
        updateBox.getChildren().add(saveServices);
        updateBox.getChildren().add(cancelServices);
    }

    // Sets In-Flight WiFi to true or false depending on if the checkbox of the same name is checked or not
    @FXML
    public void checkWifi(CheckBox wifiCheckbox) {
        if (wifiCheckbox.isSelected()) {
            inFlightWiFi = true;
        } else {
            inFlightWiFi = false;
        }
    }

    // Sets In-Flight Food and Drinks to true or false depending on if the checkbox of the same name is checked or not
    @FXML
    public void checkFnd(CheckBox fndCheckbox) {
        if (fndCheckbox.isSelected()) {
            inFlightFoodAndDrinks = true;
        } else {
            inFlightFoodAndDrinks = false;
        }
    }

    // After making changes to services, they are changed on the booking
    @FXML
    public void changeServices(CheckBox wifiCheckBox, CheckBox fndCheckBox) {
        // Checks the statuses of the boolean attributes
        if (inFlightWiFi) {
            if (bookingObj.isInFlightWiFi() != true) {
                bookingObj.setPrice(bookingObj.getPrice() + 5);
            }
            bookingObj.setInFlightWiFi(true);
        } else {
            if (bookingObj.isInFlightWiFi()) {
                bookingObj.setPrice(bookingObj.getPrice() - 5);
            }
            bookingObj.setInFlightWiFi(false);
        }
        if (inFlightFoodAndDrinks) {
            if (bookingObj.isInFlightFoodAndDrinks() != true) {
                bookingObj.setPrice(bookingObj.getPrice() + 10);
            }
            bookingObj.setInFlightFoodAndDrinks(true);
        } else {
            if (bookingObj.isInFlightFoodAndDrinks()) {
                bookingObj.setPrice(bookingObj.getPrice() - 10);
            }
            bookingObj.setInFlightFoodAndDrinks(false);
        }

        // Clears VBox
        updateBox.getChildren().clear();

        // Updates booking info on display
        displayBooking();
    }

    // Asks the user again if they're sure they want to cancel their booking
    @FXML
    public void cancelCheck() {
        // 2 new labels
        Label infoLabel = new Label("Booking Cancellation:");
        infoLabel.setStyle("-fx-font-size: 14; -fx-underline: true");
        Label check = new Label("Are you sure you want to cancel the booking?");
        check.setWrapText(true);

        // Adds the labels to the VBox
        updateBox.getChildren().clear();
        updateBox.getChildren().add(infoLabel);
        updateBox.getChildren().add(check);

        // Creates yes button
        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #ff5656; -fx-text-fill: #ffffff");
        yesButton.setOnAction(e -> {
            try {
                cancelBooking();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Creates no button
        Button noButton = new Button("No");
        noButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #1f8bff; -fx-text-fill: #ffffff");
        noButton.setOnAction(e -> cancel());

        // Adds the newly created buttons to the VBox
        updateBox.getChildren().add(yesButton);
        updateBox.getChildren().add(noButton);
    }

    // Cancel bookings controller method works by fetching BookingNo from the backend
    @FXML
    void cancelBooking() throws IOException {
        Optional<Booking> bookingToRemove = bookings.stream()
                .filter(b -> b.getBookingNo() == bookingObj.getBookingNo())
                .findFirst();

        // Checks to see if the booking is present, then proceeds to remove the booking from the ArrayList
        if (bookingToRemove.isPresent()) {
            bookings.remove(bookingToRemove.get());
            System.out.println("Booking cancelled successfully.");
            back();
        } else {
            System.out.println("Booking not found to cancel.");
        }
    }

    // Cancelling option clears VBox
    @FXML
    public void cancel() {
        updateBox.getChildren().clear();
    }

    // Returns to the menu view
    @FXML
    public void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
        selector.selectScene("/group/menuView.fxml", logoutButton.getScene());
    }

    // Saves objects to files on exit
    @FXML
    public void exitProgram() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}