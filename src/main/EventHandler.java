package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalDateTime;
import javafx.util.Pair;

public class EventHandler {

    public static Returns addEvent(Profile p, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, 
                    String eventType, int severityLevel, AbstractEvent.Frequencies frequency) {

        if (eventName.length() <= 0 || eventName.length() > 50) {
            return Returns.INVALID_EVENT_NAME;
        }
        if (eventType.length() <= 0 | eventType.length() > 50){
            return Returns.INVALID_EVENT_TYPE;
        }
        if (severityLevel < 0 || severityLevel > 5) {
            return Returns.INVALID_SEVERITY_LEVEL;
        }
        if (endDateTime.compareTo(startDateTime) < 0) {
            return Returns.INVALID_END_DATE_TIME;
        }
        
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new Event.EventBuilder().eventId(Integer.toString(newID)).eventName(eventName).startDateTime(startDateTime)
                        .endDateTime(endDateTime).eventType(eventType).severityLevel(severityLevel)
                        .frequency(frequency).build();

        events.add(newEvent);
        return Returns.SUCCESS;
    }

    public static Returns editEventName(Profile p, AbstractEvent e, String name) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }
        if (name.length() <= 0 || name.length() > 50) {
            return Returns.INVALID_EVENT_NAME;
        }


        e.setEventName(name);
        return Returns.SUCCESS;
    }

    public static Returns editEventStartDateTime(Profile p, AbstractEvent e, LocalDateTime startDateTime) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setStartTime(startDateTime);
        return Returns.SUCCESS;
    }

    public static Returns editEventEndDateTime(Profile p, AbstractEvent e, LocalDateTime endDateTime) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setEndTime(endDateTime);
        return Returns.SUCCESS;
    }

    public static Returns editEventType(Profile p, AbstractEvent e, String type) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }
        if (type.length() <= 0 | type.length() > 50){
            return Returns.INVALID_EVENT_TYPE;
        }

        e.setEventType(type);
        return Returns.SUCCESS;
    }

    public static Returns editEventSeverity(Profile p, AbstractEvent e, int severity) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }
        if (severity < 0 || severity > 5) {
            return Returns.INVALID_SEVERITY_LEVEL;
        }

        e.setSeverityLevel(severity);
        return Returns.SUCCESS;
    }

    public static Returns editEventFrequency(Profile p, AbstractEvent e, AbstractEvent.Frequencies frequency) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setFrequency(frequency);
        return Returns.SUCCESS;
    }

    public static Returns removeEvent(Profile p, AbstractEvent e) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        events.remove(e);
        return Returns.SUCCESS;
    }

    public static Returns addMonitoredEvent(Profile p, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime,
                    String eventType, Boolean isComplete, int severityLevel, AbstractEvent.Frequencies frequency) {
        
        if (eventName.length() <= 0 || eventName.length() > 50) {
            return Returns.INVALID_EVENT_NAME;
        }
        if (eventType.length() <= 0 | eventType.length() > 50){
            return Returns.INVALID_EVENT_TYPE;
        }
        if (severityLevel < 0 || severityLevel > 5) {
            return Returns.INVALID_SEVERITY_LEVEL;
        }
        if (endDateTime.compareTo(startDateTime) < 0) {
            return Returns.INVALID_END_DATE_TIME;
        }
        
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new MonitoredEvent.MonitoredEventBuilder().eventId(Integer.toString(newID)).eventName(eventName)
                        .startDateTime(startDateTime).endDateTime(endDateTime).eventType(eventType)
                        .severityLevel(severityLevel).frequency(frequency).build();

        events.add(newEvent);
        return Returns.SUCCESS;
    }

    public static Returns addStep(MonitoredEvent m, Step s) {
        ArrayList<Step> steps = m.getSteps();
        int newStepNum = m.getNextStepNumber();
        m.setNextStepNumber(newStepNum + 1);
        steps.add(s);
        return Returns.SUCCESS;
    }

    public static Pair<AbstractEvent, Returns> searchForEventSeverity(Profile p, int level) {

        if (level < 0 || level > 5) {
            return new Pair<AbstractEvent,Returns>(null, Returns.INVALID_SEVERITY_LEVEL);
        }
        
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getSeverityLevel() == level){
                return new Pair<AbstractEvent, Returns> (events.get(i),Returns.SUCCESS);
            }
        }
        return new Pair<AbstractEvent, Returns> (null, Returns.SUCCESS);
    }

    public static AbstractEvent searchForEventDateTime(Profile p, LocalDateTime dateTime) {
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getStartDateTime() == dateTime){
                return events.get(i);
            }
        }
        return null;
    }
    
    public static Pair<AbstractEvent, Returns> searchForEventType(Profile p, String type) {

        if (type.length() <= 0 | type.length() > 50){
            return new Pair<AbstractEvent,Returns>(null, Returns.INVALID_EVENT_TYPE);
        }

        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getEventType() == type){
                return new Pair<AbstractEvent, Returns> (events.get(i), Returns.SUCCESS);
            }
        }
        return new Pair<AbstractEvent, Returns> (null, Returns.SUCCESS);
    }

    public static ArrayList<AbstractEvent> sortBySeverity(Profile p, AbstractEvent.Frequencies frequency) {
        ArrayList<AbstractEvent> events = p.getEvents();
        ArrayList<AbstractEvent> sortedList = new ArrayList<AbstractEvent>();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).getFrequency() == frequency) {
                sortedList.add(events.get(i));  
            }
        }

        Comparator<AbstractEvent> comparator = (event1, event2) -> event1.getSeverityLevel() - event2.getSeverityLevel();
        sortedList.sort(comparator);
        return sortedList;
    }

    public static Step generateNextStep(Profile p) {
        ArrayList<AbstractEvent> events = p.getEvents();
        AbstractEvent curNextEvent = null;
        AbstractEvent curEvent;
        for(int i = 0; i < events.size(); i++){
            curEvent = events.get(i);
            if (curEvent instanceof MonitoredEvent){
                if (curEvent.getEndDateTime().compareTo(curNextEvent.getEndDateTime()) < 0){ //curEvent's end date is before curNextEvent's end date
                    curNextEvent = curEvent;
                }
            }
        }
        if (curNextEvent != null) {
            ArrayList<Step> steps = ((MonitoredEvent)curNextEvent).getSteps();
            return steps.get(0);
        }
        return null;
    }
    
    public static Returns displayEventSummary(ArrayList<AbstractEvent> eventsList) { //Display every event's attributes in eventsList
        if (eventsList.size() == 0) {
            System.out.println("There are no events to display.");
            return Returns.NO_EVENTS_TO_DISPLAY;
        }
        else {
            System.out.println("Displaying Event Summary:");
            for (int i = 0; i < eventsList.size(); i++) {
                AbstractEvent event = eventsList.get(i);
                System.out.println("Name: " + event.getEventName());
                System.out.println("Date & Time: " + event.getStartDateTime() + " - " + event.getEndDateTime());
                System.out.println("Type: " + event.getEventType());
                System.out.println("Severity: " + event.getSeverityLevel());
                System.out.println("Frequency: " + event.getFrequency());
                System.out.println();
            }
        }
        return Returns.SUCCESS;
    }

    public static Pair<ArrayList<AbstractEvent>, Returns> viewPastEvents(Profile p, LocalDateTime startRange, LocalDateTime endRange) {
        ArrayList<AbstractEvent> events = p.getEvents();
        LocalDateTime start;
        LocalDateTime end;
        ArrayList<AbstractEvent> pastEvents = new ArrayList<AbstractEvent>();

        for(int i = 0; i < events.size(); i++){
            start = events.get(i).getStartDateTime();
            end = events.get(i).getEndDateTime();
            if ((start.compareTo(startRange) >= 0) && end.compareTo(endRange) <= 0){
                pastEvents.add(events.get(i));
            }
        }
        
        if (pastEvents.size() == 0) {
            return new Pair<ArrayList<AbstractEvent>,Returns>(null, Returns.NO_EVENTS_TO_DISPLAY);
        }
        return new Pair<ArrayList<AbstractEvent>,Returns>(pastEvents, Returns.SUCCESS);
    }
}