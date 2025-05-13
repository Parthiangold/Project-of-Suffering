import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerManagementSystem {
    private ArrayList<Customer> customers;

    public CustomerManagementSystem() {
        customers = new ArrayList<Customer>();
    }

    // Loads the data from "customers.txt" and stores the data under the "Customer" class
    public void loadCustomers() {
        try {
            // Reads "customers.txt"
            File filename = new File("customers.txt");
            Scanner reader = new Scanner(filename);
            reader.useDelimiter(",|\r\n|\n");
            
            // For each line in the file, a new "Purchase" object is created and has data inputted from the file and added to "purchases"
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

    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in);
        CustomerManagementSystem cms = new CustomerManagementSystem();
        
        // Loads customer data
        cms.loadCustomers();

        /*cms.loadFlights(); */
        
        // Customer user login
        cms.login(input);

        /*cms.loadBookings(); */

        /*cms.menu(); */

        // User input closes when program is terminating
        input.close();
    }
}
