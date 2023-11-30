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
        displayLoginOptions();
        //displayUserOptions();
        
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
                

                //upon successful login, breaks loop, closes scanner, and returns to userInteraction
                //loginAttemptCode = loginToProfile(); //calls helper method
                break;
            }

            //if user creates a profile
            if(userChoice == 2){

            }

            //if user closes program
            if(userChoice == 3){
                closeProgram();
            }

            //if user restores from backup
            if(userChoice == 4){

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
            break;
        }
        

        //close scanner at conclusion of loop/method
        inputReader.close();
    }

    //helper method to close program
    private void closeProgram(){
        System.exit(0);
    }

    //helper method to login to profile
    private boolean loginToProfile(){
        Scanner loginScanner = new Scanner(System.in);
        int loginAttemptCode = 0;

        //Prompt for user input for username and password
        System.out.print("Please enter your username: ");
        String usernameInput = loginScanner.next();
        System.out.print("Please enter your password: ");
        String passwordInput = loginScanner.next();

        loginAttemptCode =  ProfileHandler.login(usernameInput, passwordInput);
        if(loginAttemptCode == 0) //successful login
            return true;
        if(loginAttemptCode == -101){
            //invalid username
            System.out.println("Please ensure the username is at least 1 character.");
            return false;
         }
         if(loginAttemptCode == -102){
            //invalid password
            System.out.println("Please ensure the password is at least 1 character.");
            return false;
         }
         if(loginAttemptCode == -103){
            
         }
    }

}
