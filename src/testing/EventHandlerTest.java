package testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import javafx.util.Pair;
import main.*;

public class EventHandlerTest {

    private Profile p;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date startDate;
    private Date endDate;
    private String type;
    private int severity;
    private AbstractEvent.Frequencies frequency;

    @Before
    public void createProfile(){
        p = new Profile("test", "test");
        name = "testEvent";
        startTime = LocalTime.now();
        endTime = LocalTime.now().plusMinutes(10);
        startDate = new Date();
        endDate = new Date();
        type = "test";
        severity = 0;
        frequency = AbstractEvent.Frequencies.DAILY;

        EventHandler.addEvent(p, name, startTime, endTime, startDate, endDate, type, severity, frequency);
    }

    @After
    public void cleanUp() {
        p = null;
    }

    @Test
    public void addEventValidTest() {
        int result = EventHandler.addEvent(p, name, startTime, endTime, startDate, endDate, type, severity, frequency);

        Assert.assertEquals(result, 0);
    }
    
    @Test
    public void addEventInvalidName() {
        int result = EventHandler.addEvent(p, "", startTime, endTime, startDate, endDate, type, severity, frequency);

        Assert.assertEquals(result, -1);
    }

    @Test
    public void addEventInvalidType() {
        int result = EventHandler.addEvent(p, name, startTime, endTime, startDate, endDate, "", severity, frequency);

        Assert.assertEquals(result, -2);
    }

    @Test
    public void addEventInvalidSeverityTest() {
        int result = EventHandler.addEvent(p, name, startTime, endTime, startDate, endDate, type, 10, frequency);

        Assert.assertEquals(result, -3);
    }

    @Test
    public void editEventNameValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventName(p, e, "TESTEVENT");
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventNameInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventName(p, e, "TESTEVENT");
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventNameInvalidNameTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventName(p, e, "");
        Assert.assertEquals(result, -1);
    }

    @Test
    public void editEventStartTimeValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventStartTime(p, e, startTime);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventStartTimeInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventStartTime(p, e, startTime);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventEndTimeValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventEndTime(p, e, endTime);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventEndTimeInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventEndTime(p, e, endTime);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventStartDateValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventStartDate(p, e, startDate);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventStartDateInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventStartDate(p, e, startDate);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventEndDateValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventEndDate(p, e, endDate);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventEndDateInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventEndDate(p, e, endDate);
        Assert.assertEquals(result, -4);
    }    

    @Test
    public void editEventTypeValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventType(p, e, "TEST");
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventTypeInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventType(p, e, "TEST");
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventTypeInvalidTypeTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventType(p, e, "");
        Assert.assertEquals(result, -2);
    }

    @Test
    public void editEventSeverityValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventSeverity(p, e, 5);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventSeverityInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventSeverity(p, e, 5);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void editEventSeverityInvalidSeverityTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventSeverity(p, e, 10);
        Assert.assertEquals(result, -3);
    }

    @Test
    public void editEventFrequencyValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.editEventFrequency(p, e, frequency);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void editEventFrequencyInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.editEventFrequency(p, e, frequency);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void removeEventValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        int result = EventHandler.removeEvent(p, e);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void removeEventInvalidEventTest() {
        AbstractEvent e = null;
        int result = EventHandler.removeEvent(p, e);
        Assert.assertEquals(result, -4);
    }

    @Test
    public void addMonitoredEventValidTest() {
        int result = EventHandler.addMonitoredEvent(p, name, startTime, endTime, startDate, endDate, type, false, severity, frequency);

        Assert.assertEquals(result, 0);
    }

    @Test
    public void addMonitoredEventInvalidNameTest() {
        int result = EventHandler.addMonitoredEvent(p, "", startTime, endTime, startDate, endDate, type, false, severity, frequency);

        Assert.assertEquals(result, -1);
    }

    @Test
    public void addMonitoredEventInvalidTypeTest() {
        int result = EventHandler.addMonitoredEvent(p, name, startTime, endTime, startDate, endDate, "", false, severity, frequency);

        Assert.assertEquals(result, -2);
    }

    @Test
    public void addMonitoredEventInvalidSeverityTest() {
        int result = EventHandler.addMonitoredEvent(p, name, startTime, endTime, startDate, endDate, type, false, 10, frequency);

        Assert.assertEquals(result, -3);
    }

    @Test
    public void addStepValidTest() {
        EventHandler.addMonitoredEvent(p, name, startTime, endTime, startDate, endDate, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 0, false);
        int result = EventHandler.addStep(m, s);

        Assert.assertEquals(result, 0);
    }

    @Test
    public void searchForEventSeverityValidTest() {
        Pair<AbstractEvent, Integer> result = EventHandler.searchForEventSeverity(p, severity);
        Assert.assertEquals((long) result.getValue(), 0);
    }

    @Test
    public void searchForEventSeverityInvalidSeverityTest() {
        Pair<AbstractEvent, Integer> result = EventHandler.searchForEventSeverity(p, -1);
        Assert.assertEquals((long) result.getValue(), -3);
    }
    
    @Test
    public void searchForEventDateEventFoundTest() {
        AbstractEvent result = EventHandler.searchForEventDate(p, startDate);
        Assert.assertEquals(result, p.getEvents().get(0));
    }

    @Test
    public void searchForEventDateEventNotFoundTest() {
        AbstractEvent result = EventHandler.searchForEventDate(p, new Date(100));
        Assert.assertEquals(result, null);
    }

    @Test
    public void searchForEventTypeValidTest() {
        Pair<AbstractEvent, Integer> result = EventHandler.searchForEventType(p, type);
        Assert.assertEquals((long) result.getValue(), 0);
    }

    @Test
    public void searchForEventTypeInvalidTypeTest() {
        Pair<AbstractEvent, Integer> result = EventHandler.searchForEventType(p, "");
        Assert.assertEquals((long) result.getValue(), -2);
    }

    @Test
    public void sortBySeverityFrequencyFoundTest() {
        ArrayList<AbstractEvent> result = EventHandler.sortBySeverity(p, frequency);
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void sortBySeverityFrequencyNotFoundTest() {
        ArrayList<AbstractEvent> result = EventHandler.sortBySeverity(p, AbstractEvent.Frequencies.MONTHLY);
        Assert.assertEquals(result.size(), 0);
    }

    @Test
    public void generateNextStepValidTest() {
        Step result = EventHandler.generateNextStep(p);
        Assert.assertEquals(result, null);
    }

    @Test
    public void displayEventSummaryValidTest() {
        int result = EventHandler.displayEventSummary(p.getEvents());
        Assert.assertEquals(result, 0);
    }

    @Test
    public void displayEventSummaryNoEventsFoundTest() {
        int result = EventHandler.displayEventSummary(new ArrayList<AbstractEvent>());
        Assert.assertEquals(result, -5);
    }

    @Test
    public void viewPastEventsValidTest() {
        Pair<ArrayList<AbstractEvent>, Integer> result = EventHandler.viewPastEvents(p, startDate, endDate);
        Assert.assertEquals((long) result.getValue(), 0);
    }

    @Test
    public void viewPastEventsNoEventsFoundTest() {
        Date testDate = new Date(1000);
        Pair<ArrayList<AbstractEvent>, Integer> result = EventHandler.viewPastEvents(p, testDate, testDate);
        Assert.assertEquals((long) result.getValue(), -5);
    }
}
