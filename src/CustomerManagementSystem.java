import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerManagementSystem {
    private ArrayList<Customer> customers;
    private ArrayList<Seating> seatings;
    private ArrayList<Booking> bookings;

    public CustomerManagementSystem() {
        customers = new ArrayList<Customer>();
        seatings = new ArrayList<Seating>();
        bookings = new ArrayList<Booking>();
    }

    // Loads the data from "customers.txt" and stores the data under the "Customer" class
    public void loadCustomers() {
        try {
            // Reads "customers.txt"
            File filename = new File("src/customers.txt"); //Had issues in prior projects where files wouldn't load with just the file name so I have added the /src just to make sure it works
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
            File filename = new File("src/seatings.txt");
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
    // Loads the data from bookings.txt and stores the data in the Booking Class
    public void loadBookings() {

        try {
            // Reads the bookings.txt file
            File filename = new File("src/bookings.txt");
            Scanner reader = new Scanner(filename);
            reader.useDelimiter(",|\r\n|\n");

            // For every newline the txt file has a new "Booking" object is made to store the data
            while (reader.hasNext()) {
                Booking bookingObj = new Booking();
                bookingObj.inputData(reader);
                bookings.add(bookingObj);
            }
            reader.close();
        }

        //Catches FileNotFound Exception
        catch (FileNotFoundException e) {
            System.out.println("Error 404 file not found.");
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
    public void login(Scanner input) {
        // While-loop (only for backend ver only) that requests the user to enter their details until they're authorised
        boolean authorised = false;
        while(authorised == false) {
            // User email input
            System.out.print("\nEnter email: ");
            String emailInput = input.next();

            // User password input, which goes through a hashing algorithm to compare with the stored encrypted password
            System.out.print("Enter password: ");
            String passwordInput = passwordHashing(input.next());

            // For-loop first checks to see if there's a matching email
            for (int i = 0; i < customers.size(); i++) {
                Customer customerObj = customers.get(i);
                String objEmail = customerObj.getEmail();

                // If there is a matching email, the inputted password is compared to what is stored under their details
                if (emailInput.equals(objEmail)) {
                    if (passwordInput.equals(customerObj.getPassword())) {
                        // If it matches, the user is authorised, while-loop breaks which finishes the method
                        System.out.println("Customer authorised.");
                        authorised = true;
                        // Prevents a bug where the system thinks the user inputs something invalid on load
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
    }

    public void menu(Scanner input) {
        boolean done = false;
        while(! done) {
            // Menu message that is presented at the start of each loop
            System.out.println("\nWelcome to the FlyDreamAir Customer Management System.\n");
            System.out.println("Input an option below (0-2):\n");
            System.out.println("\t1. Search and book a flight.");
            System.out.println("\t2. Manage flight bookings.");
            System.out.println("\t0. Exit program.");
            
            // Switch cases are based on the input provided by the user
            String menuInput = input.nextLine();
            switch(menuInput) {
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

    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in);
        CustomerManagementSystem cms = new CustomerManagementSystem();
        
        // Loads customer data
        cms.loadCustomers();

        /*cms.loadFlights(); */

        // Loads seating data
        cms.loadSeatings();
        
        // Customer user login
        cms.login(input);

        /*cms.loadBookings(); */

        cms.menu(input);

        // User input closes when program is terminating
        input.close();
    }
}
