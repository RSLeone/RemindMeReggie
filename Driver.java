import java.io.File;
import java.time.LocalTime;

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

        Profile p = new Profile("Andrew", "e12141213131");
        PersistenceFactory perf = new PersistenceFactory();
        LocalFile lf = (LocalFile) perf.getPersistent(PersistenceFactory.persistenceType.LocalFile);
        lf.save(p);
    }
}
