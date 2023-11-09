
public class Event extends AbstractEvent {
    
    public static class EventBuilder extends AbstractEventBuilder{
        @Override
        public Event build(){
            return new Event(this);
        }
    }
    
    private Event(EventBuilder b) 
    {
        super(b);
    }

}
