package exceptions;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("User was not found");
	}
}
