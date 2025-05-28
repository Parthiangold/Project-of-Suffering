package group.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.regex.Pattern;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class transactionController {

    @FXML
    private TextField addressInput;

    @FXML
    private Button backButton;

    @FXML
    private Button bookFlightButton;

    @FXML
    private TextField cardNameInput;

    @FXML
    private TextField cardNumberInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField countryInput;

    @FXML
    private Label errorMessage1;

    @FXML
    private Label errorMessage2;

    @FXML
    private Button exitButton;

    @FXML
    private TextField expirationInput;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField postcodeInput;

    @FXML
    private TextField securityCodeInput;

    @FXML
    private TextField stateInput;

    @FXML
    private Label priceMessage;

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
    private String[] bookedSeats;
    private boolean inFlightWiFi;
    private boolean inFlightFoodAndDrinks;
    private double price;

    public transactionController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, 
    ArrayList<Flight> flightResults, ArrayList<Seating>seatingResults, int adult, int child, Flight flightObj, Seating seatingObj, String[] bookedSeats, 
    boolean inFlightWiFi, boolean inFlightFoodAndDrinks, double price) {
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
        this.bookedSeats = bookedSeats;
        this.inFlightWiFi = inFlightWiFi;
        this.inFlightFoodAndDrinks = inFlightFoodAndDrinks;
        this.price = price;
    }

    // Shows the total price of the booking on load
    @FXML
    public void initialize() {
        priceMessage.setText("Booking Price: $" + price);
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

    // Returns to the flight view
    @FXML
    public void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj);
		selector.selectScene("/group/flightView.fxml", logoutButton.getScene());
    }

    // Once the user has clicked the book flight button, the program checks to see if valid info has been filled in each of the text fields, before confirming the booking
    @FXML
    public void bookFlight() throws IOException {
        // Retrieves user inputs
        String cardNum = cardNumberInput.getText();
        String cardName = cardNameInput.getText();
        String expiration = expirationInput.getText();
        String code = securityCodeInput.getText();

        String country = countryInput.getText();
        String name = nameInput.getText();
        String address = addressInput.getText();
        String state = stateInput.getText();
        String city = cityInput.getText();
        String postcode = postcodeInput.getText();

        // Checks if any of the text fields are empty. If so, an error message returns
        if (cardNum.isEmpty() || cardName.isEmpty() || expiration.isEmpty() || code.isEmpty() 
        || country.isEmpty() || name.isEmpty() || address.isEmpty() || state.isEmpty() || city.isEmpty() || postcode.isEmpty()) {
            errorMessage1.setText("Please fill out each field.");
        } 
        // Regexes are used to check if certain input boxes have been inputted in a specfic format. If any of the following aren't valid, an error message appears
        else if (Pattern.matches("\\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d", cardNum) != true) {
            errorMessage1.setText("Input for Card Number is not valid.");
        } else if (Pattern.matches("[0-9]{2}(\\/)[0-9]{2}", expiration) != true) {
            errorMessage1.setText("Input for Card Expiration Date is not valid.");
        } else if (Pattern.matches("\\d\\d\\d", code) != true) {
            errorMessage1.setText("Input for Security Code is not valid.");
        } else if (Pattern.matches("[1-9]{4}", postcode) != true) {
            errorMessage2.setText("Input for Postcode is not valid.");
        } 
        // If each of the inputs are valid, then the program proceeds to confirm the user's booking
        else {
            confirmBooking();
        }
    }

    // Booking is added, and the stored seating object that was booked is modified to remove the seats that were booked
    @FXML
    public void confirmBooking() throws IOException {
        // Appends the newly created booking to the bookings ArrayList
        int bookingNo = (bookings.get(bookings.size()-1).getBookingNo()) + 1;
        Booking newBooking = new Booking(bookingNo, flightObj.getFlightNo(), bookedSeats, adult, child, price, "23", seatingObj.getSeatClassType(), inFlightWiFi, inFlightFoodAndDrinks);
        bookings.add(newBooking);

        // This entire loop is dedicated to the removal of booked seats in the seatingObj of the booked flight
        // First for-loop looks for the stored object from the ArrayList by using the info this controller currently holds
        for (int i = 0; i < seatings.size(); i++) {
            Seating seatingObj2 = seatings.get(i);

            // If the info matches between the two, then that object is retrieved (and the outer loop breaks after the remainder of code is processed)
            if (seatingObj2.getFlightNo().equals(seatingObj.getFlightNo()) 
            && seatingObj2.getSeatClassType().equals(seatingObj.getSeatClassType())) {
                // 2nd for-loop retrieves each seat that the user has booked
                for (int j = 0; j < bookedSeats.length; j++) {
                    String bookedSeat = bookedSeats[j];

                    // 3rd for-loop compares the retrieved booked seat by looking for what's stored in the seatingObj from the ArrayList
                    for (int k = 0; k < seatingObj2.getAvailableSeats().size(); k++) {
                        String storedSeat = seatingObj2.getAvailableSeats().get(k);

                        // Once it looks for the matching seating, it is removed from the ArrayList, to ensure that this seat is already booked
                        if (bookedSeat.equals(storedSeat)) {
                            seatingObj2.getAvailableSeats().remove(k);
                        }
                    }
                }
                // The number of available seats are updated in the object
                seatingObj2.setAvailableNo(seatingObj2.getAvailableSeats().size());
                break;
            }
        }
        // Changes to receiptView now that the ArrayLists have been updated
        switchToReceiptView();
    }

    // Switches from transactionView to receiptView following the confirmation of payment and booking
    @FXML
    private void switchToReceiptView() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/receiptView.fxml", logoutButton.getScene());
    }

}
