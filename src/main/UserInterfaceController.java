package main;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.util.Pair;

//import all other classes
import main.Persistence;
import main.PersistenceFactory;
import main.Profile;
import main.PersistenceFactory.persistenceType;
import main.ProfileBackupHandler;
import main.ProfileHandler;
import main.EventHandler;

public class UserInterfaceController {
    static final Scanner inputReader = new Scanner(System.in);

    public void userInteraction(){
        //loop through asking for user input
        // display user options repeatedly
        // display login options when loggin in for the first time

        //repeats indefinitely until closed by user
        while(true){
            displayLoginOptions();
            displayUserOptions();
        }
       
        
    }

    private void displayLoginOptions(){
        //login, create profile, close program (end loop)
        //first thing user sees
        //array of possible options for user
        String[] loginOptions = new String[]{"1. Log in", "2. Create Profile","3. Close", "4. Restore Backup"};
       

        //create scanner for user input
        int userChoice = 0;

        //display possible options indefinitely until user quits
        while(true){
            
            System.out.println("Please enter the number corresponding to the action you wish to do.");
            for(String choice: loginOptions ){
                System.out.println(choice);
            }

            //input validation. Checks if input is a number within the valid range.
            if(inputReader.hasNextInt()){
                userChoice = inputReader.nextInt();
            }
            else{
                System.out.println("Please enter a number corresponding to an action. Do not enter a letter or symbol. Please try again.");
            }

            if(userChoice <0 || userChoice >4){
                System.out.println("The number you entered does not correspond to an option. Please try again.");
                userChoice = 0;
            }

            //if user logs in
            if(userChoice ==1){
                
                //will continously ask for credentials until successful login
                //upon successful login, breaks loop, closes scanner, and returns to userInteraction
                loginToProfile();

                
                break;
            }

            //if user creates a profile
            if(userChoice == 2){

                boolean successfulCreation = false;
                //will continously ask for credentials until successful creation
                //upon successful creation, breaks loop, closes scanner, and returns to userInteraction
                while(!successfulCreation){
                    successfulCreation = createProfile();
                }
                
            }

            //if user closes program
            if(userChoice == 3){
                inputReader.close();
                closeProgram();
            }

            //if user restores from backup
            if (userChoice == 4){

            }
            
            //inputReader.close();
        }

    }

    private void displayUserOptions(){
        //create scanner for user input
        //inputReader
        int userChoice = 0;        

        //array of possible options for user
        String[] userOptions = new String[]{"1. Logout", "2. Edit Profile", "3. Display Summary",
                                            "4. View Past Events", "5. Create Profile Backup", "6. Generate Next Step to Complete",
                                            "7. Import from Calendar File (.ics)", "8. Export to Calendar File (.ics)", "9. Add Monitored Event",
                                            "10. Edit Monitored Event", "11. Add Event", "12. Search for Event"};

        //display possible options indefinitely until user quits
        while(true){
            System.out.println();
            System.out.println("Please enter the number corresponding to the action you wish to do.");
            for(String choice: userOptions ){
                System.out.println(choice);
            }

            //input validation. Checks if input is a number within the valid range.
            if(inputReader.hasNextInt()){
                userChoice = inputReader.nextInt();
            }
            else{
                System.out.println("Please enter a number corresponding to an action. Do not enter a letter or symbol. Please try again.");
            }

            if(userChoice <0 || userChoice >12){
                System.out.println("The number you entered does not correspond to an option. Please try again.");
                userChoice = 0;
            }

            if(userChoice ==1){
                //if user logs out
                //repeats until successful operation
                boolean success = false;
                while(!success){
                    success = logOut();
                }
                
                //breaks out of interaction loop to return to main menu
                break;
            }

            if(userChoice ==2){
                //if user edits profile
                //repeats until successful operation
                //delete profile is an extended use case, prompt user for that option
                System.out.println("Would you like to delete the current profile? (Yes/No) :");
                String deleteProfileInput = inputReader.next();

                if(deleteProfileInput.equalsIgnoreCase("Yes")){
                    //delete profile
                    //won't repeat since no input is required
                    deleteProfile();
                }
                else{
                    //don't delete profile
                    boolean success = false;
                    while(!success){
                        success = editUsername();
                    }
                    success = false;
                    while(!success){
                        success = editPassword();
                    }
                }
                
            }

            if(userChoice == 3){
                //display summary
                //no need to repeat since no input is required
                boolean success = false;
                
                success = displaySummary();

                //Sort Events by severity is an extended use case, prompt for it if displaySumary was successful
                if(success){
                    System.out.println("Would you like to sort the events by severity? (Yes/No): ");
                    String sortEventInput = inputReader.next();

                    if(sortEventInput.equalsIgnoreCase("Yes")){
                        //no need to repeat since no input is required
                        sortEventBySeverity();
                    }
                    //else, do nothing
                }
                
            }

            if(userChoice == 4){
                //view past events
                viewPastEvents();
            }

            if(userChoice == 5){
                //create profile backup
                //repeats until successful operation
                boolean success = false;
                while(!success){
                    success = createProfileBackup();
                }
            }

            if(userChoice == 6){
                //Generate Next Step to complete
                //no need to repeat since no input required
                generateNextStep();
            }

            if(userChoice == 7){
                //import calendar from .ics file
                importCalander();
            }

            if(userChoice == 8){
                //export event list to .ics file
                exportListToCalander();
            }

            if(userChoice == 9){
                //add monitored event
                //repeat until successful entry
                boolean success = false;
                while(!success){
                    success = addMonitoredEvent();
                }
            }

            if(userChoice == 10){
                //edit monitored event
                //delete monitored event is an extended use case. Prompt user for that option
            }
        }
        

    }

