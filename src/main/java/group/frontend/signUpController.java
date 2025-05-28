package group.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.*;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class signUpController {

    @FXML
    private Button backButton;

    @FXML
    private Label bookingDisplay;

    @FXML
    private TextField emailInput;

    @FXML
    private Button exitButton;

    @FXML
    private TextField fNameInput;

    @FXML
    private TextField lNameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button signUpButton;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;

    public signUpController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
    }

    // Saves objects to files on exit
    @FXML
    void exitProgram() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Returns to the menu view
    @FXML
    public void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/loginView.fxml", exitButton.getScene());
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        // Hashing begins
        String hashtext = createPassword(passwordInput.getText());

        // New customer object is created
        int c = (customers.get(customers.size()-1).getCustomerNo()) + 1;
        Customer customer = new Customer(c, fNameInput.getText() + " " + lNameInput.getText(), emailInput.getText(), hashtext);
        customers.add(customer);

        // User sent back to login page
        back();
    }

    public String createPassword(String password) {
        String hashtext = "";
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            hashtext = no.toString(16);

            // Ensure the hash is padded to 128 characters
            while (hashtext.length() < 128) {
                hashtext = "0" + hashtext;
            }
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return hashtext;
    }
}
