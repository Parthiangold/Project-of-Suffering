package group.frontend;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;

public class changeSeatController {

    @FXML
    private ComboBox<String> seatComboBox;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum; // Current customer number
    private int bNum; // Current booking number

    public changeSeatController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings,
                                ArrayList<Booking> bookings, int cNum, int bNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        this.bNum = bNum;
    }

    @FXML
    public void initialize() {
        // Populate seat class combo box
        classComboBox.getItems().addAll("Economy", "Business", "First");

        // Update available seats when the class is selected
        classComboBox.setOnAction(event ->
                populateSeatOptions(classComboBox.getValue()));

        // Set placeholder for combo boxes
        classComboBox.setPromptText("Select Seat Class");
        seatComboBox.setPromptText("Available Seats");
    }

    /**
     * Populates the seat combo box based on the selected seat class.
     * */
    private void populateSeatOptions(String selectedClass) {
        seatComboBox.getItems().clear();

        // Filter available seats and update the combo box
        seatings.stream()
                .filter(seating -> seating.getSeatClassType().equalsIgnoreCase(selectedClass))
                .flatMap(seating -> seating.getAvailableSeats().stream())
                .forEach(seat -> seatComboBox.getItems().add(seat));
    }

    /**
     * Confirms changing the seat on the selected booking.
     * */
    @FXML
    private void confirmChanges() {
        String selectedClass = classComboBox.getValue();
        String selectedSeat = seatComboBox.getValue();

        if (selectedClass == null || selectedSeat == null) {
            System.out.println("Error: Please select both a class and a seat.");
            return;
        }

        // Find the current booking
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingNo() == bNum)
                .findFirst()
                .orElse(null);

        if (booking == null) {
            System.err.println("Error: Booking not found.");
            return;
        }

        // Update booking details
        String oldSeat = String.join(";", booking.bookedSeats); // Get the old seat(s)
        booking.setSeatClassType(selectedClass); // Update the seat class
        booking.setBookedSeats(new String[]{selectedSeat}); // Update the seat number

        // Update seat availability in the seating list
        seatings.forEach(seating -> {
            if (seating.getFlightNo().equalsIgnoreCase(booking.flightNo)) {
                seating.markSeatAsAvailable(oldSeat); // Release the old seat
                seating.markSeatAsBooked(selectedSeat); // Book the new seat
            }
        });

        System.out.println("Booking updated successfully.");
        System.out.println("New Seat: " + selectedSeat + " | New Class: " + selectedClass);

        try {
            back(); // Navigate back to the Booking Management menu
        } catch (IOException e) {
            System.err.println("Error navigating back to the Booking Management menu: " + e.getMessage());
        }
    }

    /**
     * Navigates back to the Booking Management menu.
     * */
    @FXML
    private void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, bNum);
        selector.selectScene("/group/bookingView.fxml", cancelButton.getScene());
    }
}