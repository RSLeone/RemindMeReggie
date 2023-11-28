package main;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import main.Persistence;
import main.PersistenceFactory;
import main.Profile;
import main.PersistenceFactory.persistenceType;
import main.Returns;

public class ProfileHandler {
    
    private static Profile currentProfile;

    /**
     * Attempts to login with the entered username and password. If successful, sets the currentProfile to the 
     *      Profile with the entered uesrname
     * @param username - the username of the profile to log in to
     * @param password - the password of the profile to log in to
     * @return 0 if a successful log in occured, otherwise a negative integer that corresponds to what went wrong
     */
    public static Returns login(String username, String password)
    {

        //If the username is invalid return code -101
        if(username.length() <= 0)
        {
            return Returns.INVALID_USERNAME;
        }

        //If the password is invalid return code -102
        if(password.length() <= 0)
        {
            return Returns.INVALID_PASSWORD;
        }

        //Use persistence.load() to get the profile with the passed in username
        PersistenceFactory pf = new PersistenceFactory();
        Persistence persistence = pf.getPersistent(persistenceType.JsonFile);
        Profile loadedProfile = persistence.load(username);

        //If the loadedProfile is null beacuse no Profile with the username passed in exists, return code -104
        if(loadedProfile == null)
        {
            return Returns.COULD_NOT_LOGIN_WITH_ENTERED_CREDENTAILS;
        }
        
        //Hashing the entered password in isSamePassowrd may throw an exception
        try
        {
            if(isSamePassword(loadedProfile, password)) 
            {
                currentProfile = loadedProfile; 
            }
            //If the entered password does not match the password for the username, return code -104
            else
            {
                return Returns.COULD_NOT_LOGIN_WITH_ENTERED_CREDENTAILS;
            }
        }
        //isSamePassword failed to hash the entered password so return code -103
        catch(NoSuchAlgorithmException e)
        {
            return Returns.COULD_NOT_HASH_PASSWORD;
        }
 
        //Successful login so return code 0
        return Returns.SUCCESS;

    }

    /**
     * Attempts to save the current profile and log out the current profile
     * @return true is the current profile could be saved and was logged out sucessfully, otherwise returns false
     */
    public static boolean logOut()
    {
        
        PersistenceFactory pf = new PersistenceFactory();
        Persistence persistence = pf.getPersistent(persistenceType.JsonFile);

        //If the currentProfile could be saved sucessfully, set the currentProfile to null and return true
        if(persistence.save(currentProfile))
        {
            currentProfile = null;
            //The currentProfile was successfully logged out
            return true;
        }

        //The currentProfile could not be saved, so it could not be logged out
        return false;

    }

    /**
     * Attempts to create a new profile and add it to the system
     * @param username - the username of the profile to be created
     * @param unhashedPassword - the password of the profile to be created
     * @return 0 if a successful profile creation occured, otherwise a negative integer that corresponds to what went wrong
     */
    public static Returns createNewProfile(String username, String unhashedPassword)
    {

        //If the username is invalid return code -101
        if(username.length() <= 0)
        {
            return Returns.INVALID_USERNAME;
        }
        //If the password is invalid return code -102
        if(unhashedPassword.length() < 8)
        {
            return Returns.INVALID_PASSWORD;
        }

        PersistenceFactory pf = new PersistenceFactory();
        Persistence persistence = pf.getPersistent(persistenceType.JsonFile);

        //If the username desired is already taken return code -106
        if(persistence.load(username) != null)
        {
            return Returns.USERNAME_ALREADY_TAKEN;
        }

        //Hashing the entered password in hashPassword may throw an exception
        try 
        {
            String hashedPassword = hashPassword(unhashedPassword);
            Profile newProfile = new Profile(username, hashedPassword);

            //If the system fails to save the new profile, return code -105
            if(!persistence.save(newProfile))
            {
                return Returns.COULD_NOT_SAVE_PROFILE;
            }

        }
        //hashPassword failed to hash the entered password so return code -103
        catch(NoSuchAlgorithmException e)
        {
            return Returns.COULD_NOT_HASH_PASSWORD;
        }

        //The new profile was created and saved sucessfully so return code 0
        return Returns.SUCCESS;
    }

