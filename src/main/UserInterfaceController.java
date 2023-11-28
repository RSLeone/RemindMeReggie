import java.util.Scanner;

public class UserInterfaceController {
    Profile currentProfile;
    EventController ec = new EventController();
    ProfileBackupController pbc = new ProfileBackupController();
    //ProfileHanlder ph = new ProfileHandler();
    

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
    private void loginToProfile(){
        
    }
}
