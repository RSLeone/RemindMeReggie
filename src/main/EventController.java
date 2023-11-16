package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class EventController {

    public boolean addEvent(Profile p, String eventName, LocalTime startTime, LocalTime endTime, Date startDate, Date endDate, String eventType, int severityLevel, AbstractEvent.Frequencies frequency) {
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new Event.EventBuilder().eventId(Integer.toString(newID)).eventName(eventName).startTime(startTime).endTime(endTime).startDate(startDate).endDate(endDate).eventType(eventType).severityLevel(severityLevel).frequency(frequency).build();

        events.add(newEvent);
        return true;
    }

    public boolean editEvent(Profile p, AbstractEvent e) {
        ArrayList<AbstractEvent> events = p.getEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i) == e) {
                AbstractEvent event = events.get(i);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please select the attribute to edit: ");
                System.out.println("1: Name, 2: Start Time, 3: End Time, 4: Start Date, 5: End Date, 6: Severity, 7: Frequency");
                String inputStr = scanner.nextLine();
                int input1 = Integer.valueOf(inputStr);
                System.out.println("Please input what you want to change it to: ");
                inputStr = scanner.nextLine();
                DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                if (input1 == 1){
                    event.setEventName(inputStr);
                }
                else if (input1 == 2){
                    event.setStartTime(LocalTime.parse(inputStr));
                }
                else if (input1 == 3){
                    event.setEndTime(LocalTime.parse(inputStr));
                }
                else if (input1 == 4){
                    try {
                        event.setStartDate(format.parse(inputStr));
                    } catch (ParseException e1) {
                        System.out.println("Incorrect date format - MM/DD/YYYY");
                        e1.printStackTrace();
                    }
                }
                else if (input1 == 5){
                    try {
                        event.setEndDate(format.parse(inputStr));
                    } catch (ParseException e1) {
                        System.out.println("Incorrect date format - MM/DD/YYYY");
                        e1.printStackTrace();
                    }
                }
                else if (input1 == 6){
                    event.setSeverityLevel(Integer.parseInt(inputStr));
                }
                else if (input1 == 7){
                    event.setFrequency(AbstractEvent.Frequencies.valueOf(inputStr));
                }
                else {
                    scanner.close();
                    return false;
                }
                scanner.close();
                return true;
            }
        }
        return false;
    }

    public boolean removeEvent(Profile p, int eventId){
        ArrayList<AbstractEvent> events = p.getEvents();
        events.remove(eventId);
        return true;
    }

    public boolean addMonitoredEvent(Profile p, String eventName, LocalTime startTime, LocalTime endTime, Date startDate, Date endDate, String eventType, Boolean isComplete, int severityLevel, AbstractEvent.Frequencies frequency) {
        ArrayList<AbstractEvent> events = p.getEvents();
        int newID = p.getNextEventId();
        p.setNextEventId(newID + 1);
        AbstractEvent newEvent = new MonitoredEvent.MonitoredEventBuilder().eventId(Integer.toString(newID)).eventName(eventName).startTime(startTime).endTime(endTime).startDate(startDate).endDate(endDate).eventType(eventType).severityLevel(severityLevel).frequency(frequency).build();

        events.add(newEvent);
        return true;
    }

    public boolean addStep(MonitoredEvent m, Step s) {
        ArrayList<Step> steps = m.getSteps();
        int newStepNum = m.getNextStepNumber();
        m.setNextStepNumber(newStepNum + 1);
        steps.add(s);
        return true;
    }

    public AbstractEvent searchForEventSeverity(Profile p, int level){
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getSeverityLevel() == level){
                return events.get(i);
            }
        }
        return null;
    }

    public AbstractEvent searchForEventDate(Profile p, Date date) {
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getStartDate() == date){
                return events.get(i);
            }
        }
        return null;
    }
    
    public AbstractEvent searchforEventType(Profile p, String type) {
        ArrayList<AbstractEvent> events = p.getEvents();
        for(int i = 0; i < events.size(); i++){
            if (events.get(i).getEventType() == type){
                return events.get(i);
            }
        }
        return null;
    }

    public ArrayList<AbstractEvent> sortBySeverity(Profile p) {
        ArrayList<AbstractEvent> events = p.getEvents();
        ArrayList<AbstractEvent> sortedList = (ArrayList<AbstractEvent>)events.clone();
        Comparator<AbstractEvent> comparator = (event1, event2) -> event1.getSeverityLevel() - event2.getSeverityLevel();
        sortedList.sort(comparator);
        return sortedList;
    }

    public Step generateNextStep(Profile p) {
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
    
    public void displayEventSummary(Profile p) { //Display every event's attributes
        ArrayList<AbstractEvent> events = p.getEvents();
        System.out.println("Displaying Event Summary:");
        for (int i = 0; i < events.size(); i++) {
            AbstractEvent event = events.get(i);
            System.out.println("Name: " + event.getEventName());
            System.out.println("Duration: " + event.getStartTime() + " - " + event.getEndTime());
            System.out.println("Dates: " + event.getStartDate() + " - " + event.getEndDate());
            System.out.println("Type: " + event.getEventType());
            System.out.println("Severity: " + event.getSeverityLevel());
            System.out.println("Frequency: " + event.getFrequency());
            System.out.println();
        }
    }
}