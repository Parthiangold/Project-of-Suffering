package group.frontend;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import group.backend.Booking;
import group.backend.Customer;
import group.backend.Flight;
import group.backend.Seating;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Callback;

public class SceneSelector {
	
	private final Callback<Class<?>, Object> controllerFactory ;
	
	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum);
	}

	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, int bNum) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum, bNum);
	}

	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum, flightResults, seatingResults);
	}
	
	// Loads the fxml with the parameters of the called controller class
	public void selectScene(String fxmlFile, Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		loader.setControllerFactory(controllerFactory);
		scene.setRoot((Parent)loader.load());
	}
	
	// Each parameter is retrieved from the controller calling the scene change and is loaded with the fxml
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 5 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum);
						}
					}
					return type.getDeclaredConstructor().newInstance();
				} 
				// Nothing is returned if the above pattern of expected parameters aren't fulfilled
				catch (Exception exc) {
					exc.printStackTrace();
					return null ;
				}
			}
			
		};
	}

	// For manage booking-related controllers
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, int bNum) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 6
								&& constructor.getParameterTypes()[0]==ArrayList.class
								&& constructor.getParameterTypes()[4]==int.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum, bNum);
						}
					}
					return type.getDeclaredConstructor().newInstance();
				}
				// Nothing is returned if the above pattern of expected parameters aren't fulfilled
				catch (Exception exc) {
					exc.printStackTrace();
					return null ;
				}
			}

		};
	}

	// For search flight-related controllers
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 7 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class
								&& constructor.getParameterTypes()[5]==ArrayList.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum, flightResults, seatingResults);
						}
					}
					return type.getDeclaredConstructor().newInstance();
				} 
				// Nothing is returned if the above pattern of expected parameters aren't fulfilled
				catch (Exception exc) {
					exc.printStackTrace();
					return null ;
				}
			}
			
		};
	}
}