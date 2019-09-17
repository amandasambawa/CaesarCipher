package exceptions;

public class SiteNotFoundException extends Exception {
	
	public SiteNotFoundException() {
		super("Site not found");
	}
}
