package testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventHandlerTest {

    private Profile p;

    @Before
    public void createProfile(){
        p = new Profile("test", "test");
    }

    @After
    public void cleanUp() {
        p = null;
    }

    @Test
    public void addEventNormalValuesTest() {
        
    }
}
