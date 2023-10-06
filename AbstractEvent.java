import java.time.LocalTime;
import java.util.Date;

public  class AbstractEvent 
{
    
    public enum Frequencies {NOT_RECURRING, DAILY, MONTHLY, YEARLY}

    private String eventId;
    private String eventName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Date startDate;
    private Date endDate;
    private String eventType;
    private boolean isComplete;
    private int severityLevel;
    private Frequencies frequency;
    
    public AbstractEvent(String eventId, String eventName, LocalTime startTime, LocalTime endTime, Date startDate,
            Date endDate, String eventType, boolean isComplete, int severityLevel,
            AbstractEvent.Frequencies frequency) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
        this.isComplete = isComplete;
        this.severityLevel = severityLevel;
        this.frequency = frequency;
    }

    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTIme) {
        this.endTime = endTIme;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public boolean isComplete() {
        return isComplete;
    }
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
    public int getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    public Frequencies getFrequency() {
        return frequency;
    }
    public void setFrequency(Frequencies frequency) {
        this.frequency = frequency;
    }

}