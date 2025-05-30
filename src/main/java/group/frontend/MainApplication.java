package group.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class MainApplication extends Application {

    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;
    private int cNum;
    
    private static MainApplication applicationInstance;

    // Application instance is returned to the controller that calls it, enabling it to use methods
    public static MainApplication getApplicationInstance() {
        return applicationInstance;
    }

    // Application instance is initialised following launch() method
    @Override
    public void init() {
        applicationInstance = this;
    }

    // Creates a stage window and loads the login display
    @Override
    public void start(Stage stage) throws Exception {
        // Initialises ArrayLists
        customers = new ArrayList<Customer>();
        flights = new ArrayList<Flight>();
        seatings = new ArrayList<Seating>();
        bookings = new ArrayList<Booking>();
        int cNum = 0;

        // Loads data from text files into containers
        loadFlights();
        loadCustomers();
        loadSeatings();

        // Loads the login display (loginView.fxml), which also loads in the data from the ArrayLists
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/group/loginView.fxml"));
		loader.setControllerFactory(SceneSelector.createControllerFactory(customers, flights, seatings, bookings, cNum));
        final Parent root = (Parent) loader.load();

        // Creates scene
		final Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("FlyDreamAir Customer Management System");
        stage.show();
    }

    // Saves objects to files on exit
    @Override
    public void stop() {
        try {
            // Formats all customers into "customers.txt"
            Formatter formatter = new Formatter("src/main/resources/group/customers.txt");
            for (Customer c : customers) {
                c.outputData(formatter);
            }
            formatter.close();

            // Formats all flights into "flights.txt"
            formatter = new Formatter("src/main/resources/group/flights.txt");
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
        System.out.println("The program will proceed to exit");
    }

    // Used by controllers to track which customer is logged in
    public void setCNum(int i) {
        this.cNum = i;
    }

    // Loads the data from "customers.txt" and stores the data under the "Customer" class
    public void loadCustomers() throws FileNotFoundException {
        // Reads "customers.txt"
        InputStream filename = getClass().getResourceAsStream("/group/customers.txt");
        Scanner reader = new Scanner(filename);
        reader.useDelimiter(",|\r\n|\n");

        // For each line in the file, a new "Customer" object is created and has data inputted from the file and added to "customers"
        while (reader.hasNext()) {
            Customer customerObj = new Customer();
            customerObj.inputData(reader);
            customers.add(customerObj);
        }
        reader.close();
    }

    // Loads the data from "seatings.txt" and stores the data under the "Seating" class
    public void loadSeatings() throws FileNotFoundException {
        // Reads "seatings.txt"
        InputStream filename = getClass().getResourceAsStream("/group/seatings.txt");
        Scanner reader = new Scanner(filename);
        reader.useDelimiter(",|\r\n|\n");

        // For each line in the file, a new "Seating" object is created and has data inputted from the file and added to "seatings"
        while (reader.hasNext()) {
            Seating seatingObj = new Seating();
            seatingObj.inputData(reader);
            seatings.add(seatingObj);
        }
        reader.close();
    }

    // Loads the data from flights.txt and stores the data in the Flight Class
    public void loadFlights() throws FileNotFoundException {
        // Reads "flights.txt"
        InputStream filename = getClass().getResourceAsStream("/group/flights.txt");
        Scanner reader = new Scanner(filename);
        reader.useDelimiter(",|\r\n|\n");
        
        // For each line in the file, a new "Flight" object is created and has data inputted from the file and added to "flights"
        while (reader.hasNext()) {
            Flight flightObj = new Flight();
            flightObj.inputData(reader);
            flights.add(flightObj);
        }
        reader.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
