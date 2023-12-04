package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.time.LocalTime;
import java.util.Date;
import javafx.util.Pair;

public class EventHandler {

    public static Returns addEvent(Profile p, String eventName, LocalTime startTime, LocalTime endTime, Date startDate, Date endDate, 
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
        
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new Event.EventBuilder().eventId(Integer.toString(newID)).eventName(eventName).startTime(startTime)
                        .endTime(endTime).startDate(startDate).endDate(endDate).eventType(eventType).severityLevel(severityLevel)
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

    public static Returns editEventStartTime(Profile p, AbstractEvent e, LocalTime startTime) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setStartTime(startTime);
        return Returns.SUCCESS;
    }

    public static Returns editEventEndTime(Profile p, AbstractEvent e, LocalTime endTime) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setEndTime(endTime);
        return Returns.SUCCESS;
    }

    public static Returns editEventStartDate(Profile p, AbstractEvent e, Date startDate) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setStartDate(startDate);
        return Returns.SUCCESS;
    }

    public static Returns editEventEndDate(Profile p, AbstractEvent e, Date endDate) {
        ArrayList<AbstractEvent> events = p.getEvents();

        if (! events.contains(e)) {
            return Returns.EVENT_DOES_NOT_EXIST;
        }

        e.setEndDate(endDate);
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

    public static Returns addMonitoredEvent(Profile p, String eventName, LocalTime startTime, LocalTime endTime, Date startDate,
                     Date endDate, String eventType, Boolean isComplete, int severityLevel, AbstractEvent.Frequencies frequency) {
        
        if (eventName.length() <= 0 || eventName.length() > 50) {
            return Returns.INVALID_EVENT_NAME;
        }
        if (eventType.length() <= 0 | eventType.length() > 50){
            return Returns.INVALID_EVENT_TYPE;
        }
        if (severityLevel < 0 || severityLevel > 5) {
            return Returns.INVALID_SEVERITY_LEVEL;
        }
        
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new MonitoredEvent.MonitoredEventBuilder().eventId(Integer.toString(newID)).eventName(eventName)
                        .startTime(startTime).endTime(endTime).startDate(startDate).endDate(endDate).eventType(eventType)
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

    public static AbstractEvent searchForEventDate(Profile p, Date date) {
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getStartDate() == date){
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
                if (curEvent.getEndDate().compareTo(curNextEvent.getEndDate()) < 0){ //curEvent's end date is before curNextEvent's end date
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
                System.out.println("Duration: " + event.getStartTime() + " - " + event.getEndTime());
                System.out.println("Dates: " + event.getStartDate() + " - " + event.getEndDate());
                System.out.println("Type: " + event.getEventType());
                System.out.println("Severity: " + event.getSeverityLevel());
                System.out.println("Frequency: " + event.getFrequency());
                System.out.println();
            }
        }
        return Returns.SUCCESS;
    }

    public static Pair<ArrayList<AbstractEvent>, Returns> viewPastEvents(Profile p, Date startRange, Date endRange) {
        ArrayList<AbstractEvent> events = p.getEvents();
        Date start;
        Date end;
        ArrayList<AbstractEvent> pastEvents = new ArrayList<AbstractEvent>();

        for(int i = 0; i < events.size(); i++){
            start = events.get(i).getStartDate();
            end = events.get(i).getEndDate();
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