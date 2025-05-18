import java.util.*;

public class Flight implements MyFileIO {
    private String flightNo;
    private String departureDestination;
    private String arrivalDestination;
    private String departureDate;
    private String arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private String stopover;
    private String totalDuration;
    private String aircraftType;

    // base constructor class
    public Flight() {}

    //Parameterised Constructor class
    public Flight(String flightNo, String departureDestination, String arrivalDestination, String departureDate, String arrivalDate, String departureTime, String arrivalTime, String stopover, String totalDuration, String aircraftType) {
        this.flightNo = flightNo;
        this.departureDestination = departureDestination;
        this.arrivalDestination = arrivalDestination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.stopover = stopover;
        this.totalDuration = totalDuration;
        this.aircraftType = aircraftType;
    }

    //1 change that I made
    // getFlightNo method
    public void inputData(Scanner reader){
        flightNo = reader.next();
        departureDestination = reader.next();
        arrivalDestination = reader.next();
        departureDate = reader.next();
        arrivalDate= reader.next();
        departureTime = reader.next();
        arrivalTime = reader.next();
        stopover = reader.next();
        totalDuration = reader.next();
        aircraftType = reader.next();

    }

    // Writes objects to a text file
    public void outputData(Formatter formatter) {
        formatter.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", 
        flightNo, 
        departureDestination, 
        arrivalDestination, 
        departureDate, 
        arrivalDate, 
        departureTime, 
        arrivalTime, 
        stopover, 
        totalDuration, 
        aircraftType);
    }

    // getDepartureDestination method
    public String getDepartureDestination() {
        return this.departureDestination;
    }

    public String getArrivalDestination() {
        return this.arrivalDestination;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }
    
    public String getArrivalDate() {
        return this.arrivalDate;
    }

    public String getFlightNo() {
        return this.flightNo;
    }

    @Override
    public String toString(){
        return String.format(
            "Flight no: %s\nDeparture Destination: %s\nArrival destination: %s\nDeparture date: %s\nArrival Date: %s\nDeparture time: %s\nArrival: %s\nStopover: %s\nTotal duration: %s\nAircraft Type: %s\n",
            flightNo,
            departureDestination,
            arrivalDestination,
            departureDate,
            arrivalDate,
            departureTime,
            arrivalTime,
            stopover,
            totalDuration,
            aircraftType);
            }
    
}
