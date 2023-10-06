import java.time.LocalTime;
import java.util.Date;

public class Event extends AbstractEvent {
    
    public Event(String eventId, String eventName, LocalTime startTime, LocalTime endTime, Date startDate,
            Date endDate, String eventType, boolean isComplete, int severityLevel,
            Event.Frequencies frequency) 
    {
        super(eventId, eventName, startTime, endTime, startDate, endDate, eventType, isComplete, severityLevel, frequency);
    }

}
