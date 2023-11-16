package testing;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import main.Persistence;
import main.PersistenceFactory;
import main.Profile;
import main.PersistenceFactory.persistenceType;

public class JsonFileTest {
    Profile profile;
    Persistence persistence;

    @Before
    public void createProfileAndPersistence(){
        profile = new Profile("TestUser", "TestPassword");

        PersistenceFactory factory = new PersistenceFactory();

        persistence = factory.getPersistent(persistenceType.JsonFile);
    }

    @Test
    public void testProfileSave(){
        boolean retVal = persistence.save(profile);

        assertEquals(retVal, true);
    }

    @Test
    public void testProfileLoad(){
        Profile p = persistence.load(profile.getUsername());

        assertEquals(p.getUsername(), profile.getUsername());
        assertEquals(p.getHashedPassword(), profile.getHashedPassword());
        assertEquals(p.getNextEventId(), profile.getNextEventId());
        assertEquals(p.getEvents().equals(profile.getEvents()), true);
    }

    @Test
    public void testProfileLoadInvalidUsername(){
        Profile p = persistence.load("I DO NOT EXIST");

        assertEquals(p, null);
    }
}
