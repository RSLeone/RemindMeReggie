package main;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            System.out.println();
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
                boolean success = false;
                //upon successful login, breaks loop, and returns to userInteraction
                success = loginToProfile();

                if(success){
                    break;
                }
                
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
                restoreFromBackup();
            }
            
        }

    }

    private void displayUserOptions(){
        int userChoice = 0;        

        //array of possible options for user
        String[] userOptions = new String[]{"1. Logout", "2. Edit Profile", "3. Display Summary",
                                            "4. View Past Events", "5. Create Profile Backup", "6. Generate Next Step to Complete",
                                            "7. Import from Calendar File (.ics)", "8. Export to Calendar File (.ics)", "9. Add Monitored Event",
                                            "10. Add Event", "11. Search for Event"};

        //display possible options indefinitely until user quits
        while(true){
            System.out.println();
            System.out.println("Please enter the number corresponding to the action you wish to do.");
            for(String choice: userOptions ){
                System.out.println(choice);
            }

            inputReader.nextLine();
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
                logOut();
                
                //breaks out of interaction loop to return to main menu
                break;
            }

            if(userChoice ==2){
                //if user edits profile
                //prompt user to edit username, password, or delete profile
                String[] editProfileOptions = new String[]{"1. Edit username", "2. Edit Password", "3. Delete Profile"};
                int editProfileInput = 0;

                System.out.println();
                System.out.println("Please enter the number corresponding to the action you wish to do.");
                for(String choice: editProfileOptions ){
                    System.out.println(choice);
                }

                //input validation. Checks if input is a number within the valid range.
                if(inputReader.hasNextInt()){
                    editProfileInput = inputReader.nextInt();
                }
                else{
                    System.out.println("Please enter a number corresponding to an action. Do not enter a letter or symbol. Please try again.");
                }

                if(editProfileInput <0 || editProfileInput >3){
                    System.out.println("The number you entered does not correspond to an option. Please try again.");
                }
                
                if(editProfileInput == 3){
                    //delete profile
                    //won't repeat since no input is required
                    deleteProfile();

                    //will exit to main menu since profile is gone
                    break;
                }
                if(editProfileInput ==1){
                    //don't delete profile
                    boolean success = false;
                    while(!success){
                        success = editUsername();
                    }
                }
                if(editProfileInput == 2){
                    boolean success = false;
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
                        sortEventBySeverity();
                    }
                    //else, do nothing
                }
                
            }

            if(userChoice == 4){
                //view past events
                viewPastEvents();
                userChoice = 0;
            }

            if(userChoice == 5){
                //create profile backup
                //repeats until successful operation
                boolean success = false;
                while(!success){
                    success = createProfileBackup();
                }
                userChoice = 0;
            }

            if(userChoice == 6){
                //Generate Next Step to complete
                //no need to repeat since no input required
                generateNextStep();
                userChoice = 0;
            }

            if(userChoice == 7){
                //import calendar from .ics file
                importCalander();
                userChoice = 0;
            }

            if(userChoice == 8){
                //export event list to .ics file
                exportListToCalander();
                userChoice = 0;
            }

            if(userChoice == 9){
                //add monitored event, add respective steps
                boolean success = false;
                success = addEvent();

                if(success)
                    addSteps();

                userChoice = 0;
            }

            if(userChoice == 10){
                //add regular event
                addEvent();
                userChoice = 0;
            }

            if(userChoice == 11){
                //search for events. From here, the user can find an event and edit/remove it if they wish.
                AbstractEvent foundEvent = null;
                foundEvent = searchforEvent();

                //if an event is found, ask to edit or delete
                if(foundEvent != null){
                    
                    String editInput = null;
                    //prompt user to edit/delete found event if they wish
                    System.out.println("Would you like to edit the found event? (Yes/No)");
                    editInput = inputReader.next();

                    if(editInput.equalsIgnoreCase("Yes")){
                        //edit event
                        editEvent(foundEvent);
                    }
                    else{
                        System.out.println("Would you like to delete the found event? (Yes/No)");
                        editInput= inputReader.next();

                        if(editInput.equalsIgnoreCase("Yes")){
                            //delete event
                            removeEvent(foundEvent);
                        }
                    }
                }
                else{
                    System.out.println("The event could not be found.");
                }
                userChoice = 0;
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
            System.out.println("Please ensure the username is not blank. Please try again.");
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -102){
            //invalid password
            System.out.println("Please ensure your password is at least 8 characters long. Please try again.");
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
        boolean rfbsuccess = false;
        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = inputReader.next();
        System.out.print("Please enter the exact file location of your backup: ");
        String locationInput = inputReader.next();

        rfbsuccess= ProfileBackupHandler.restoreFromBackup(locationInput, PersistenceFactory.persistenceType.JsonFile, usernameInput);

        if(rfbsuccess){
            System.out.println("Restoration successful.");
            return true;
        }
        else{
            System.out.println("Failed to restore profile. Please try again.");
            return false;
        }
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

    //private helper method for adding events. Parameter is true if monitored event, false if regular event
    private boolean addEvent(){
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
        System.out.print("Please enter the name for the event (Max 50 characters): ");
        eventNameInput =  inputReader.next();
        inputReader.nextLine();
        System.out.print("Please enter the event type (Max 50 characters): ");
        eventTypeInput = inputReader.next();
        inputReader.nextLine();
        System.out.print("Please enter the numerical severity level of the event from 1 to 5: ");
        if(!inputReader.hasNextInt()){
            System.out.println("The severity level must be an integer from 1 to 5. Please try again.");
            return false;
        }
        else{
            severityLevelInput = inputReader.nextInt();
        }
        inputReader.nextLine();


        System.out.print("Please enter the starting year as a positive number (ex:2023. Input must be a positive number): ");
        
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

        inputReader.nextLine();
        System.out.print("Please enter the starting month as a positive number (ex:February is 2. Input must be between 1 and 12): ");
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

        inputReader.nextLine();
        System.out.print("Please enter the starting day as a positive number (ex:22. Input must be between 1 and 31): ");
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

        inputReader.nextLine();
        System.out.print("Please enter the starting hour for the event as a number in the 24-hour clock. (ex: 2pm is 14. Input must be between 0 and 23) : ");
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

        inputReader.nextLine();
        System.out.print("Please enter the starting minute of the event as an integer. Input must be between 0 and 59 : ");
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

        System.out.print("Please enter the ending year as a positive number (ex:2023. Input must be a positive number): ");
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

        inputReader.nextLine();
        System.out.print("Please enter the ending month as a positive number (ex:February is 2. Input must be between 1 and 12): ");
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
        System.out.print("Please enter the ending day as a positive number (ex:22. Input must be between 1 and 31): ");
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

        System.out.print("Please enter the ending hour for the event as a number in the 24-hour clock. (ex: 2pm is 14. Input must be between 0 and 23) : ");
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
        System.out.print("Please enter the ending minute of the event as an integer. Input must between 0 and 59 : ");
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

        System.out.print("What is the frequency of this event? (Not-Recurring, Daily,Weekly,Monthly, or Yearly) : ");
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

        //create event
        addMonitoredEventAttempt = EventHandler.addMonitoredEvent(ProfileHandler.getCurrentProfile(), eventNameInput, startDateTime, endDateTime, eventTypeInput, false, severityLevelInput, frequencyChoice);
        
        if(addMonitoredEventAttempt.getReturnCode() == 0){
            //success
            System.out.println("Event added successfully");
            
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
            System.out.println("Unable to add event. Please try again.");
            return false;
        }
        
    }

   // eventhandler?????
    //private helper method for adding recurring events
    private boolean addRecurringEvents(AbstractEvent eventToAdd){
        AbstractEvent.Frequencies frequency = eventToAdd.getFrequency();
        int yearInput = -1;
        LocalDateTime startDateTime = eventToAdd.getStartDateTime();
        LocalDateTime initialEndDateTime = eventToAdd.getEndDateTime();
        if (frequency == AbstractEvent.Frequencies.YEARLY) {

            //ask user when recurrence ends and find time difference
            System.out.println("Please enter the ending year of the reccurence as a positive number (ex:2023. Input must be a positive number): ");
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
            
            LocalDateTime endDateTime = LocalDateTime.of(yearInput,eventToAdd.getStartDateTime().getMonth(),eventToAdd.getStartDateTime().getDayOfMonth(),eventToAdd.getStartDateTime().getHour(),eventToAdd.getStartDateTime().getMinute());
            long yearsBetween = 0;
            yearsBetween = ChronoUnit.YEARS.between(eventToAdd.getStartDateTime(), endDateTime);
            int listSize = 0;

            //create as many events as required for recurrence. Copies contents of initial event and edits to accommodate for new DateTime
            for(int i = 1; i<=yearsBetween; i++){
                ProfileHandler.getCurrentProfile().getEvents().add(eventToAdd);
                listSize = ProfileHandler.getCurrentProfile().getEvents().size();

                //edits event to change time
                ProfileHandler.getCurrentProfile().getEvents().get(listSize-1).setStartTime(startDateTime.plusYears(i));
                ProfileHandler.getCurrentProfile().getEvents().get(listSize-1).setStartTime(initialEndDateTime.plusYears(i));
            }
        }
        return true;
    }

    
    //private helper method for adding steps - work in progress
    private boolean addSteps(){
        //if the specified event is an monitored event, ask for step information
            int numSteps = 0;
            String editEventInput = null;
            int listSize = 0;
            System.out.println("How many steps are there in this event? Please enter a positive number.");
            if(inputReader.hasNextInt()){
                numSteps = inputReader.nextInt();

                if(numSteps <= 0){
                    System.out.println("Do not enter a nonpositive number. Please try again.");
                    return false;
                }
                else{
                    Step stepToAdd = null;
                    String stepName = null;

                    //create as many steps as the user specified
                    for(int i = 1; i<=numSteps; i++){
                        listSize = ProfileHandler.getCurrentProfile().getEvents().size();

                        System.out.println("What is Step #" + i + "'s title?");
                        editEventInput = inputReader.next();
                        stepName = editEventInput;
                        stepToAdd = new Step(stepName, i, false);
                        
                        //grab monitoredevent to edit
                        MonitoredEvent m = (MonitoredEvent)(ProfileHandler.getCurrentProfile().getEvents().get(listSize-1));
                        EventHandler.addStep(m, stepToAdd);
                    }

                    System.out.println("Monitored event steps created successfully");
                    return true;
                }
            }
            else{
                System.out.println("Do not enter a letter or number. Please try again.");
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
            System.out.println("Please ensure the username is not blank. Please try again.");
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
            //unable to delete old profile
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
                System.out.println("Profile Deleted Successfully. Returning to main menu.");
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
        
        //retrieves events from current profile. output is created from event handler
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
        ArrayList<AbstractEvent> importedList = null;
        System.out.println("Please enter the exact file location of the calendar you wish to import (.ics) :");
        calanderFilePath = inputReader.next();

        importedList = CalanderHandler.importFromCalander(calanderFilePath);
        
        if(importedList == null){
            //if import fails
            System.out.println("Unable to import list. Please try again");
            return false;
        }
        else{
            //if import succeeds, add imported events to current event list
            for(int i = 0; i<importedList.size(); i++){
                ProfileHandler.getCurrentProfile().getEvents().add(importedList.get(i));
            }

            System.out.println("Import successful. The events list has been updated.");
            return true;
        }
    }

    //private helper method for exporting event listto Calander
    private boolean exportListToCalander(){
        String calanderFilePath = null;
        System.out.println("Please enter the exact file location of where to wish to send the export :");
        calanderFilePath = inputReader.next();

        boolean success = false;

        success = CalanderHandler.exportToCalander(calanderFilePath,ProfileHandler.getCurrentProfile());

        if(success){
            System.out.println("Calander exported successfully.");
            return true;
        }
        else{
            System.out.println("Calander failed to export. Please try again.");
            return false;
        }
    }


    //private helper method for input validation in searching for events
    //returns the user's choice for option, 0 if invalid input
    private int searchForEventValidation(){
        //prompt user to edit username, password, or delete profile
        String[] searchEventOptions = new String[]{"1. Start Date", "2. Severity", "3. Type"};
        int searchInput = 0;

        System.out.println();
        System.out.println("Please enter the number corresponding to the action you wish to do.");
        for(String choice: searchEventOptions ){
            System.out.println(choice);
        }

        //input validation. Checks if input is a number within the valid range.
        if(inputReader.hasNextInt()){
            searchInput = inputReader.nextInt();
        }
        else{
            System.out.println("Please enter a number corresponding to an action. Do not enter a letter or symbol. Please try again.");
            searchInput = 0;
            return searchInput;
        }

        if(searchInput <0 || searchInput >3){
            System.out.println("The number you entered does not correspond to an option. Please try again.");
            searchInput = 0;
            return searchInput;
        }
        return searchInput;
    }

    //private helper method for searchforEvents
    private AbstractEvent searchforEvent(){
        //if no events exist, exit
        if(ProfileHandler.getCurrentProfile().getEvents().size() == 0){
            System.out.println("No events exist.");
            return null;
        }

        //pair that will be returned upon searching
        Pair<AbstractEvent, Returns> returnPair = null;
        Returns returnAttempt = null;

        //continously checks for input validation until correct
        int searchInput = 0;
        while(searchInput ==0){
            searchInput = searchForEventValidation();
        }

        AbstractEvent foundEvent = null;

        //if searching by start date
        if(searchInput == 1){
            int dayInput = -1;
            int monthInput = -1;
            int yearInput = -1;
            int minuteInput = -1;
            int hourInput = -1;

            System.out.println("Please enter the starting year as a positive number (ex:2023. Input must be a positive number): ");
            if(!inputReader.hasNextInt()){
                System.out.println("Please ensure the year is an integer. Please try again.");
                return null;
            }
            else{
                yearInput = inputReader.nextInt();
                if(yearInput <=0){
                    System.out.println("Please ensure the year is a positive integer. Please try again.");
                    return null;
                }
            }

            System.out.println("Please enter the starting month as a positive number (ex:February is 2. Must be between 1 and 12): ");
            if(!inputReader.hasNextInt()){
                System.out.println("Please ensure the month is a positive integer. Please try again.");
                return null;
            }
            else{
                monthInput = inputReader.nextInt();
                if(monthInput <= 0 || monthInput > 12){
                    System.out.println("Please ensure the month is between 1 and 12. Please try again.");
                    return null;
                }
            }

            System.out.println("Please enter the starting day as a positive number (ex:22. Input must be between 1 and 31): ");
            if(!inputReader.hasNextInt()){
                System.out.println("Please ensure the day is an integer. Please try again.");
                return null;
            }
            else{
                dayInput = inputReader.nextInt();
                if(dayInput <= 0 || dayInput > 31){
                    System.out.println("Please ensure the day is a positive integer between 1 and 31. Please try again.");
                    return null;
                }

                //compensate for different months having different days
                if((monthInput == 2 && dayInput >28) || (monthInput == 4 && dayInput >30) || (monthInput == 6 && dayInput>30) || (monthInput == 9 && dayInput > 30) || (monthInput == 11 && dayInput > 30)){
                    System.out.println("Please ensure the day is a valid one for the given month you entered.");
                    return null;
                }
            }

            System.out.println("Please enter the starting hour for the event as a number in the 24-hour clock. (ex: 2pm is 14. Input must be between 0 and 23)");
            if(!inputReader.hasNextInt()){
                System.out.println("Please ensure the hour is an integer. Please try again.");
                return null;
            }
            else{
                hourInput = inputReader.nextInt();
                if(hourInput <0 || hourInput >=24){
                    System.out.println("Please ensure the hour is an integer between 0 and 23. Please try again.");
                    return null;
                }
            }

            System.out.println("Please enter the starting minute of the event as an integer. (Input must between 0 and 59)");
            if(!inputReader.hasNextInt()){
                System.out.println("Please ensure the minute is an integer. Please try again.");
                return null;
            }
            else{
                minuteInput = inputReader.nextInt();
                if(hourInput <0 || hourInput >=60){
                    System.out.println("Please ensure the minute is an integer between 0 and 59. Please try again.");
                    return null;
                }
            }

            //search event with given time
            LocalDateTime startDateTime = LocalDateTime.of(yearInput,monthInput,dayInput,hourInput,minuteInput);
            returnPair = EventHandler.searchForEventDateTime(ProfileHandler.getCurrentProfile(), startDateTime);
            returnAttempt = returnPair.getValue();
        }

        //searching by severity
        if(searchInput ==2){
            int severityInput = 0;
            System.out.println("Please enter the severity level of the event you wish to search for.");
            if(inputReader.hasNextInt()){
                severityInput = inputReader.nextInt();
            }
            else{
                System.out.println("Please enter a number corresponding to an action. Do not enter a letter or symbol. Please try again.");
                return null;
            }

            returnPair= EventHandler.searchForEventSeverity(ProfileHandler.getCurrentProfile(), severityInput);
            returnAttempt = returnPair.getValue();
        }

        //searching by type
        if(searchInput == 3){
            String typeInput = null;
            System.out.println("Please enter the event type you wish to search for: ");
            typeInput = inputReader.next();

            returnPair = EventHandler.searchForEventType(ProfileHandler.getCurrentProfile(), typeInput);
            returnAttempt = returnPair.getValue();
        }


        //check to see if returned event was valid
        if(returnAttempt.getReturnCode() == 0){
            //success
            System.out.println("Event successfully found.");
            foundEvent = returnPair.getKey();
            return foundEvent;
        }
        else if (returnAttempt.getReturnCode() == -2){
            //invalid event type
            System.out.println("Invalid Event Type. Please ensure the event type is between 1 and 50 characters. Please try again.");
            return null;
        }
        else if (returnAttempt.getReturnCode() == -3){
            //invalid severity level
            System.out.println("Invalid severity level. Please ensure the severity level is between 1 and 5. Please try again.");
            return null;
        }
        else if (returnAttempt.getReturnCode() == -4){
            //event doesn't exist
            System.out.println("Event doesn't exist. Please try again.");
            return null;
        }
        else{
            System.out.println("Unable to find event. Please try again.");
            return null;
        }
        
    }

    private boolean editEvent(AbstractEvent foundEvent){
        //able to edit name, start time, end time, severity, frequency
        String editEventInput = null;
        Returns editingAttempt = null;

        //ask user if they wish to edit each attribute
        System.out.println("Would you like to edit the event name? (Yes/No)");
        editEventInput = inputReader.next();
        if(editEventInput.equalsIgnoreCase("Yes")){
            //editname
            System.out.println("What is the name for the event? (Between 0 and 50 characters)");
            editEventInput = inputReader.next();
            editingAttempt = EventHandler.editEventName(ProfileHandler.getCurrentProfile(), foundEvent, editEventInput);

            if(editingAttempt.getReturnCode() == -1){
                //invalid event name
                System.out.println("Invalid event name. Please try again.");
                return false;
            }
            if (editingAttempt.getReturnCode() == -4){
                //event doesn't exist
                System.out.println("Event does not exist. Please try again.");
            }
        }

        System.out.println("Would you like to edit the start time? (Yes/No)");
        editEventInput = inputReader.next();
        if(editEventInput.equalsIgnoreCase("Yes")){
            //editstart time
            int dayInput = -1;
            int monthInput = -1;
            int yearInput = -1;
            int minuteInput = -1;
            int hourInput = -1;

            System.out.println("Please enter the starting year as a positive number (ex:2023. Input must be a positive number): ");
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
            System.out.println("Please enter the starting month as a positive number (ex:February is 2. Must be between 1 and 12): ");
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
            System.out.println("Please enter the starting day as a positive number (ex:22. Input must be between 1 and 31): ");
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

            System.out.println("Please enter the starting hour for the event as a number in the 24-hour clock. (ex: 2pm is 14. Input must be between 0 and 23)");
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

            System.out.println("Please enter the starting minute of the event as an integer. (Input must between 0 and 59)");
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

            //edit event with new time
            LocalDateTime startDateTime = LocalDateTime.of(yearInput,monthInput,dayInput,hourInput,minuteInput);

            editingAttempt= EventHandler.editEventStartDateTime(ProfileHandler.getCurrentProfile(), foundEvent, startDateTime);

            if(editingAttempt.getReturnCode() == -4){
                //if event doesn't exist
                System.out.println("Event doesn't exist. Please try again.");
                return false;
            }

        }

        System.out.println("Would you like to edit the end time? (Yes/No)");
        editEventInput = inputReader.next();
        if(editEventInput.equalsIgnoreCase("Yes")){
            int dayInput = -1;
            int monthInput = -1;
            int yearInput = -1;
            int minuteInput = -1;
            int hourInput = -1;

            //edit end time
            System.out.println("Please enter the ending year as a positive number (ex:2023. Input must be a positive number): ");
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

            System.out.println("Please enter the ending month as a positive number (ex:February is 2. Input must be between 1 and 12): ");
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

            System.out.println("Please enter the ending day as a positive number (ex:22. Input must be between 1 and 31): ");
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

            System.out.println("Please enter the ending hour for the event as a number in the 24-hour clock. (ex: 2pm is 14. Input must be between 0 and 23)");
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
            System.out.println("Please enter the ending minute of the event as an integer. Input must between 0 and 59");
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

            //edit event with new time
            LocalDateTime endDateTime = LocalDateTime.of(yearInput,monthInput,dayInput,hourInput,minuteInput);

            editingAttempt= EventHandler.editEventEndDateTime(ProfileHandler.getCurrentProfile(), foundEvent, endDateTime);

            if(editingAttempt.getReturnCode() == -4){
                //if event doesn't exist
                System.out.println("Event doesn't exist. Please try again.");
                return false;
            }

            //REVISIT WITH NEW ERROR
        }

        System.out.println("Would you like to edit the severity? (Yes/No)");
        editEventInput = inputReader.next();
        if(editEventInput.equalsIgnoreCase("Yes")){
            //edit severity
            int severityInput = 0;
            System.out.println("What is the severity for the event? (Between 1 and 5)");
            if(inputReader.hasNextInt()){
                severityInput = inputReader.nextInt();
            }
            editingAttempt = EventHandler.editEventSeverity(ProfileHandler.getCurrentProfile(), foundEvent, severityInput);

            if(editingAttempt.getReturnCode() == -3){
                //invalid severity level
                System.out.println("Invalid severity level. Please try again.");
                return false;
            }
            if (editingAttempt.getReturnCode() == -4){
                //event doesn't exist
                System.out.println("Event does not exist. Please try again.");
            }
        }

        System.out.println("Would you like to edit the frequency? (Yes/No)");
        editEventInput = inputReader.next();
        if(editEventInput.equalsIgnoreCase("Yes")){
            //edit frequency
            AbstractEvent.Frequencies frequencyChoice = null;

            System.out.println("What is the frequency of the event? (Not Recurring, Daily, Weekly, Monthly, Yearly)");
            editEventInput = inputReader.next();
            if(editEventInput.equalsIgnoreCase("Not-Recurring") || editEventInput.equalsIgnoreCase("not recurring"))
                frequencyChoice = AbstractEvent.Frequencies.NOT_RECURRING;
            else if(editEventInput.equalsIgnoreCase("Daily"))
                frequencyChoice = AbstractEvent.Frequencies.DAILY;
            else if(editEventInput.equalsIgnoreCase("Weekly"))
                frequencyChoice = AbstractEvent.Frequencies.WEEKLY;
            else if(editEventInput.equalsIgnoreCase("Monthly"))
                frequencyChoice = AbstractEvent.Frequencies.MONTHLY;
            else if(editEventInput.equalsIgnoreCase("Yearly"))
                frequencyChoice = AbstractEvent.Frequencies.YEARLY;
            else{
                System.out.println("Invalid frequency entered. Please try again.");
                return false;
            }

            editingAttempt = EventHandler.editEventFrequency(ProfileHandler.getCurrentProfile(), foundEvent, frequencyChoice);

            
            if (editingAttempt.getReturnCode() == -4){
                //event doesn't exist
                System.out.println("Event does not exist. Please try again.");
            }
        }



        //edit monitored event
        //if the found event is an monitored event, edit MonitoredEvent's unique attributes (adding a step)
        if(foundEvent instanceof MonitoredEvent){
            System.out.println("Would you like to add a step? (Yes/No)");
            editEventInput = inputReader.next();
            if(editEventInput.equalsIgnoreCase("Yes")){
                String stepName = null;
                //add a step
                System.out.println("What is the name of the event?");
                stepName = inputReader.next();

                //adds step to end of event
                Step nextStep = new Step(stepName,((MonitoredEvent)foundEvent).getNumSteps() + 1 , false);
                EventHandler.addStep((MonitoredEvent)foundEvent, nextStep);

            }

        }

        return true;
    }

    //private helper method for removing an event
    private boolean removeEvent(AbstractEvent event){
        Returns removeEventAttempt = null;

        removeEventAttempt = EventHandler.removeEvent(ProfileHandler.getCurrentProfile(), event);

        if(removeEventAttempt.getReturnCode() == 0){
            //success
            System.out.println("Event successfully removed.");
            return true;
        }
        else if (removeEventAttempt.getReturnCode() == -4){
            //event does not exist
            System.out.println("Event doesn't exist. Please try again.");
            return false;
        }
        else{
            System.out.println("Failed to remove event. Please try again.");
            return false;
        }
    }


}
