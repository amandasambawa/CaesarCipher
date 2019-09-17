package main;

import exceptions.DuplicateSiteException;
import exceptions.DuplicateUserException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidSiteException;
import exceptions.InvalidUsernameException;
import exceptions.PasswordMismatchException;
import exceptions.SiteNotFoundException;
import exceptions.UserLockedOutException;
import exceptions.UserNotFoundException;
import vault.PasswordVault;
import menu.PasswordlVaultMenu;

public class Driver {

	public static void main(String[] args) {
		System.out.println("Testing Password Vault functionality\n");
		PasswordVault pv = new PasswordVault();

		try {
			System.out.println("Add user: amanda");
			pv.addNewUser("amanda", "Password1!");
			System.out.println("Add site amazon with password: " + pv.addNewSite("amanda", "Password1!", "amazon"));
			System.out.println("Add site netflix with password: " + pv.addNewSite("amanda", "Password1!", "netflix"));
			System.out.println("Update password for amazon: " + pv.updateSitePassword("amanda", "Password1!", "amazon"));
			System.out.println("Check password for netflix: " + pv.retrieveSitePassword("amanda", "Password1!", "netflix"));
			
		} catch (InvalidUsernameException | InvalidPasswordException | DuplicateUserException | DuplicateSiteException | UserNotFoundException | UserLockedOutException | PasswordMismatchException | InvalidSiteException | SiteNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nTesting menu-driven console app\n");
		PasswordlVaultMenu passwordVaultMenu = new PasswordlVaultMenu();
		passwordVaultMenu.run();
		System.out.println("You quit Password Vault.");
	}

}
