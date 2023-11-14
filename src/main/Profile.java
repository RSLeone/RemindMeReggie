package main;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Profile
{

    private String username;
    private String hashedPassword;
    private int nextEventId;
    private ArrayList<AbstractEvent> events;

    public Profile(String username, String passwordStr)
    {
        this.username = username;
        nextEventId = 0;
        events = new ArrayList<AbstractEvent>();

        //Hashes the entered password and stores it in hashedPassword
        setNewPassword(passwordStr);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Hashes newPassword and stores it in hashedPassowrd
     * @param newPassword - the password to be hashed then stored
     */
    public void setNewPassword(String newPassword) {
        hashedPassword = hashPassword(newPassword);
    }

    /**
     * Check if the saved password and entered password are the same
     * @param passwordStr - the entered password to be verified
     * @return true if the hash of passwordStr is the same as the hash of the saved password for this Profile
     */
    public boolean isSamePassword(String passwordStr)
    {
        return hashedPassword.equals(hashPassword(passwordStr));
    }

    public int getNextEventId() {
        return nextEventId;
    }

    public void setNextEventId(int nextEventId) {
        this.nextEventId = nextEventId;
    }

    public ArrayList<AbstractEvent> getEvents() {
        return events;
    }

    /**
     * Hash passwordStr and return the hash
     * @param passwordStr - the password String to be hashed
     */
    private String hashPassword(String passwordStr) {
        String hash = "";

        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(passwordStr.getBytes(StandardCharsets.UTF_8));
            hash = bytesToHex(encodedhash);
        }
        catch(Exception e)
        {
            System.out.println("The password entered could not be hashed. Closing application. Please restart to try again.");
            System.exit(0);
        }

        //Will never return an empty string because if the hash fails the program will end
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