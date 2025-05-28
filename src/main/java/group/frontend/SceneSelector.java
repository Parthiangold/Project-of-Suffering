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

	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, 
	ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child);
	}

	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, 
	ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child, Flight flightObj, Seating seatingObj) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj);
	}

	public SceneSelector(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum, 
	ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child, Flight flightObj, Seating seatingObj, String[] bookedSeats, boolean wifi, boolean fnd, double price) {
		controllerFactory = createControllerFactory(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj, bookedSeats, wifi, fnd, price);
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
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, 
	ArrayList<Booking> bookings, int cNum, int bNum) {
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

	// For resultsController
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, 
	ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 9 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class
								&& constructor.getParameterTypes()[5]==ArrayList.class
								&& constructor.getParameterTypes()[7]==int.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child);
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

	// For flightController
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, 
	ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child, Flight flightObj, Seating seatingObj) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 11 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class
								&& constructor.getParameterTypes()[5]==ArrayList.class
								&& constructor.getParameterTypes()[7]==int.class
								&& constructor.getParameterTypes()[9]==Flight.class
								&& constructor.getParameterTypes()[10]==Seating.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj);
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

	// For transactionController
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, 
	ArrayList<Booking> bookings, int cNum, ArrayList<Flight> flightResults, ArrayList<Seating> seatingResults, int adult, int child, Flight flightObj, Seating seatingObj, 
	String[] bookedSeats, boolean wifi, boolean fnd, double price) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					// Within the created SceneSelector object, it validates if the order of parameters is 4 ArrayLists followed by an int before returning
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 15 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class
								&& constructor.getParameterTypes()[5]==ArrayList.class
								&& constructor.getParameterTypes()[7]==int.class
								&& constructor.getParameterTypes()[9]==Flight.class
								&& constructor.getParameterTypes()[10]==Seating.class
								&& constructor.getParameterTypes()[11]==String[].class
								&& constructor.getParameterTypes()[12]==boolean.class
								&& constructor.getParameterTypes()[14]==double.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum, flightResults, seatingResults, adult, child, flightObj, seatingObj, bookedSeats, wifi, fnd, price);
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