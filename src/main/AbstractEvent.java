package main;
import java.time.LocalTime;
import java.util.Date;

public abstract class AbstractEvent 
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
    
    public AbstractEvent(AbstractEventBuilder b) {
        this.eventId = b.eventId;
        this.eventName = b.eventName;
        this.startTime = b.startTime;
        this.endTime = b.endTime;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.eventType = b.eventType;
        this.isComplete = b.isComplete;
        this.severityLevel = b.severityLevel;
        this.frequency = b.frequency;
    }


    public static abstract class AbstractEventBuilder{
        private String eventId;
        private String eventName;
        private LocalTime startTime;
        private LocalTime endTime;
        private Date startDate;
        private Date endDate;
        private String eventType;
        private boolean isComplete = false;
        private int severityLevel;
        private Frequencies frequency;

        public AbstractEventBuilder eventId(String eID){
            this.eventId = eID;
            return this;
        }

        public AbstractEventBuilder eventName(String eName){
            this.eventName = eName;
            return this;
        }

        public AbstractEventBuilder startTime(LocalTime time){
            this.startTime = time;
            return this;
        }

        public AbstractEventBuilder endTime(LocalTime time){
            this.endTime = time;
            return this;
        }

        public AbstractEventBuilder startDate(Date date){
            this.startDate = date;
            return this;
        }

        public AbstractEventBuilder endDate(Date date){
            this.endDate = date;
            return this;
        }

        public AbstractEventBuilder eventType(String eType){
            this.eventType = eType;
            return this;
        }

        public AbstractEventBuilder severityLevel(int severityLevel){
            this.severityLevel = severityLevel;
            return this;
        }

        public AbstractEventBuilder frequency(Frequencies frequency){
            this.frequency = frequency;
            return this;
        }

        public abstract AbstractEvent build();
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