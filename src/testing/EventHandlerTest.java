package testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.util.Pair;
import main.*;

public class EventHandlerTest {

    private Profile p;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String type;
    private int severity;
    private AbstractEvent.Frequencies frequency;

    @Before
    public void createProfile(){
        p = new Profile("test", "test");
        name = "testEvent";
        startDateTime = LocalDateTime.now();
        endDateTime = LocalDateTime.now().plusMinutes(10);
        type = "test";
        severity = 0;
        frequency = AbstractEvent.Frequencies.NOT_RECURRING;

        EventHandler.addEvent(p, name, startDateTime, endDateTime, type, severity, frequency);
    }

    @After
    public void cleanUp() {
        p = null;
    }

    @Test
    public void addEventValidTest() {
        int prevSize = p.getEvents().size();
        Returns result = EventHandler.addEvent(p, name, startDateTime, endDateTime, type, severity, frequency);
        int curSize = p.getEvents().size();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertEquals(curSize, prevSize + 1);
    }
    
    @Test
    public void addEventInvalidName() {
        int prevSize = p.getEvents().size();
        Returns result = EventHandler.addEvent(p, "", startDateTime, endDateTime, type, severity, frequency);
        int curSize = p.getEvents().size();

        Assert.assertEquals(result.getReturnCode(), -1);
        Assert.assertEquals(prevSize, curSize);
    }

    @Test
    public void addEventInvalidType() {
        int prevSize = p.getEvents().size();
        Returns result = EventHandler.addEvent(p, name, startDateTime, endDateTime, "", severity, frequency);
        int curSize = p.getEvents().size();

        Assert.assertEquals(result.getReturnCode(), -2);
        Assert.assertEquals(prevSize, curSize);
    }

    @Test
    public void addEventInvalidSeverityTest() {
        int prevSize = p.getEvents().size();
        Returns result = EventHandler.addEvent(p, name, startDateTime, endDateTime, type, 10, frequency);
        int curSize = p.getEvents().size();

        Assert.assertEquals(result.getReturnCode(), -3);
        Assert.assertEquals(prevSize, curSize);
    }

    @Test
    public void addEventInvalidEndDateTimeTest() {
        int prevSize = p.getEvents().size();
        Returns result = EventHandler.addEvent(p, name, startDateTime, endDateTime.minusMinutes(15), type, severity, frequency);
        int curSize = p.getEvents().size();

        Assert.assertEquals(result.getReturnCode(), -6);
        Assert.assertEquals(prevSize, curSize);
    }

