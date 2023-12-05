package main;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import main.Event.EventBuilder;

import java.time.Duration;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.transform.recurrence.Frequency;
import net.fortuna.ical4j.util.RandomUidGenerator;









public class Driver
{
    public static void main(String args[])
    {   
        
        // Event b1 = (Event) new Event.EventBuilder().eventId("122313131").eventName("Party").
        // startTime(LocalTime.now()).endTime(LocalTime.now()).startDate(null).endDate(null).eventType("EVENT").severityLevel(24)
        // .build();

        // System.out.println(b1.getEventId());
        // System.out.println(b1.getEventName());
        // System.out.println(b1.getEventType());
        // System.out.println(b1.getEventName());

        // MonitoredEvent b2 = (MonitoredEvent) new MonitoredEvent.MonitoredEventBuilder().eventId("122313131").eventName("Party").
        // startTime(LocalTime.now()).endTime(LocalTime.now()).startDate(null).endDate(null).eventType("EVENT").severityLevel(24)
        // .build();

        // System.out.println(b2.getEventId());
        // System.out.println(b2.getEventName());
        // System.out.println(b2.getEventType());
        // System.out.println(b2.getPercentCompleted());
        // System.out.println(b2.getNextStepNumber());
        // b2.getSteps().add(new Step("HA", 1, false));
        // System.out.println(b2.getSteps().get(0).getStepName());

        // CalanderHandler ch = new CalanderHandler();
        // ch.exportToCalander("",null);
        // ch.importFromCalander("");

        LocalDateTime startDateTime = LocalDateTime.of(2023, 11, 28, 4, 30, 0);

        LocalDateTime endDateTime = LocalDateTime.of(2023, 12, 28, 8, 15, 0);

        Profile p = new Profile("ANdrew", "123456");

        Event e = (Event)(new EventBuilder()).startDateTime(LocalDateTime.of(2023, 11, 28, 8, 0, 0)).endDateTime(
            LocalDateTime.of(2023, 12, 28, 12, 0, 0)).frequency(AbstractEvent.Frequencies.NOT_RECURRING).eventName("Gaming").build();
        
        p.getEvents().add(e);

        CalanderHandler ch = new CalanderHandler();

        ch.exportToCalander("./calanderTestFiles/here.ics", p);


        ch.importFromCalander("./calanderTestFiles/here.ics");

    }
}
