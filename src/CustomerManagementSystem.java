import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerManagementSystem {
    private ArrayList<Customer> customers;
    private ArrayList<Flight> flights;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;

    // Initialises ArrayLists
    public CustomerManagementSystem() {
        customers = new ArrayList<Customer>();
        flights = new ArrayList<Flight>();
        seatings = new ArrayList<Seating>();
        bookings = new ArrayList<Booking>();
    }

    // Loads the data from "customers.txt" and stores the data under the "Customer" class
    public void loadCustomers() {
        try {
            // Reads "customers.txt"
            File filename = new File("customers.txt");
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
        
        // Catches error if "customers.txt" isn't found
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Loads the data from "seatings.txt" and stores the data under the "Seating" class
    public void loadSeatings() {
        try {
            // Reads "seatings.txt"
            File filename = new File("seatings.txt");
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

        // Catches error if "seatings.txt" isn't found
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Loads the data from bookings_<CustomerNo>.txt and stores the data in the Booking Class
    public void loadBookings(int customerNo) {

        try {
            // Reads the bookings txt file based on the authorised customer's number
            String customerFile = "bookings_" + Integer.toString(customerNo) + ".txt";
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

    // Loads the data from flights.txt and stores the data in the Flight Class
    public void loadFlights(){
        try{
            File filename = new File("flights.txt");
            Scanner reader = new Scanner(filename);
            reader.useDelimiter(",|\r\n|\n");
            while(reader.hasNext()){
                Flight flightObj= new Flight();
                flightObj.inputData(reader);
                flights.add(flightObj);
            }
            reader.close();

        }

        // Catches error if "flights.txt" isn't found
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
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

    // Authorises customer login details before proceeding with booking management and searches
    public Customer login(Scanner input) {
        // While-loop (only for backend ver only) that requests the user to enter their details until they're authorised
        boolean authorised = false;
        Customer customerObj = null;
        while (authorised == false) {
            // User email input
            System.out.print("\nEnter email: ");
            String emailInput = input.next();

            // User password input, which goes through a hashing algorithm to compare with the stored encrypted password
            System.out.print("Enter password: ");
            String passwordInput = passwordHashing(input.next());

            // For-loop first checks to see if there's a matching email
            for (int i = 0; i < customers.size(); i++) {
                customerObj = customers.get(i);
                String objEmail = customerObj.getEmail();

                // If there is a matching email, the inputted password is compared to what is stored under their details
                if (emailInput.equals(objEmail)) {
                    if (passwordInput.equals(customerObj.getPassword())) {
                        // If it matches, the user is authorised, while-loop breaks which finishes the method
                        System.out.println("Customer authorised.");
                        authorised = true;

                        // Fixes a bug where the +menu() method takes an empty invalid input on load following this method's end
                        input.nextLine();
                        break;
                    } 
                    
                    // Error message if the inputted password doesn't match what is stored
                    else {
                        System.out.println("Invalid Password.");
                        break;
                    }
                } 
                
                // Error message if the inputted email doesn't match any of the stored emails
                else if (i == (customers.size() - 1)) {
                    System.out.println("Invalid email address.");
                }
            }
        }
        return customerObj;
    }

    // Menu loop implementation
    public void menu(Scanner input, Customer customerObj) {
        boolean done = false;
        while (!done) {
            // Menu message that is presented at the start of each loop
            System.out.println("\nWelcome to the FlyDreamAir Customer Management System.\n");
            System.out.println("Input an option below (0-2):\n");
            System.out.println("\t1. Search and book a flight.");
            System.out.println("\t2. Manage flight bookings.");
            System.out.println("\t0. Exit program.\n");
            
            // Switch cases are based on the input provided by the user
            String menuInput = input.nextLine();
            switch (menuInput) {
                // Option 1 - Search and book a flight
                case "1":
                    System.out.println("i wish i could search and book a flight out of this group project on jah :fire: :fire:");
                    break;

                // Option 2 - Manage flight bookings
                case "2":
                    System.out.println("managing these flights will be easier than managing this project ong :fire: :fire:");
                    break;

                // Option 0 - Exit program
                case "0":
                    try {
                        // Formats all flights into "flights.txt"
                        Formatter formatter = new Formatter("flights.txt");
                        for (Flight f:flights) {
                            f.outputData(formatter);
                        }
                        formatter.close();
        
                        // Formats all seatings into "seatings.txt"
                        formatter = new Formatter("seatings.txt");
                        for (Seating s:seatings) {
                            s.outputData(formatter);
                        }
                        formatter.close();
        
                        // Formats all bookings into "bookings_<CustomerNo>.txt"
                        String customerBookingFilename = "bookings_" + customerObj.getCustomerNo();
                        formatter = new Formatter(customerBookingFilename + ".txt");
                        for (Booking b:bookings) {
                            b.outputData(formatter);
                        }
                        formatter.close();
                        System.out.println("\nData has successfully been saved.");
                    }
                    // Catches error if any of the files don't exist
                    catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    System.out.println("The program will proceed to exit");
                    done = true;
                    break;

                // Error message if invalid menu input
                default:
                    System.out.println("\"" + menuInput + "\" is not a valid input. Try again.");
                    break;
            }
        }
    }

    // Prints all bookings in the "bookings" ArrayList
    // This is used for debugging purposes and will be removed in the final build
    public void printAllBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    // Debugging method for printing all Flight objects
    public void printAllFlights(){
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    // Debugging method for printing all Seating objects
    public void printAllSeatings() {
        for (Seating s : seatings) {
            System.out.println(s);
        }
    }

    // Debugging method used to test if file exports can save data made within the program
    public void testData() {
        String[] bookingData = {"12B","12C"};
        Booking bookingObj = new Booking(4,"FDA124",bookingData,2,0,1400.00,"2025-05-12","Economy",false,false);
        bookings.add(bookingObj);
        Flight flightObj = new Flight("FDA800","Sydney","Tokyo","2025-05-12","2025-05-12","07:00 GMT+10","16:00 GMT+9","None","10h","Boeing 787");
        flights.add(flightObj);
        String[] seatingData = {"24D","34D","48D"};
        Seating seatingObj = new Seating("FDA800","Economy",320.00,160.00,seatingData,3);
        seatings.add(seatingObj);
    }

    public static void main(String[] args) { 
        // Initialises user input scanner and CMS object
        Scanner input = new Scanner(System.in);
        CustomerManagementSystem cms = new CustomerManagementSystem();
        System.out.println();
        
        // Loads flight, customer and seating data
        cms.loadFlights();
        cms.loadCustomers();
        cms.loadSeatings();

        // Customer user login, which also returns the object of the authorised customer to be used in the menu method
        Customer customerObj = cms.login(input);

        // Loads booking data based on the authorised customer's customerNo
        cms.loadBookings(customerObj.getCustomerNo()); 

        // Initialises the menu, which takes in the customerObj to use for exiting the program (case "0")
        cms.menu(input, customerObj);

        // User input closes when program is terminating
        input.close();
    }
}
