package menu;

import vault.PasswordVault;
import java.util.Scanner;

import exceptions.DuplicateSiteException;
import exceptions.DuplicateUserException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidSiteException;
import exceptions.InvalidUsernameException;
import exceptions.PasswordMismatchException;
import exceptions.SiteNotFoundException;
import exceptions.UserLockedOutException;
import exceptions.UserNotFoundException;

/**
 * Reference: <a href="https://www.java-forums.org/new-java/26584-menu-driven-console-help-please.html">A Simple Text-Based Menu System</a>
 * @author JosAH
 */


public class PasswordlVaultMenu {
	
	private final PasswordVault vault;
	
	public PasswordlVaultMenu() {
		vault = new PasswordVault();
	}
	
	public void run() {
		MenuItem addUser = new MenuItem("Add new user", new Runnable() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				try {
					vault.addNewUser(username, password);
					System.out.println("\nUser added successfuly\n");
				} catch (InvalidUsernameException | InvalidPasswordException | DuplicateUserException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		});
		
		MenuItem addNewSite = new MenuItem("Add new site", new Runnable() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				System.out.println("Enter site name: ");
				String sitename = scanner.nextLine();
				try {
					vault.addNewSite(username, password, sitename);
					System.out.println("\nSite added successfully\n");
				} catch (DuplicateSiteException | UserNotFoundException | UserLockedOutException
						| PasswordMismatchException | InvalidSiteException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		});
		
		MenuItem retrieveSitePassword = new MenuItem("Retrieve site password: ", new Runnable() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				System.out.println("Enter site name: ");
				String sitename = scanner.nextLine();
				try {
					System.out.println("\nThe password for " + sitename + " is " + vault.retrieveSitePassword(username, password, sitename) + "\n");
				} catch (SiteNotFoundException | UserNotFoundException | UserLockedOutException
						| PasswordMismatchException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		});
		
		MenuItem updateSitePassword = new MenuItem("Update site password: ", new Runnable() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter username: ");
				String username = scanner.nextLine();
				System.out.println("Enter password: ");
				String password = scanner.nextLine();
				System.out.println("Enter site name: ");
				String sitename = scanner.nextLine();
				try {
					System.out.println("\nYour new site password for " + sitename + " is " + vault.updateSitePassword(username, password, sitename) + "\n");
				} catch (SiteNotFoundException | UserNotFoundException | UserLockedOutException
						| PasswordMismatchException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
				
		});
		
		Menu menu = new Menu("PASSWORD VAULT MENU", false, true, addUser, addNewSite, retrieveSitePassword, updateSitePassword);
		menu.run();
	}
}