    /**
     * Attempts to update the currentProfile's username, save the profile, and delete the file that uses the old username
     * @param newUserName - the username that the currentProfile's username should be updated to
     * @return 0 if the currentProfile's username was updated and was saved successfully, otherwise a negative integer that corresponds to what went wrong
     */
    public static Returns editCurrentProfileUsername(String newUserName)
    {

        //Need to save the previous username in case the save fails
        String previousUsername = currentProfile.getUsername();

        //If the newUsername is invalid return code -101
        if(newUserName.length() <= 0)
        {
            return Returns.INVALID_USERNAME;
        }

        PersistenceFactory pf = new PersistenceFactory();
        Persistence persistence = pf.getPersistent(persistenceType.JsonFile);

        //If the username desired is already taken return code -106
        if(persistence.load(newUserName) != null)
        {
            return Returns.USERNAME_ALREADY_TAKEN;
        }

        //Update the currentProfile's username
        currentProfile.setUsername(newUserName);

        //If the system fails to save the new profile, revert the currentProfile's username and return code -105
        if(!persistence.save(currentProfile))
        {
            currentProfile.setUsername(previousUsername);
            return Returns.COULD_NOT_SAVE_PROFILE;
        }

        //If the current profile with the updated username could be saved, need to delete the old file that uses the old username
        if(!persistence.delete(previousUsername))
        {
            //If the previousUsername version of the profile could not be deleted return code -107
            return Returns.OLD_USERNAME_FILE_NOT_DELETED;
        }

        //The system update the currentProfile's username, saved the currentProfile, and delted the old username file successfullly so return code 0
        return Returns.SUCCESS;
    }

    /**
     * Attempts to edit the currentProfile's hashedPassword and save the currentProfile
     * @param newPassword - the new password to be hashed and update the currentProfile
     * @return 0 if the the currentProfile's hashedPassword was updated and the currentProfile was saved sucessfully, otherwise a negative integer that corresponds to what went wrong
     */
    public static Returns editCurrentProfilePassword(String newPassword) 
    {

        //If the newPassword has an invalid length return code
        if(newPassword.length() < 8)
        {
            return Returns.INVALID_PASSWORD;
        }

        //Hashing the entered password in hashPassword may throw an exception
        try
        {
            //Need to store the previous hashedPassword in case the save fails
            String previousHashedPassword = currentProfile.getHashedPassword();

            //Update the currentProfile's hashedPassword
            currentProfile.setHashedPassword(hashPassword(newPassword));
            
            PersistenceFactory pf = new PersistenceFactory();
            Persistence persistence = pf.getPersistent(persistenceType.JsonFile);

            //Failed to save the currentProfile so revert the hashedPassword of the currentProfile and return code -105
            if(!persistence.save(currentProfile))
            {
                currentProfile.setHashedPassword(previousHashedPassword);
                return Returns.COULD_NOT_SAVE_PROFILE;
            }
        }
        //hashPassword failed to hash the entered password so return code -103
        catch(NoSuchAlgorithmException e)
        {
            return Returns.COULD_NOT_HASH_PASSWORD;
        }
        
        //The currentProfile's hashedPassword was updated and the currentProfile was saved sucessfully so return code 0
        return Returns.SUCCESS;

    }

    /**
     * Attemps to delete the current profile
     * @return true if the profile was sucessfully delted, otherwise false
     */
    public static boolean deleteCurrentProfile()
    {
        PersistenceFactory pf = new PersistenceFactory();
        Persistence persistence = pf.getPersistent(persistenceType.JsonFile);
    
        return persistence.delete(currentProfile.getUsername());
    }

    //Getter for currentProfile
    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    /**
     * Check if the saved hashed password for the loaded profile and the hash entered password are the same
     * @param loadedProfile - the loaded profile form the persistence
     * @param passwordStr - the entered password to be verified
     * @return true if the hash of passwordStr is the same as the hash of the saved password for currentProfile, otherwise false
     * @throws NoSuchAlgorithmException if passwordStr could not be hashed
     */
    private static boolean isSamePassword(Profile loadedProfile, String passwordStr) throws NoSuchAlgorithmException
    {
        return loadedProfile.getHashedPassword().equals(hashPassword(passwordStr));
    }

    /**
     * Hashes the passed in string
     * @param passwordStr - the password string to be hashed
     * @return the hash of passwordStr
     * @throws NoSuchAlgorithmException when SHA-256 cannot be found as a valid algorithm
     */
    private static String hashPassword(String passwordStr) throws NoSuchAlgorithmException{

        String hash;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(passwordStr.getBytes(StandardCharsets.UTF_8));
        hash = bytesToHex(encodedhash);

        return hash;
    }

    /**
     * Converts the entered hash bytes array into hexidecimal String so that it can be stored
     * @param hash - the hash value to be converted
     * @return the hexidecimal String of hash
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