    //helper method to close program
    private void closeProgram(){
        inputReader.close();
        System.out.println("Goodbye. Thanks for using RemindMeReggie!");
        System.exit(0);
    }

    //helper method to login to profile
    private boolean loginToProfile(){
        Returns loginAttemptReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = inputReader.next();
        System.out.print("Please enter your password: ");
        String passwordInput = inputReader.next();

        loginAttemptReturn =  ProfileHandler.login(usernameInput, passwordInput);

        if(loginAttemptReturn.getReturnCode() == 0){
            //successful login
            System.out.println("Logged in successfully.");
            return true;
        } 
        else if(loginAttemptReturn.getReturnCode() == -101 || loginAttemptReturn.getReturnCode() == -102){
            //invalid username or password
            System.out.println("Please ensure the username and password are not blank. Please try again.");
            return false;
         }
        else if(loginAttemptReturn.getReturnCode() == -103){
            //unable to hash passwords
            System.out.println("Unable to hash password. Please try again.");
            return false;
         }
        else if(loginAttemptReturn.getReturnCode() == -104){
            //unable to login with entered credentials
            System.out.println("Unable to login with entered credentials. Please ensure your password is correct and you have previously created a profile.");
            return false;
         }
        else{
            System.out.println("Failed to login. Please try again.");
            return false;
         }
            
    }

    //private method for creating profile
    private boolean createProfile(){
        Returns createProfileAttemptReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = inputReader.next();
        System.out.print("Please enter a password of at least 8 characters: ");
        String passwordInput = inputReader.next();

        createProfileAttemptReturn =  ProfileHandler.createNewProfile(usernameInput, passwordInput);
        
        if(createProfileAttemptReturn.getReturnCode() == 0){
            //successful creation
            System.out.println("Profile created successfully.");
            return true;
        } 
        else if(createProfileAttemptReturn.getReturnCode() == -101){
            //invalid username
            System.out.println("Please ensure the username is at least 1 character");
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -102){
            //invalid password
            System.out.println("Please ensure your password is at least 8 characters long.");
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -103){
            //unable to hash passwords
            System.out.println("Unable to hash password. Please try again.");
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -105){
            //unable to save profile
            System.out.println("Unable to save profile. Please try again.");
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -106){
            //username already taken
            System.out.println("Username already taken. Please try again.");
            return false;
         }
         else{
            System.out.println("Failed to create profile. Please try again.");
            return false;
         }
    }

