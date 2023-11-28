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

    public Profile(String username, String hashedPassword)
    {
        this.username = username;
        this.hashedPassword = hashedPassword;
        nextEventId = 0;
        events = new ArrayList<AbstractEvent>();

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

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

}