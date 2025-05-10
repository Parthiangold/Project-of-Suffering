import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
package org.example;

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

    public static void main(String[] args) {
        CustomerManagementSystem cms = new CustomerManagementSystem();
        cms.loadCustomers();
    }
}