    @Test
    public void editEventNameValidTest() {
        String prevName = p.getEvents().get(0).getEventName();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventName(p, e, "TESTEVENT");
        String curName = p.getEvents().get(0).getEventName();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevName, curName);
    }

    @Test
    public void editEventNameInvalidEventTest() {
        String prevName = p.getEvents().get(0).getEventName();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventName(p, e, "TESTEVENT");
        String curName = p.getEvents().get(0).getEventName();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevName, curName);
    }

    @Test
    public void editEventNameInvalidNameTest() {
        String prevName = p.getEvents().get(0).getEventName();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventName(p, e, "");
        String curName = p.getEvents().get(0).getEventName();

        Assert.assertEquals(result.getReturnCode(), -1);
        Assert.assertEquals(prevName, curName);
    }

    @Test
    public void editEventStartDateTimeValidTest() {
        LocalDateTime prevStartDateTime = p.getEvents().get(0).getStartDateTime();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventStartDateTime(p, e, LocalDateTime.now().plusMinutes(5));
        LocalDateTime curStartDateTime = p.getEvents().get(0).getStartDateTime();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevStartDateTime, curStartDateTime);
    }

    @Test
    public void editEventStartDateTimeInvalidEventTest() {
        LocalDateTime prevStartDateTime = p.getEvents().get(0).getStartDateTime();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventStartDateTime(p, e, LocalDateTime.now());
        LocalDateTime curStartDateTime = p.getEvents().get(0).getStartDateTime();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevStartDateTime, curStartDateTime);
    }

    @Test
    public void editEventEndDateTimeValidTest() {
        LocalDateTime prevEndDateTime = p.getEvents().get(0).getEndDateTime();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventEndDateTime(p, e, LocalDateTime.now());
        LocalDateTime curEndDateTime = p.getEvents().get(0).getEndDateTime();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevEndDateTime, curEndDateTime);
    }

    @Test
    public void editEventEndDateTimeInvalidEventTest() {
        LocalDateTime prevEndDateTime = p.getEvents().get(0).getEndDateTime();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventEndDateTime(p, e, LocalDateTime.now());
        LocalDateTime curEndDateTime = p.getEvents().get(0).getEndDateTime();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevEndDateTime, curEndDateTime);
    }

    
    @Test
    public void editEventTypeValidTest() {
        String prevType = p.getEvents().get(0).getEventType();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventType(p, e, "TEST");
        String curType = p.getEvents().get(0).getEventType();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevType, curType);
    }

    @Test
    public void editEventTypeInvalidEventTest() {
        String prevType = p.getEvents().get(0).getEventType();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventType(p, e, "TEST");
        String curType = p.getEvents().get(0).getEventType();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevType, curType);
    }

    @Test
    public void editEventTypeInvalidTypeTest() {
        String prevType = p.getEvents().get(0).getEventType();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventType(p, e, "");
        String curType = p.getEvents().get(0).getEventType();

        Assert.assertEquals(result.getReturnCode(), -2);
        Assert.assertEquals(prevType, curType);
    }

    @Test
    public void editEventSeverityValidTest() {
        int prevSeverity = p.getEvents().get(0).getSeverityLevel();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventSeverity(p, e, 5);
        int curSeverity = p.getEvents().get(0).getSeverityLevel();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevSeverity, curSeverity);
    }

    @Test
    public void editEventSeverityInvalidEventTest() {
        int prevSeverity = p.getEvents().get(0).getSeverityLevel();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventSeverity(p, e, 5);
        int curSeverity = p.getEvents().get(0).getSeverityLevel();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevSeverity, curSeverity);
    }

    @Test
    public void editEventSeverityInvalidSeverityTest() {
        int prevSeverity = p.getEvents().get(0).getSeverityLevel();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventSeverity(p, e, 10);
        int curSeverity = p.getEvents().get(0).getSeverityLevel();

        Assert.assertEquals(result.getReturnCode(), -3);
        Assert.assertEquals(prevSeverity, curSeverity);
    }

    @Test
    public void editEventFrequencyValidTest() {
        AbstractEvent.Frequencies prevFrequency = p.getEvents().get(0).getFrequency();
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.editEventFrequency(p, e, AbstractEvent.Frequencies.DAILY);
        AbstractEvent.Frequencies curFrequency = p.getEvents().get(0).getFrequency();

        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertNotEquals(prevFrequency, curFrequency);
    }

    @Test
    public void editEventFrequencyInvalidEventTest() {
        AbstractEvent.Frequencies prevFrequency = p.getEvents().get(0).getFrequency();
        AbstractEvent e = null;
        Returns result = EventHandler.editEventFrequency(p, e, AbstractEvent.Frequencies.DAILY);
        AbstractEvent.Frequencies curFrequency = p.getEvents().get(0).getFrequency();

        Assert.assertEquals(result.getReturnCode(), -4);
        Assert.assertEquals(prevFrequency, curFrequency);
    }

    @Test
    public void removeEventValidTest() {
        AbstractEvent e = p.getEvents().get(0);
        Returns result = EventHandler.removeEvent(p, e);
        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void removeEventInvalidEventTest() {
        AbstractEvent e = null;
        Returns result = EventHandler.removeEvent(p, e);
        Assert.assertEquals(result.getReturnCode(), -4);
    }

    @Test
    public void addMonitoredEventValidTest() {
        Returns result = EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);

        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void addMonitoredEventInvalidNameTest() {
        Returns result = EventHandler.addMonitoredEvent(p, "", startDateTime, endDateTime, type, false, severity, frequency);

        Assert.assertEquals(result.getReturnCode(), -1);
    }

    @Test
    public void addMonitoredEventInvalidTypeTest() {
        Returns result = EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, "", false, severity, frequency);

        Assert.assertEquals(result.getReturnCode(), -2);
    }

    @Test
    public void addMonitoredEventInvalidSeverityTest() {
        Returns result = EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, 10, frequency);

        Assert.assertEquals(result.getReturnCode(), -3);
    }

    @Test
    public void addStepValidTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 0, false);
        Returns result = EventHandler.addStep(m, s);

        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void searchForEventSeverityValidTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventSeverity(p, severity);
        Assert.assertEquals(result.getValue().getReturnCode(), 0);
    }

    @Test
    public void searchForEventSeverityInvalidSeverityTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventSeverity(p, -1);
        Assert.assertEquals(result.getValue().getReturnCode(), -3);
    }

    @Test
    public void searchForEventSeverityNoEventFoundTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventSeverity(p, 5);
        Assert.assertEquals( result.getValue().getReturnCode(), -4);
    }
    
    @Test
    public void searchForEventDateTimeEventFoundTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventDateTime(p, startDateTime);
        Assert.assertEquals((AbstractEvent) result.getKey(), p.getEvents().get(0));
    }

    @Test
    public void searchForEventDateTimeEventNotFoundTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventDateTime(p, LocalDateTime.now().plusMinutes(100));
        Assert.assertEquals(result.getKey(), null);
        Assert.assertEquals(result.getValue().getReturnCode(), -4);
    }

    @Test
    public void searchForEventTypeValidTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventType(p, type);
        Assert.assertEquals (result.getValue().getReturnCode(), 0);
    }

    @Test
    public void searchForEventTypeInvalidTypeTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventType(p, "");
        Assert.assertEquals(result.getValue().getReturnCode(), -2);
    }

    @Test
    public void searchForEventTypeNoEventFoundTest() {
        Pair<AbstractEvent, Returns> result = EventHandler.searchForEventType(p, "test2");
        Assert.assertEquals(result.getValue().getReturnCode(), -4);
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
        Returns result = EventHandler.displayEventSummary(p.getEvents());
        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void displayEventSummaryNoEventsFoundTest() {
        Returns result = EventHandler.displayEventSummary(new ArrayList<AbstractEvent>());
        Assert.assertEquals(result.getReturnCode(), -5);
    }

    @Test
    public void viewPastEventsValidTest() {
        p.getEvents().get(0).setComplete(true);
        Pair<ArrayList<AbstractEvent>, Returns> result = EventHandler.viewPastEvents(p, startDateTime, endDateTime);
        Assert.assertEquals(result.getValue().getReturnCode(), 0);
    }

    @Test
    public void viewPastEventsNoEventsFoundTest() {
        LocalDateTime testDate = LocalDateTime.now().plusMinutes(100);
        Pair<ArrayList<AbstractEvent>, Returns> result = EventHandler.viewPastEvents(p, testDate, testDate);
        Assert.assertEquals(result.getValue().getReturnCode(), -5);
    }

    @Test
    public void checkCompletionValidIncompleteTest() {
        Returns result = EventHandler.checkCompletion(p);
        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertEquals(p.getEvents().get(0).isComplete(), false);
    }

    @Test
    public void checkCompetionValidCompleteTest() {
        EventHandler.editEventStartDateTime(p, p.getEvents().get(0), LocalDateTime.now().minusMinutes(10));
        EventHandler.editEventEndDateTime(p, p.getEvents().get(0), LocalDateTime.now().minusMinutes(1));

        Returns result = EventHandler.checkCompletion(p);
        Assert.assertEquals(result.getReturnCode(), 0);
        Assert.assertEquals(p.getEvents().get(0).isComplete(), true);
    }

    @Test
    public void checkCompletionInvalidTest() {
        EventHandler.removeEvent(p, p.getEvents().get(0));
        Returns result = EventHandler.checkCompletion(p);
        Assert.assertEquals(result.getReturnCode(), -5);
    }

    @Test
    public void setStepCompleteValidTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.setStepComplete(p, m, 1);
        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void setStepCompleteInvalidEventTest() {
        Returns result = EventHandler.setStepComplete(p, null, 1);
        Assert.assertEquals(result.getReturnCode(), -4);
    }

    @Test
    public void setStepCompleteInvalidStepNumTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.setStepComplete(p, m, 2);
        Assert.assertEquals(result.getReturnCode(), -4);
    }

    @Test
    public void editStepNameValidTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.editStepName(m, 1, "test2");

        Assert.assertEquals(result.getReturnCode(), 0);
    }

    @Test
    public void editStepNameInvalidNameTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.editStepName(m, 1, "");

        Assert.assertEquals(result.getReturnCode(), -1);
    }

    @Test
    public void editStepNameInvalidStepNumberTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.editStepName(m, 0, "test");

        Assert.assertEquals(result.getReturnCode(), -4);
    }

    @Test
    public void editStepNameInvalidEventTest() {
        EventHandler.addMonitoredEvent(p, name, startDateTime, endDateTime, type, false, severity, frequency);
        MonitoredEvent m = (MonitoredEvent) p.getEvents().get(1);
        Step s = new Step("test", 1, false);
        EventHandler.addStep(m, s);
        Returns result = EventHandler.editStepName(null, 1, "test");

        Assert.assertEquals(result.getReturnCode(), -4);
    }
}
