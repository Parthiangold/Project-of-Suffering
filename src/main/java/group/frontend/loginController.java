package group.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {

    @FXML
    private TextField emailInput;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordInput;

    @FXML
    private Label errorMessage;

    @FXML
    private Button exitButton;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;

    public loginController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
    }

    // Switches from loginView to menuView
    @FXML
    private void switchToMenuView() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/menuView.fxml", emailInput.getScene());
    }

    // Authorises customer login details before proceeding with booking management and searches
    @FXML
    public void login() throws IOException {
        boolean authorised = false;
        Customer customerObj = null;
        String email = emailInput.getText();
        String password = passwordHashing(passwordInput.getText());

        // Error message if the user hasn't inputted anything in either text fields
        if (email.isEmpty() || passwordInput.getText().isEmpty()) {
            errorMessage.setText("Please enter your data.");
        }
        
        else { 
            // For-loop first checks to see if there's a matching email
            for (int i = 0; i < customers.size(); i++) {
                customerObj = customers.get(i);

                // If there is a matching email, the inputted password is compared to what is stored under their details
                if (email.equals(customerObj.getEmail()) && password.equals(customerObj.getPassword())) {
                    // If it matches, the user is authorised, loads the booking file of the user and switches to the menu display
                    authorised = true;
                    cNum = customerObj.getCustomerNo();
                    MainApplication.getApplicationInstance().setCNum(cNum);
                    loadBookings(cNum);
                    switchToMenuView();
                    break;
                }

                // Error message if either the email or password doesn't match what is stored
                else if (i == (customers.size() - 1)){
                    errorMessage.setText("Invalid email or password.");
                    customerObj = null;
                    break;
                }
            }
        }
    }

    // Converts user input into SHA-512 to check their customer password input in +login() method
    public String passwordHashing(String passwordInput) {
        try {
            // SHA-512 algorithm is called and used to return the byte array of the user input
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(passwordInput.getBytes());

            // Byte array is stored as a BigInteger object and then converted into a hex value (hashing)
            BigInteger biObj = new BigInteger(1, messageDigest);
            String hashedInput = biObj.toString(16);

            // Ensures the length of the hash is 128 characters before returning the hashed user input
            while (hashedInput.length() < 128) {
                hashedInput = "0" + hashedInput;
            }

            return hashedInput;
        }

        // Catches error if an invalid message digest algorithm is used
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Loads the data from bookings_<CustomerNo>.txt and stores the data in the Booking Class
    public void loadBookings(int customerNo) {
        
        try {
            // Reads the bookings txt file based on the authorised customer's number
            String customerFile = "src/main/resources/group/bookings_" + Integer.toString(customerNo) + ".txt";
            File filename = new File(customerFile);
            Scanner reader = new Scanner(filename);
            reader.useDelimiter(",|\r\n|\n");

            // For every newline in the txt file, a new "Booking" object is made to store the data
            while (reader.hasNext()) {
                Booking bookingObj = new Booking();
                bookingObj.inputData(reader);
                bookings.add(bookingObj);
            }
            reader.close();
        }

        // Catches error if "bookings_<customerNo>.txt" isn't found
        catch (FileNotFoundException e) {
            System.out.println("Error 404: file 'bookings.txt' not found.");
            e.printStackTrace();
        }
    }

    // Saves objects to files on exit
    @FXML
    public void exitProgram() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
