package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;
import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate = new RestTemplate();
    private TenmoService tenmoService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new TenmoService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, TenmoService tenmoService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.tenmoService = tenmoService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
    	Balance balance = tenmoService.getBalance(currentUser.getToken());

		System.out.println("Your current account balance is: " + NumberFormat.getCurrencyInstance().format(balance.getBalance()));
	}

	private void viewTransferHistory() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity(httpHeaders);

		int userId = currentUser.getUser().getId();
		int accountId = restTemplate.getForObject(API_BASE_URL + "/users/" + userId, Integer.class);
		//	String username = restTemplate.getForObject(API_BASE_URL + "/users/" + )
		ResponseEntity<Transfer[]> responseEntity = restTemplate.getForEntity(API_BASE_URL + "/transfers/" + accountId, Transfer[].class);
		Transfer[] transfers = responseEntity.getBody();
		System.out.println("\n-----------------------------------------\n" +
				"   Id         From/To         Amount\n" +
				"-----------------------------------------");
		for (Transfer transfer : transfers) {

			if (transfer.getAccountFrom() != accountId) {
				String username = restTemplate.getForObject(API_BASE_URL + "/users/accounts/" + transfer.getAccountFrom(), String.class);
				System.out.println(transfer.toStringLimitedTo(username));
			} else {
				String username = restTemplate.getForObject(API_BASE_URL + "/users/accounts/" + transfer.getAccountTo(), String.class);
				System.out.println(transfer.toStringLimitedFrom(username));
			}

		}

		int transferId = console.getUserInputInteger("\nPlease enter transfer ID to view details (0 to cancel)");
		if (transferId == 0) {
			return;
		}
		boolean found = false;
		for (Transfer transfer1 : transfers) {
			if (transferId == transfer1.getTransferId()) {
				found = true;
			}
		}
			if(!found){
				System.out.println("\n*** Invalid transfer ID ***");
				return;
			}



		Transfer transfer = restTemplate.getForObject(API_BASE_URL + "/transfer/" + transferId, Transfer.class);
		String usernameFrom = restTemplate.getForObject(API_BASE_URL + "/users/accounts/" + transfer.getAccountFrom(), String.class);
		String usernameTo = restTemplate.getForObject(API_BASE_URL + "/users/accounts/" + transfer.getAccountTo(), String.class);
		System.out.println(transfer.toStringFullTransfer(usernameFrom, usernameTo));

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void sendBucks() {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(currentUser.getToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		System.out.println("\n-------------------------------------\n" +
				"     Users ID              Name\n" +
				"-------------------------------------");
		User[] user = restTemplate.getForObject(API_BASE_URL + "/users", User[].class);
		for (User users : user) {
			System.out.println("      " + users.getId() + "                 " + users.getUsername());
		}
		int accountToUserID = console.getUserInputInteger("\nEnter ID of user you are sending to (0 to cancel)");
		if (accountToUserID == 0) {
			return;
		}
        boolean found = false;
        for (User user1 : user) {
            if (accountToUserID == user1.getId()) {
                found = true;
            }
        }
        if (!found) {
            System.out.println("\n*** Invalid user ID ***");
            return;
        }

		BigDecimal amount = new BigDecimal("0");
		try {
			amount = new BigDecimal(console.getUserInput("\nEnter amount"));
		} catch (NumberFormatException e) {
			System.out.println(System.lineSeparator() + "*** Please enter a number ***" + System.lineSeparator());
		}


		int currentUserId = currentUser.getUser().getId();
		TransferUser transferUser = new TransferUser(currentUserId, accountToUserID, amount);
		HttpEntity<TransferUser> entity = new HttpEntity<>(transferUser, httpHeaders);

		restTemplate.postForObject(API_BASE_URL + "/transfer", entity, TransferUser.class);

		//updating balance after transfer
		try{
			restTemplate.put(API_BASE_URL + "/balance/transfer", transferUser);
		} catch (Exception ex){
			System.out.println(ex);
		}
		//restTemplate.exchange(API_BASE_URL + "/transfer",HttpMethod.PUT, entity, Transfer.class).getBody();

	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
