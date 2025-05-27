package group.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private DatePicker dateInput;

    @FXML
    private TextField departureInput;

    @FXML
    private Button logoutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label errorMessageSearch;

    @FXML
    private Label errorMessageManage;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;

    private ArrayList<Flight> flightResults;
    private ArrayList<Seating> seatingResults;

    public menuController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        flightResults = new ArrayList<Flight>();
        seatingResults = new ArrayList<Seating>();
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
		selector.selectScene("/group/loginView.fxml", adultInput.getScene());
    }

    // Saves objects to files on exit
    @FXML
    public void exitProgram() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    // Checks if inputs are valid in the search inputs or not, before proceeding with searching for flights
    @FXML
    public void inputCheck() throws IOException {
        String departure = departureInput.getText();
        String arrival = arrivalInput.getText();
        int adult = 0;
        int child = 0;

        // Error message if the user hasn't inputted anything in at least one of the text fields
        if (departure.isEmpty() || arrival.isEmpty() || (dateInput.getValue() == null) || adultInput.getText().isEmpty() || childInput.getText().isEmpty()) {
            errorMessageSearch.setText("Please fill out each field.");
        }
        
        // System tries to parse the adult and child input as an int. If it fails, an error message appears
        else if ((adultInput.getText().isEmpty() || childInput.getText().isEmpty()) != true) {
            try {
                String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                try {
                    adult = Integer.parseInt(adultInput.getText());
                    child = Integer.parseInt(childInput.getText());

                    // Also checks if adult is less than 1. If so, an error message appears
                    if (adult < 1) {
                        errorMessageSearch.setText("Number of Adults must be '1' or more.");
                    }

                    // If it's above 1, then the program proceeds with searching for available flights
                    else {
                        searchFlights(departure, arrival, date, adult, child);
                    }
                } catch (NumberFormatException e) {
                    errorMessageSearch.setText("Adults and Children must be a #.");
                }
            } catch (NullPointerException e) {
                errorMessageSearch.setText("Invalid date input.");
            }
        }
    }

    // Method searches for available seats for flights based on user inputs
    @FXML
    public void searchFlights(String departure, String arrival, String date, int adult, int child) throws IOException {
        // For-loop checks for any flights that match the departure location, arrival location and date of departure
        int resultCounter = 0;
        for (int i = 0; i < flights.size(); i++) {
            Flight flightObj = flights.get(i);

            if (departure.equals(flightObj.getDepartureDestination()) 
                    && arrival.equals(flightObj.getArrivalDestination()) 
                    && date.equals(flightObj.getDepartureDate())) {
                
                // If it finds a flight with the above criteria, it then checks for available seatings under this flight
                for (int j = 0; j < seatings.size(); j++) {
                    Seating seatingObj = seatings.get(j);

                    // If it successfully finds a flight, it adds 1 to the result counter and returns the result
                    if ((flightObj.getFlightNo().equals(seatingObj.getFlightNo())) 
                            && ((adult + child) <= seatingObj.getAvailableNo())) {
                        flightResults.add(flightObj);
                        seatingResults.add(seatingObj);
                        resultCounter += 1;
                        errorMessageSearch.setText("");

                    }
                }
            }

            // Error message if either the email or password doesn't match what is stored
            else if ((i == (flights.size() - 1)) && (resultCounter == 0)){
                errorMessageSearch.setText("No flights for this route and date are available.");
                break;
            }
        }

        // Prevents NullPointerException
        if (resultCounter > 0) {
            switchToResultsView();
        }
    }

    // Checks bookingID input which changes view
    @FXML
    public void searchBooking() throws IOException {
        // If the bookingID input is empty, an error message is returned
        if (bookingInput.getText().isEmpty()) {
            errorMessageManage.setText("Please input a booking ID");
        }

        // Otherwise, it attempts to check if the input is an integer, otherwise an error message returns
        else {
            try {
                int bookingID = Integer.parseInt(bookingInput.getText());
                for (int i = 0; i < bookings.size(); i++) {
                    Booking bookingObj = bookings.get(i);
                    
                    // If there is a booking with the matching ID, the display changes to the bookingView and returns the result
                    if (bookingID == (bookingObj.getBookingNo())) {
                        System.out.println(bookingObj.toString());
                        errorMessageManage.setText("");
                        switchToBookingView();
                    } 
                    else if (i == (bookings.size() - 1)) {
                        errorMessageManage.setText("No existing booking under this ID");
                    }
                } 
            } catch (NumberFormatException e) {
                errorMessageManage.setText("Input must be a number");
            }
        }
    }

    // Switches from menuView to resultsView following on search results retrieval
    @FXML
    private void switchToResultsView() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, flightResults, seatingResults);
		selector.selectScene("/group/resultsView.fxml", adultInput.getScene());
    }

    // Switches from menuView to bookingView following on booking ID input
    @FXML
    private void switchToBookingView() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/bookingView.fxml", adultInput.getScene());
    }
}
