package main;
import java.util.Scanner;

//import all other classes
import main.Persistence;
import main.PersistenceFactory;
import main.Profile;
import main.PersistenceFactory.persistenceType;
import main.ProfileBackupHandler;
import main.ProfileHandler;
import main.EventHandler;

public class UserInterfaceController {
    Profile currentProfile;
    EventHandler eh = new EventHandler();
    ProfileBackupHandler pbh = new ProfileBackupHandler();
    

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile p){
        this.currentProfile = p;
    }

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

    public void displayLoginOptions(){
        //login, create profile, close program (end loop)
        //first thing user sees
        //array of possible options for user
        String[] loginOptions = new String[]{"1. Log in", "2. Create Profile","3. Close", "4. Restore Backup"};
       

        //create scanner for user input
        Scanner inputReader = new Scanner(System.in);
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
                
                boolean successfulLogin = false;
                //will continously ask for credentials until successful login
                //upon successful login, breaks loop, closes scanner, and returns to userInteraction
                while(!successfulLogin){
                    successfulLogin = loginToProfile();
                }
                inputReader.close();
                
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
                
                break;
            }

            //if user closes program
            if(userChoice == 3){
                inputReader.close();
                closeProgram();
            }

            //if user restores from backup
            if (userChoice == 4){

            }

            break;
        }

        //close scanner at conclusion of loop/method
        inputReader.close();
    }

    private void displayUserOptions(){
        //create scanner for user input
        Scanner inputReader = new Scanner(System.in);
        int userChoice = 0;        

        //array of possible options for user
        String[] userOptions = new String[]{"1. Logout", "2. Edit Profile", "3. Display Summary",
                                            "4. View Past Events", "5. Create Profile Backup", "6. Generate Next Step to Complete",
                                            "7. Import from Calendar File (.ics)", "8. Export to Calendar File (.ics)", "9. Add Monitored Event",
                                            "10. Edit Monitored Event", "11. Add Event", "12. Search for Event"};

        //display possible options indefinitely until user quits
        while(true){
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
                
                break;
            }

            if(userChoice == 9){

            }

            if(userChoice == 5){
                //create profile backup
                //repeats until successful operation
                boolean success = false;
                while(!success){
                    success = createProfileBackup();
                }
            }
        }
        

        //close scanner at conclusion of loop/method
        inputReader.close();
    }

    //helper method to close program
    private void closeProgram(){
        System.out.println("Goodbye. Thanks for using RemindMeReggie!");
        System.exit(0);
    }

    //helper method to login to profile
    private boolean loginToProfile(){
        Scanner loginScanner = new Scanner(System.in);
        Returns loginAttemptReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = loginScanner.next();
        System.out.print("Please enter your password: ");
        String passwordInput = loginScanner.next();

        loginAttemptReturn =  ProfileHandler.login(usernameInput, passwordInput);

        if(loginAttemptReturn.getReturnCode() == 0){
            //successful login
            System.out.println("Logged in successfully.");
            loginScanner.close();
            return true;
        } 
        else if(loginAttemptReturn.getReturnCode() == -101 || loginAttemptReturn.getReturnCode() == -102){
            //invalid username or password
            System.out.println("Please ensure the username and password are at least 1 character.");
            loginScanner.close();
            return false;
         }
        else if(loginAttemptReturn.getReturnCode() == -103){
            //unable to hash passwords
            System.out.println("Unable to hash password. Please try again.");
            loginScanner.close();
            return false;
         }
        else if(loginAttemptReturn.getReturnCode() == -104){
            //unable to login with entered credentials
            System.out.println("Unable to login with entered credentials. Please ensure your password is correct and you have previously created a profile.");
            loginScanner.close();
            return false;
         }
        else{
            loginScanner.close();
            return false;
         }
            
    }

    //private method for creating profile
    private boolean createProfile(){
        Scanner createProfileScanner = new Scanner(System.in);
        Returns createProfileAttemptReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = createProfileScanner.next();
        System.out.print("Please enter a password of at least 8 characters: ");
        String passwordInput = createProfileScanner.next();

        createProfileAttemptReturn =  ProfileHandler.createNewProfile(usernameInput, passwordInput);
        
        if(createProfileAttemptReturn.getReturnCode() == 0){
            //successful creation
            System.out.println("Profile created successfully.");
            createProfileScanner.close();
            return true;
        } 
        else if(createProfileAttemptReturn.getReturnCode() == -101){
            //invalid username
            System.out.println("Please ensure the username is at least 1 character");
            createProfileScanner.close();
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -102){
            //invalid password
            System.out.println("Please ensure your password is at least 8 characters long.");
            createProfileScanner.close();
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -103){
            //unable to hash passwords
            System.out.println("Unable to hash password. Please try again.");
            createProfileScanner.close();
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -105){
            //unable to save profile
            System.out.println("Unable to save profile. Please try again.");
            createProfileScanner.close();
            return false;
         }
         else if(createProfileAttemptReturn.getReturnCode() == -106){
            //username already taken
            System.out.println("Username already taken. Please try again.");
            createProfileScanner.close();
            return false;
         }
         else{
            createProfileScanner.close();
            return false;
         }
    }

    //helper method for restoring from backup
    private boolean restoreFromBackup(){
        Scanner rfbScanner = new Scanner(System.in);

        boolean rfbReturn;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = rfbScanner.next();
        System.out.print("Please enter the location of your backup: ");
        String locationInput = rfbScanner.next();
        System.out.print("Please enter the type of persistence used for you backup: ");
        String typeInput = rfbScanner.next();

        rfbReturn = ProfileBackupHandler.restoreFromBackup(locationInput, typeInput, usernameInput);

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
        return false;
    }

    //private helper method for creating profile backup
    private boolean createProfileBackup(){
        Scanner cpbInput = new Scanner(System.in);
        System.out.println("Please enter the exact file location for which you wish to save your backup. :");
        String fileLocation = cpbInput.next();
        boolean success = false;
        
        success = ProfileBackupHandler.generateBackup(fileLocation, ProfileHandler.getCurrentProfile(), PersistenceFactory.persistenceType.JsonFile);
        if(!success){
            System.out.println("Unable to generate backup. Please try again.");
            cpbInput.close();
            return false;
        }
        cpbInput.close();
        return true;
    }

}
