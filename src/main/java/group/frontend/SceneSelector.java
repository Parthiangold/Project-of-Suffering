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
	
	public void selectScene(String fxmlFile, Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		loader.setControllerFactory(controllerFactory);
		scene.setRoot((Parent)loader.load());
	}
	
	public static final Callback<Class<?>, Object> createControllerFactory(ArrayList<Customer> customers, ArrayList<Flight> flights, ArrayList<Seating> seatings, ArrayList<Booking> bookings, int cNum) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 5 
								&& constructor.getParameterTypes()[0]==ArrayList.class 
								&& constructor.getParameterTypes()[4]==int.class) {
							return constructor.newInstance(customers, flights, seatings, bookings, cNum);
						}
					}
					return type.getDeclaredConstructor().newInstance();
				} catch (Exception exc) {
					exc.printStackTrace();
					return null ;
				}
			}
			
		};
	}
}