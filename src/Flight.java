import java.util.*;

public class Flight {
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

    // getDepartureDestination method
    public String getDepartureDestination() {
        return departureDestination;
    }

    public String getArrivalDestination() {
        return arrivalDestination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return flightNo;
    }

    public String toString(Formatter formatter){
        return String.valueOf(formatter.format(String.valueOf(this)));
    }
}
