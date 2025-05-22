import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FlightBookingConfirmation extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30, 40, 30, 40));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Text title = new Text("Flight Booking Confirmation");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Confirmation details
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(15);
        detailsGrid.setVgap(15);
        detailsGrid.setPadding(new Insets(20, 10, 20, 10));
        
        // Passenger Name
        Label passengerLabel = new Label("Passenger Name:");
        passengerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label passengerValue = new Label("John Doe");
        passengerValue.setFont(Font.font("Arial", 14));
        
        // Departure Date
        Label dateLabel = new Label("Departure Date:");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label dateValue = new Label("06/15/2023");
        dateValue.setFont(Font.font("Arial", 14));
        
        // Departure Place
        Label departureLabel = new Label("Departure Place:");
        departureLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label departureValue = new Label("New York (JFK)");
        departureValue.setFont(Font.font("Arial", 14));
        
        // Destination
        Label destinationLabel = new Label("Destination:");
        destinationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label destinationValue = new Label("London (LHR)");
        destinationValue.setFont(Font.font("Arial", 14));
        
        // Flight Class
        Label classLabel = new Label("Flight Class:");
        classLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label classValue = new Label("Business Class");
        classValue.setFont(Font.font("Arial", 14));
        
        // Add fields to grid
        detailsGrid.add(passengerLabel, 0, 0);
        detailsGrid.add(passengerValue, 1, 0);
        detailsGrid.add(dateLabel, 0, 1);
        detailsGrid.add(dateValue, 1, 1);
        detailsGrid.add(departureLabel, 0, 2);
        detailsGrid.add(departureValue, 1, 2);
        detailsGrid.add(destinationLabel, 0, 3);
        detailsGrid.add(destinationValue, 1, 3);
        detailsGrid.add(classLabel, 0, 4);
        detailsGrid.add(classValue, 1, 4);
        
        // Buttons
        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back to Form");
        backButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        Button confirmButton = new Button("Confirm Booking");
        confirmButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        buttonBox.getChildren().addAll(backButton, confirmButton);
        
        // Add all components to main container
        mainContainer.getChildren().addAll(title, detailsGrid, buttonBox);
        
        // Set up the scene
        Scene scene = new Scene(mainContainer, 500, 450);
        primaryStage.setTitle("Flight Booking Confirmation");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Button actions
        backButton.setOnAction(e -> {
            // Here you would typically navigate back to the booking form
            FlightBookingForm bookingForm = new FlightBookingForm();
            bookingForm.start(new Stage());
            primaryStage.close();
        });
        
        confirmButton.setOnAction(e -> {
            // Confirmation logic would go here
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking Confirmed");
            alert.setHeaderText(null);
            alert.setContentText("Your flight has been successfully booked!");
            alert.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}