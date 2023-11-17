package main;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import main.PersistenceFactory.persistenceType;

public class ProfileHandler {
    
    private static Profile currentProfile;

    //Revisit this after Andrew finishes presistence
    public static int login(String username, String password)
    {
        //Search use persistence.load() to get the profile witht the passed in username and make sure the password is valid
        PersistenceFactory pf = new PersistenceFactory();
        pf.getPersistent(persistenceType.LocalFile)
        Profile loadedProfile = persistence.load();
        try
        {
            if(same password entered) 
            {
                currentProfile = loadedProfile; 
            }
            else
            {

            }
        }
        catch
        {
            checking that the passwords are the same may throw an exception 
        }

        */
 
        return 0;

    }

    //Revisit this after Andrew finished presistence
    public static boolean logOut()
    {
        //Save the profile useing persistence.save()
        currentProfile = null;

        return true;
    }

    //Revisit this after Andrew finished presistence
    public static int createNewProfile(String username, String unhashedPassword)
    {
        try 
        {
            String hashedPassword = hashPassword(unhashedPassword);
            Profile newProfile = new Profile(username, hashedPassword);
            //Save the profile useing persistence.save()
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("The password entered could not be set. Please try creating a profile again.");
        }

        return true;
    }

    //Revisit
    public static int editCurrentProfileUsername(String newUserName)
    {
        //Save the profile using persistence.save()

        return true;
    }

    //Revisit
    /**
    * If possible, hashes newPassword and sets the currentProfile's hashed password to the result and saves the profile, 
    *       otherwise prints that the new password could not be set
    * @param newPassword - the password to be hashed then set
    */
    public static int editCurrentProfilePassword(String newPassword) 
    {
        
        try
        {
            currentProfile.setHashedPassword(hashPassword(newPassword));
            //Save the profile using persistence.save()
            PersistenceFactory pf = new PersistenceFactory();
            pf.getPersistent(persistenceType.LocalFile);
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("The new password entered could not be set. Please try setting a new password again.");
            return false;
        }
        
        return true;

    }

    public static boolean deleteCurrentProfile()
    {

    }

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
