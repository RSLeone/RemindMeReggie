package testing;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;

public class JsonFileTest {
    Profile profile;
    Persistence persistence;

    @Before
    public void createProfileAndPersistence(){
        profile = new Profile("TestUser", "TestPassword");
        EventHandler.addEvent(profile, "party", LocalDateTime.now(), LocalDateTime.now(), "Event", 0, AbstractEvent.Frequencies.DAILY);
        EventHandler.addMonitoredEvent(profile, "monitor", LocalDateTime.now(), LocalDateTime.now(), "MonitoredEvent", false, 0, AbstractEvent.Frequencies.NOT_RECURRING);

        PersistenceFactory factory = new PersistenceFactory();

        persistence = factory.getPersistent(PersistenceFactory.persistenceType.JsonFile);
    }

    @Test
    public void testProfileSave(){
        boolean retVal = persistence.save(profile);

        assertEquals(retVal, true);
    }

    @Test
    public void testProfileLoad(){
        testProfileSave();

        Profile p = persistence.load(profile.getUsername());

        assertEquals(p.getUsername(), profile.getUsername());
        assertEquals(p.getHashedPassword(), profile.getHashedPassword());
        assertEquals(p.getNextEventId(), profile.getNextEventId());

        for(int i = 0; i < p.getEvents().size(); i++){
            assertEquals(true, p.getEvents().get(i).getEventId().equals(profile.getEvents().get(i).getEventId()));
            assertEquals(true, p.getEvents().get(i).getEventName().equals(profile.getEvents().get(i).getEventName()));
            assertEquals(true, p.getEvents().get(i).getStartDateTime().equals(profile.getEvents().get(i).getStartDateTime()));
            assertEquals(true, p.getEvents().get(i).getEndDateTime().equals(profile.getEvents().get(i).getEndDateTime()));
            assertEquals(true, p.getEvents().get(i).getFrequency().equals(profile.getEvents().get(i).getFrequency()));
        }
    }

    @Test
    public void testProfileLoadInvalidUsername(){
        Profile p = persistence.load("I DO NOT EXIST");

        assertEquals(p, null);
    }

    @Test
    public void testDeleteProfile(){
        testProfileSave();

        boolean sucess = persistence.delete(profile.getUsername());

        assertEquals(sucess, true);
    }

    @After
    public void finish(){
        testDeleteProfile();
    }
}
