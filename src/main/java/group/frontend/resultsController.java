package group.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class resultsController {

    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button idk;

    @FXML
    private Button logoutButton;

    @FXML
    private Text resultCount;

    @FXML
    private VBox resultsBox;

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;
    private ArrayList<Flight> flightResults;
    private ArrayList<Seating> seatingResults;
    private int adult;
    private int child;

    public resultsController(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating>seatingResults, int adult, int child) {
        this.customers = customers;
        this.flights = flights;
        this.seatings = seatings;
        this.bookings = bookings;
        this.cNum = cNum;
        this.flightResults = flightResults;
        this.seatingResults = seatingResults;
        this.adult = adult;
        this.child = child;
    }

    // Shows search results of returned flights once controller is initialised
    @FXML
    public void initialize() throws IOException {
        displayResults();
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

    // Returns to the menu view
    @FXML
    public void back() throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum);
		selector.selectScene("/group/menuView.fxml", logoutButton.getScene());
    }

    // Displays the search results
    @FXML
    public void displayResults() throws IOException {
        // Displays number of flights retrieved from the search input
        resultCount.setText(flightResults.size() + " Result(s) Returned:");

        // List of Buttons that are used to store the dynamically created buttons
        List<Button> buttonlist = new ArrayList<>();

        // For-loop that retrieves the info of the retrieved flight and seating results, creates a button in a VBox that displays the retrieved info,
        // with the button also being set style parameters
        for (int i = 0; i < flightResults.size(); i++) {
            Flight flightObj = flightResults.get(i);
            Seating seatingObj = seatingResults.get(i);

            // Creates new button with info of the flight and seating
            Button dynamicButton = new Button(flightObj.overviewString() + seatingObj.overviewString());
            dynamicButton.setOnAction(e -> {
                try {
                    switchToFlightView(flightObj, seatingObj);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            // Background colour is set based on the seating class
            if (seatingObj.getSeatClassType().equals("Economy")) {
                dynamicButton.setStyle("-fx-font-size: 14; -fx-alignment: TOP_LEFT; -fx-background-radius: 0; -fx-pref-height: 130; -fx-pref-width: 636; -fx-background-color: #1f8bff; -fx-text-fill: #ffffff");
            } else if (seatingObj.getSeatClassType().equals("Business")) {
                dynamicButton.setStyle("-fx-font-size: 14; -fx-alignment: TOP_LEFT; -fx-background-radius: 0; -fx-pref-height: 130; -fx-pref-width: 636; -fx-background-color: #113a80; -fx-text-fill: #ffffff");
            } else if (seatingObj.getSeatClassType().equals("First Class")) {
                dynamicButton.setStyle("-fx-font-size: 14; -fx-alignment: TOP_LEFT; -fx-background-radius: 0; -fx-pref-height: 130; -fx-pref-width: 636; -fx-background-color: #3c4962; -fx-text-fill: #ffffff");
            }

            // Added to the button list after styles are set
            buttonlist.add(dynamicButton);

        // After the loop, the VBox is cleared (to prevent any duplicates from the previous load) and then the buttons from the buttonlist is added to the VBox
        resultsBox.getChildren().clear();
        resultsBox.getChildren().addAll(buttonlist);
        }
    }

    // Switches from resultsView to flightView following the selection of a flight
    @FXML
    private void switchToFlightView(Flight flightObj, Seating seatingObj) throws IOException {
        SceneSelector selector = new SceneSelector(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj);
		selector.selectScene("/group/flightView.fxml", logoutButton.getScene());
    }

}
