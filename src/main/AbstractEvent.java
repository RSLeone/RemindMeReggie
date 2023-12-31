package main;
import java.time.LocalDateTime;

public abstract class AbstractEvent 
{
    
    public enum Frequencies {NOT_RECURRING, DAILY, WEEKLY, MONTHLY, YEARLY}

    private String eventId;
    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String eventType;
    private boolean isComplete;
    private int severityLevel;
    private Frequencies frequency;
    
    public AbstractEvent(AbstractEventBuilder b) {
        this.eventId = b.eventId;
        this.eventName = b.eventName;
        this.startDateTime = b.startDateTime;
        this.endDateTime = b.endDateTime;
        this.eventType = b.eventType;
        this.isComplete = b.isComplete;
        this.severityLevel = b.severityLevel;
        this.frequency = b.frequency;
    }


    public static abstract class AbstractEventBuilder{
        private String eventId;
        private String eventName;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
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

        public AbstractEventBuilder startDateTime(LocalDateTime time){
            this.startDateTime = time;
            return this;
        }

        public AbstractEventBuilder endDateTime(LocalDateTime time){
            this.endDateTime = time;
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
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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