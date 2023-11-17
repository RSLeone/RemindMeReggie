package testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.Date;
import main.*;

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
    public void addEventTest() {
        String name = "testEvent";
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = LocalTime.now().plusMinutes(10);
        Date startDate = new Date();
        Date endDate = new Date();
        String type = "test";
        int severity = 0;
        AbstractEvent.Frequencies frequency = AbstractEvent.Frequencies.DAILY;

        int result = EventHandler.addEvent(p, name, startTime, endTime, startDate, endDate, type, severity, frequency);

        Assert.assertEquals(result, 0);
    }
}
