package group.frontend;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

public class changeSeatController {

    @FXML
    private Button addSeatButton;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label numberMessage;

    @FXML
    private Button removeSeatButton;

    @FXML
    private Label errorMessage;

    private ObservableList<String> aSeatList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> availableSeatList = new ListView<String>(aSeatList);

    private ObservableList<String> sSeatList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> selectedSeatList = new ListView<String>(sSeatList);

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;
    private Booking bookingObj;

    private int adult;
    private int child;
    private Seating seatingObj;

    public changeSeatController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings,
        ArrayList<Booking> bookings, int cNum, Booking bookingObj) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        this.bookingObj = bookingObj;
    }

    @FXML
    public void initialize() {
        // Gets adult, child, and seating object values
        adult = bookingObj.getAdult();
        child = bookingObj.getChild();
        for (int i = 0; i < seatings.size(); i++) {
            seatingObj = seatings.get(i);
            if (seatingObj.getFlightNo().equals(bookingObj.getFlightNo()) 
            && seatingObj.getSeatClassType().equals(bookingObj.getSeatClassType())) {
                break;
            }
        }

        // Populate seat class combo box
        classComboBox.getItems().addAll("Economy", "Business", "First Class");

        // Update available seats when the class is selected
        classComboBox.setOnAction(event ->
                populateSeatOptions(classComboBox.getValue()));

        // Set placeholder for combo box
        classComboBox.setPromptText("Select Seat Class");
        
        // Retrieves available seats
        for (int i = 0; i < seatingObj.getAvailableSeats().size(); i++) {
            String aSeat = seatingObj.getAvailableSeats().get(i);
            aSeatList.add(aSeat);
            availableSeatList.getItems().add(aSeat);
        }

        // Retrieves currently booked seats by the user
        for (int i = 0; i < bookingObj.getBookedSeats().length; i++) {
            String sSeat = bookingObj.getBookedSeats()[i];
            sSeatList.add(sSeat);
            selectedSeatList.getItems().add(sSeat);
        }

        // Sets the number of remaining seats to add based on retrieved information in ObserableList
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

    // Returns back to the bookingView
    @FXML
    private void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, bookingObj);
        selector.selectScene("/group/bookingView.fxml", logoutButton.getScene());
    }

    // Adds the selected seat to the selected seats list
    @FXML
    public void addSeat() {
        if ((adult + child - sSeatList.size()) == 0) {
            errorMessage.setText("No more seats can be selected.");
        } else {
            String selectedSeat = availableSeatList.getSelectionModel().getSelectedItem();
            int selectedID = availableSeatList.getSelectionModel().getSelectedIndex();
            if (selectedSeat == null) {
                errorMessage.setText("Select a seat in the list first.");
            } else {
                selectedSeatList.getItems().add(selectedSeat);
                sSeatList.add(selectedSeat);
                availableSeatList.getItems().remove(selectedID);
                aSeatList.remove(selectedID);
            }
        }
        numberMessage.setText("# of Seats to Add: " + (adult + child - sSeatList.size()));
    }

    // Removes the selected seat from the selected seats list
    @FXML
    public void removeSeat() {
        if ((sSeatList.size() == 0)) {
            errorMessage.setText("No more seats can be removed.");
        } else {
            String selectedSeat = selectedSeatList.getSelectionModel().getSelectedItem();
            int selectedID = selectedSeatList.getSelectionModel().getSelectedIndex();
            if (selectedSeat == null) {
                errorMessage.setText("Select a seat in the list first.");
            } else {
                availableSeatList.getItems().add(selectedSeat);
                aSeatList.add(selectedSeat);
                selectedSeatList.getItems().remove(selectedID);
                sSeatList.remove(selectedID);
            }
        }
        numberMessage.setText("# of Seats to Add: " + (adult + child - sSeatList.size()));
    }

    // 
    private void populateSeatOptions(String selectedClass) {

        // Retrieve new seatingObj based on selected class type
        for (int i = 0; i < seatings.size(); i++) {
            seatingObj = seatings.get(i);
            if (seatingObj.getFlightNo().equals(bookingObj.getFlightNo()) 
            && seatingObj.getSeatClassType().equals(selectedClass)) {
                // Clears lists
                availableSeatList.getItems().clear();
                aSeatList.clear();
                selectedSeatList.getItems().clear();
                sSeatList.clear();

                // Retrieves available seats
                for (int j = 0; j < seatingObj.getAvailableSeats().size(); j++) {
                    String aSeat = seatingObj.getAvailableSeats().get(j);
                    aSeatList.add(aSeat);
                    availableSeatList.getItems().add(aSeat);
                }
                
                // Retrieves currently booked seats by the user if the selected class is the same as the currently booked class
                if (selectedClass.equals(bookingObj.getSeatClassType())) {
                    for (int k = 0; k < bookingObj.getBookedSeats().length; k++) {
                        String sSeat = bookingObj.getBookedSeats()[k];
                        sSeatList.add(sSeat);
                        selectedSeatList.getItems().add(sSeat);
                    }
                }
                errorMessage.setText("");
                break;
            } else {
                errorMessage.setText("No available seats under this class.");
            }
        }
    }

    // Confirms changing the seat on the selected booking.
    @FXML
    private void confirmChanges() throws IOException {
        if (sSeatList.size() != adult + child) {
            errorMessage.setText("You must select enough seats.");
        } else {
            // Price is set based on the selected seating class type
            bookingObj.setPrice((adult * seatingObj.getAdultPricing()) + (child * seatingObj.getChildPricing()));
            if (bookingObj.isInFlightWiFi()) {
                bookingObj.setPrice(bookingObj.getPrice() + 5);
            }
            if (bookingObj.isInFlightFoodAndDrinks()) {
                bookingObj.setPrice(bookingObj.getPrice() + 10);
            }

            // Anything in the current array for the bookingObj is returned
            for (int i = 0; i < seatings.size(); i++) {
                Seating seatingObj2 = seatings.get(i);
                if (seatingObj2.getFlightNo().equals(bookingObj.getFlightNo()) 
            && seatingObj2.getSeatClassType().equals(bookingObj.getSeatClassType())) {
                
                    // 2nd loop retrieves what the user has booked recently and adds it back
                    for (int j = 0; j < bookingObj.getBookedSeats().length; j++) {
                        String bookedSeat = bookingObj.getBookedSeats()[j];
                        seatingObj2.getAvailableSeats().add(bookedSeat);
                    }
                // The number of available seats are updated in the object
                seatingObj2.setAvailableNo(seatingObj2.getAvailableSeats().size());
                break;
                }
            }

            // Anything in the new array is removed from the seatingObj ArrayList
            for (int j = 0; j < sSeatList.size(); j++) {
                String bookedSeat = sSeatList.get(j);
                for (int k = 0; k < seatingObj.getAvailableSeats().size(); k++) {
                    String storedSeat = seatingObj.getAvailableSeats().get(k);

                    // Once it looks for the matching seating, it is removed from the ArrayList, to ensure that this seat is already booked
                    if (bookedSeat.equals(storedSeat)) {
                        seatingObj.getAvailableSeats().remove(k);
                    }
                }
            }

            // The number of available seats are updated in the object
            seatingObj.setAvailableNo(seatingObj.getAvailableSeats().size());

            // BookingObj array is updated
            String[] bookedSeats = new String[adult + child];
            for (int i = 0; i < sSeatList.size(); i++) {
                bookedSeats[i] = sSeatList.get(i);
            }
            bookingObj.setBookedSeats(bookedSeats);
            bookingObj.setSeatClassType(seatingObj.getSeatClassType());

            // Returns back to the bookingView, with the updated info
            back();
        }
    }

}