    //helper method for restoring from backup
    private boolean restoreFromBackup(){

        boolean rfbReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = inputReader.next();
        System.out.print("Please enter the exact file location of your backup: ");
        String locationInput = inputReader.next();

        rfbReturn = ProfileBackupHandler.restoreFromBackup(locationInput, PersistenceFactory.persistenceType.JsonFile, usernameInput);

        return false;
    }

    //private helper method for logging out
    private boolean logOut(){
        boolean success = false;

        success = ProfileHandler.logOut();

        if(success){
            System.out.println("Profile successfully saved. Logging out...");
            return true;
        }
        else{
            System.out.println("Profile not saved and could not log out successfully. Please try again.");
            return false;
        }
    }

    //private helper method for adding a monitored event
    private boolean addMonitoredEvent(){
        String eventNameInput = null;
        String eventTypeInput = null;
        int severityLevelInput = -1;
        int yearInput = -1;
        int monthInput = -1;
        int dayInput = -1;
        int hourInput = -1;
        int minuteInput = -1;
        Returns addMonitoredEventAttempt = null;
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        String frequencyInput = null;
        AbstractEvent.Frequencies frequencyChoice = null;

        //prompt for user input and validate
        System.out.println("Please enter the name for the event (Max 50 characters): ");
        eventNameInput =  inputReader.next();
        System.out.println("Please enter the event type (Max 50 characters): ");
        eventTypeInput = inputReader.next();
        System.out.println("Please enter the numerical severity level of the event from 1 to 5: ");
        if(!inputReader.hasNextInt()){
            System.out.println("The severity level must be an integer from 1 to 5. Please try again.");
            return false;
        }

        System.out.println("Please enter the starting year as a positive number (ex:2023): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the year is an integer. Please try again.");
            return false;
        }
        else{
            yearInput = inputReader.nextInt();
            if(yearInput <=0){
                System.out.println("Please ensure the year is a positive integer. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the starting month as a positive number (ex:February is 2): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the month is a positive integer. Please try again.");
            return false;
        }
        else{
            monthInput = inputReader.nextInt();
            if(monthInput <= 0 || monthInput > 12){
                System.out.println("Please ensure the month is between 1 and 12. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the starting day as a positive number (ex:22): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the day is an integer. Please try again.");
            return false;
        }
        else{
            dayInput = inputReader.nextInt();
            if(dayInput <= 0 || dayInput > 31){
                System.out.println("Please ensure the day is a positive integer between 1 and 31. Please try again.");
                return false;
            }

            //compensate for different months having different days
            if((monthInput == 2 && dayInput >28) || (monthInput == 4 && dayInput >30) || (monthInput == 6 && dayInput>30) || (monthInput == 9 && dayInput > 30) || (monthInput == 11 && dayInput > 30)){
                System.out.println("Please ensure the day is a valid one for the given month you entered.");
                return false;
            }
        }

        System.out.println("Please enter the starting hour for the event as a number in the 24-hour clock. (ex: 2pm is 14)");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the hour is an integer. Please try again.");
            return false;
        }
        else{
            hourInput = inputReader.nextInt();
            if(hourInput <0 || hourInput >=24){
                System.out.println("Please ensure the hour is an integer between 0 and 23. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the starting minute of the event as an integer.");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the minute is an integer. Please try again.");
            return false;
        }
        else{
            minuteInput = inputReader.nextInt();
            if(hourInput <0 || hourInput >=60){
                System.out.println("Please ensure the minute is an integer between 0 and 59. Please try again.");
                return false;
            }
        }

        startDateTime = LocalDateTime.of(yearInput,monthInput,dayInput,hourInput,minuteInput);

        System.out.println("Please enter the ending year as a positive number (ex:2023): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the year is an integer. Please try again.");
            return false;
        }
        else{
            yearInput = inputReader.nextInt();
            if(yearInput <=0){
                System.out.println("Please ensure the year is a positive integer. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the ending month as a positive number (ex:February is 2): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the month is a positive integer. Please try again.");
            return false;
        }
        else{
            monthInput = inputReader.nextInt();
            if(monthInput <= 0 || monthInput > 12){
                System.out.println("Please ensure the month is between 1 and 12. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the ending day as a positive number (ex:22): ");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the day is an integer. Please try again.");
            return false;
        }
        else{
            dayInput = inputReader.nextInt();
            if(dayInput <= 0 || dayInput > 31){
                System.out.println("Please ensure the day is a positive integer between 1 and 31. Please try again.");
                return false;
            }

            //compensate for different months having different days
            if((monthInput == 2 && dayInput >28) || (monthInput == 4 && dayInput >30) || (monthInput == 6 && dayInput>30) || (monthInput == 9 && dayInput > 30) || (monthInput == 11 && dayInput > 30)){
                System.out.println("Please ensure the day is a valid one for the given month you entered.");
                return false;
            }
        }

        System.out.println("Please enter the ending hour for the event as a number in the 24-hour clock. (ex: 2pm is 14)");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the hour is an integer. Please try again.");
            return false;
        }
        else{
            hourInput = inputReader.nextInt();
            if(hourInput <0 || hourInput >=24){
                System.out.println("Please ensure the hour is an integer between 0 and 23. Please try again.");
                return false;
            }
        }
        System.out.println("Please enter the ending minute of the event as an integer.");
        if(!inputReader.hasNextInt()){
            System.out.println("Please ensure the minute is an integer. Please try again.");
            return false;
        }
        else{
            minuteInput = inputReader.nextInt();
            if(hourInput <0 || hourInput >=60){
                System.out.println("Please ensure the minute is an integer between 0 and 59. Please try again.");
                return false;
            }
        }

        endDateTime = LocalDateTime.of(yearInput,monthInput,dayInput,hourInput,minuteInput);

        System.out.println("What is the frequency of this event? (Not-Recurring, Daily,Weekly,Monthly, or Yearly)");
        frequencyInput = inputReader.next();

        if(frequencyInput.equalsIgnoreCase("Not-Recurring") || frequencyInput.equalsIgnoreCase("not recurring"))
            frequencyChoice = AbstractEvent.Frequencies.NOT_RECURRING;
        else if(frequencyInput.equalsIgnoreCase("Daily"))
            frequencyChoice = AbstractEvent.Frequencies.DAILY;
        else if(frequencyInput.equalsIgnoreCase("Weekly"))
            frequencyChoice = AbstractEvent.Frequencies.WEEKLY;
        else if(frequencyInput.equalsIgnoreCase("Monthly"))
            frequencyChoice = AbstractEvent.Frequencies.MONTHLY;
        else if(frequencyInput.equalsIgnoreCase("Yearly"))
            frequencyChoice = AbstractEvent.Frequencies.YEARLY;
        else{
            System.out.println("Invalid frequency entered. Please try again.");
            return false;
        }

        addMonitoredEventAttempt = EventHandler.addMonitoredEvent(ProfileHandler.getCurrentProfile(), eventNameInput, startDateTime, endDateTime, eventTypeInput, false, severityLevelInput, frequencyChoice);
        
        if(addMonitoredEventAttempt.getReturnCode() == 0){
            //success
            System.out.println("Monitored Event added successfully");
            return true;
        }
        else if (addMonitoredEventAttempt.getReturnCode() == -1){
            //invalid event name
            System.out.println("Invalid Event Name. Please ensure the event name is between 1 and 50 characters. Please try again.");
            return false;
        }
        else if (addMonitoredEventAttempt.getReturnCode() == -2){
            //invalid event type
            System.out.println("Invalid Event Type. Please ensure the event type is between 1 and 50 characters. Please try again.");
            return false;
        }
        else if (addMonitoredEventAttempt.getReturnCode() == -3){
            //invalid severity level
            System.out.println("Invalid severity level. Please ensure the severity level is between 1 and 5. Please try again.");
            return false;
        }
        else{
            System.out.println("Unable to add monitored event. Please try again.");
            return false;
        }
        
    }

    //private helper method for editing username
    private boolean editUsername(){
        Returns editUsernameAttemptReturn;

        //Prompt for user input for username
        System.out.print("Please enter your new desired username: ");
        String usernameInput = inputReader.next();

        editUsernameAttemptReturn = ProfileHandler.editCurrentProfileUsername(usernameInput);

         if(editUsernameAttemptReturn.getReturnCode() == 0){
            //successful edit
            System.out.println("Username edited successfully.");
            return true;
        } 
        else if(editUsernameAttemptReturn.getReturnCode() == -101){
            //invalid username
            System.out.println("Please ensure the username is at least 1 character. Please try again.");
            return false;
         }
         else if(editUsernameAttemptReturn.getReturnCode() == -105){
            //unable to save profile
            System.out.println("Unable to save profile. Please try again.");
            return false;
         }
         else if(editUsernameAttemptReturn.getReturnCode() == -106){
            //username already taken
            System.out.println("Username already taken. Please try again.");
            return false;
         }
         else if(editUsernameAttemptReturn.getReturnCode() == -107){
            //username already taken
            System.out.println("Previously saved profile unable to be deleted. Please try again.");
            return false;
         }
        else{
            System.out.println("Failed to edit username. Please try again.");
            return false;
        }
            
    }

    //private helper method for editing username
    private boolean editPassword(){
        Returns editPasswordAttemptReturn;

        //Prompt for user input for a new password
        System.out.print("Please enter your new desired password: ");
        String passwordInput = inputReader.next();

        editPasswordAttemptReturn = ProfileHandler.editCurrentProfilePassword(passwordInput);

        if(editPasswordAttemptReturn.getReturnCode() == 0){
            //successful edit
            System.out.println("Password edited successfully.");
            return true;
        }
        else if(editPasswordAttemptReturn.getReturnCode() == -102){
            //invalid password
            System.out.println("Please ensure your password is at least 8 characters long.");
            return false;
         }
        else if(editPasswordAttemptReturn.getReturnCode() == -103){
            //unable to hash passwords
            System.out.println("Unable to hash password. Please try again.");
            return false;
         }
         else if(editPasswordAttemptReturn.getReturnCode() == -105){
            //unable to save profile
            System.out.println("Unable to save profile. Please try again.");
            return false;
         }
        else{
            System.out.println("Failed to edit password. Please try again.");
            return false;
        }
    }

    //private helper method for deleting profile
    private boolean deleteProfile(){
        //reconfirm with user if they would like to delete the profile
        System.out.println("Are you sure you would like to delete the profile? All your events will be lost. (Yes/No) :");
        String deleteProfileInput = inputReader.next();

        if(!deleteProfileInput.equalsIgnoreCase("Yes")){
            //if answer is any input other than yes
            return true;
        }
        else{
            //If answer is yes
            System.out.println("Deleting Profile...");

            boolean success = false;
            success= ProfileHandler.deleteCurrentProfile();

            if(success){
                System.out.println("Profile Deleted Successfully");
                return true;
            }
            else{
                System.out.println("Failed to delete profile. Please try again.");
                return false;
            }
        }
    }

    //private helper method for displaying summary
    private boolean displaySummary(){
        Returns displaySummaryAttemptReturn;
        
        //retrieves events from current profile
        displaySummaryAttemptReturn = EventHandler.displayEventSummary(ProfileHandler.getCurrentProfile().getEvents());

        //returns true if no errors
        if(displaySummaryAttemptReturn.getReturnCode() == 0)
            return true;
        else
            return false;
    }

    //private helper method for sorting events by severity
    private boolean sortEventBySeverity(){
        ArrayList<AbstractEvent> sortedEventList = null;

        //display non-recurring events
        System.out.println("Non-recurring events: ");
        sortedEventList = EventHandler.sortBySeverity(ProfileHandler.getCurrentProfile(), AbstractEvent.Frequencies.NOT_RECURRING);
        for (int i = 0; i < sortedEventList.size(); i++) {
            AbstractEvent event = sortedEventList.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }

        //display daily events
        System.out.println("Daily events: ");
        sortedEventList = EventHandler.sortBySeverity(ProfileHandler.getCurrentProfile(), AbstractEvent.Frequencies.DAILY);
        for (int i = 0; i < sortedEventList.size(); i++) {
            AbstractEvent event = sortedEventList.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }

        //display weekly events
        System.out.println("Weekly events: ");
        sortedEventList = EventHandler.sortBySeverity(ProfileHandler.getCurrentProfile(), AbstractEvent.Frequencies.WEEKLY);
        for (int i = 0; i < sortedEventList.size(); i++) {
            AbstractEvent event = sortedEventList.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }

        //display monthly events
        System.out.println("Monthly events: ");
        sortedEventList = EventHandler.sortBySeverity(ProfileHandler.getCurrentProfile(), AbstractEvent.Frequencies.MONTHLY);
        for (int i = 0; i < sortedEventList.size(); i++) {
            AbstractEvent event = sortedEventList.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }

        //display yearly events
        System.out.println("Non-recurring events: ");
        sortedEventList = EventHandler.sortBySeverity(ProfileHandler.getCurrentProfile(), AbstractEvent.Frequencies.YEARLY);
        for (int i = 0; i < sortedEventList.size(); i++) {
            AbstractEvent event = sortedEventList.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }


        return true;
    }

    //private helper method for viewing past events
    private boolean viewPastEvents(){
        Returns viewPastEventsAttempt = null;
        ArrayList<AbstractEvent> pastEvents = null;
        Pair<ArrayList<AbstractEvent>, Returns> pastEventPair = EventHandler.viewPastEvents(ProfileHandler.getCurrentProfile(), LocalDateTime.MIN, LocalDateTime.now());

        viewPastEventsAttempt = pastEventPair.getValue();
        pastEvents = pastEventPair.getKey();

        if(viewPastEventsAttempt.getReturnCode() == 0){
            System.out.println("Successfully found past events: ");
            for (int i = 0; i < pastEvents.size(); i++) {
                AbstractEvent event = pastEvents.get(i);
                System.out.println("Name: " + event.getEventName());
                System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
                System.out.println("Type: " + event.getEventType());
                System.out.println("Severity: " + event.getSeverityLevel());
                System.out.println("Frequency: " + event.getFrequency());
                System.out.println();
            }
            return true;
        }
        else if (viewPastEventsAttempt.getReturnCode() == -5){
            System.out.println("No events to display.");
            return false;
        }
        else{
            System.out.println("Unable to display past events.");
            return false;
        }

    }

    //private helper method for creating profile backup
    private boolean createProfileBackup(){
        System.out.println("Please enter the exact file location for which you wish to save your backup. :");
        String fileLocation = inputReader.next();
        boolean success = false;
        
        success = ProfileBackupHandler.generateBackup(fileLocation, ProfileHandler.getCurrentProfile(), PersistenceFactory.persistenceType.JsonFile);
        if(!success){
            System.out.println("Unable to generate backup. Please try again.");
            return false;
        }
        System.out.println("Backup generated successfully.");
        return true;
    }

    //private helper method for generating next step to complete
    private boolean generateNextStep(){
        Step nextstep = null;

        nextstep = EventHandler.generateNextStep(ProfileHandler.getCurrentProfile());

        //prints next step
        if(nextstep != null){
            System.out.println("Next step is: " + nextstep.getStepName() + " and is number: " + nextstep.getStepNumber());
        }
        else  
            System.out.println("No available steps to do.");

        return true;

    }

    //private helper method for importing calendar
    private boolean importCalander(){
        //prompt user for file path
        String calanderFilePath = null;
        AbstractEvent event = null;
        System.out.println("Please enter the exact file location of the calendar you wish to import (.ics) :");
        calanderFilePath = inputReader.next();

        //event = CalanderHandler.importFromCalander(calanderFilePath);

        return true;

    }

    private boolean exportListToCalander(){
        String calanderFilePath = null;
        System.out.println("Please enter the exact file location of the calendar you wish to import (.ics) :");
        calanderFilePath = inputReader.next();

        boolean success = false;
        //success = CalanderHandler.exportToCalander(calanderFilePath,ProfileHandler.getCurrentProfile());

        if(success){
            System.out.println("Calander exported successfully.");
            return true;

        }
        else{
            System.out.println("Calander failed to export. Please try again.");
            return false;
        }

    }

}
