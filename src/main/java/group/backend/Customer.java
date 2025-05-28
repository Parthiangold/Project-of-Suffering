package group.backend;

import java.util.*;

public class Customer {
    private int customerNo;
    private String name, email, password;

    // Default constructors initialise class and attributes
    public Customer() {}
    public Customer(int c, String n, String e, String p) {
        this.customerNo = c;
        this.name = n;
        this.email = e;
        this.password = p;
    }

    // Reads the text file and stores them into attributes
    public void inputData(Scanner reader) {
        customerNo = reader.nextInt();
        name = reader.next();
        email = reader.next();
        password = reader.next();
    }

    // Writes objects to a text file
    public void outputData(Formatter formatter) {
        formatter.format("%d,%s,%s,%s\n", customerNo, name, email, password);
    }

    // Returns the "email" attribute
    public String getEmail() {
        return this.email;
    }

    // Gets the stored hashed password
    public String getPassword() {
        return this.password;
    }

    // Returns the "customerNo" of the object
    public int getCustomerNo() {
        return this.customerNo;
    }

    // Returns the name of the customer
    public String getName() {
        return this.name;
    }
}
