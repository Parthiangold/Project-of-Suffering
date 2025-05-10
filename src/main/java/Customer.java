import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Customer {
    private int customerNo;
    private String name, email, address, password;

    // Default constructors initialise class and attributes
    public Customer() {}
    public Customer(int c, String n, String e, String a, String p) {
        this.customerNo = c;
        this.name = n;
        this.email = e;
        this.address = a;
        this.password = p;
    }

    // Reads the text file and stores them into attributes
    public void inputData(Scanner reader) {
        customerNo = reader.nextInt();
        name = reader.next();
        email = reader.next();
        address = reader.next();
        password = reader.next();
    }

    // Returns the "email" attribute
    public String getEmail() {
        return this.email;
    }

    // Returns the "customerNo" of the object
    public int getCustomerNo() {
        return this.customerNo;
    }
}
