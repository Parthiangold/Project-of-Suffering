package group.frontend;

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

public class FlightBookingForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20, 30, 20, 30));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        // Title
        Text title = new Text("Flight Booking Form");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Form fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10, 10, 10, 10));
        
        // Passenger Name
        Label passengerLabel = new Label("Passenger Name:");
        passengerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField passengerField = new TextField();
        passengerField.setPrefWidth(250);
        
        // Departure Date
        Label dateLabel = new Label("Departure Date:");
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField dateField = new TextField();
        dateField.setPromptText("mm/dd/yyyy");
        
        // Departure Place
        Label departureLabel = new Label("Departure Place:");
        departureLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField departureField = new TextField();
        departureField.setPromptText("Enter Departure City");
        
        // Destination
        Label destinationLabel = new Label("Destination:");
        destinationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        TextField destinationField = new TextField();
        destinationField.setPromptText("Enter Destination City");
        
        // Flight Class
        Label classLabel = new Label("Flight Class:");
        classLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ToggleGroup classGroup = new ToggleGroup();
        RadioButton economyRadio = new RadioButton("Economy");
        economyRadio.setToggleGroup(classGroup);
        economyRadio.setSelected(true);
        RadioButton businessRadio = new RadioButton("Business");
        businessRadio.setToggleGroup(classGroup);
        RadioButton firstClassRadio = new RadioButton("First Class");
        firstClassRadio.setToggleGroup(classGroup);
        HBox classBox = new HBox(10, economyRadio, businessRadio, firstClassRadio);
        
        // Add fields to grid
        formGrid.add(passengerLabel, 0, 0);
        formGrid.add(passengerField, 1, 0);
        formGrid.add(dateLabel, 0, 1);
        formGrid.add(dateField, 1, 1);
        formGrid.add(departureLabel, 0, 2);
        formGrid.add(departureField, 1, 2);
        formGrid.add(destinationLabel, 0, 3);
        formGrid.add(destinationField, 1, 3);
        formGrid.add(classLabel, 0, 4);
        formGrid.add(classBox, 1, 4);
        
        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button bookButton = new Button("Book Flight");
        bookButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        buttonBox.getChildren().addAll(bookButton, clearButton);
        
        // Footer
        Separator separator = new Separator();
        Label footer = new Label("HTTP://WWW.BURING.COM");
        footer.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        // Add all components to main container
        mainContainer.getChildren().addAll(title, formGrid, buttonBox, separator, footer);
        
        // Set up the scene
        Scene scene = new Scene(mainContainer, 500, 450);
        primaryStage.setTitle("Flight Booking System");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Button actions
        clearButton.setOnAction(e -> {
            passengerField.clear();
            dateField.clear();
            departureField.clear();
            destinationField.clear();
            economyRadio.setSelected(true);
        });
        
        bookButton.setOnAction(e -> {
            // Create and show the confirmation form
            FlightBookingConfirmation confirmation = new FlightBookingConfirmation();
            confirmation.start(new Stage());
            
            // Close the current form (optional)
            ((Stage) bookButton.getScene().getWindow()).close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}