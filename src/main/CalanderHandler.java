package main;

import java.time.LocalDateTime;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;

public class CalanderHandler {
    
    public boolean exportToCalander(String path, Profile p){
        try{
            net.fortuna.ical4j.model.Calendar calendar = new Calendar();

            for(AbstractEvent e: p.getEvents()){
                
                if(e.getFrequency() == AbstractEvent.Frequencies.NOT_RECURRING){
                    LocalDateTime startDateTime = e.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().with(e.getStartTime());
                    LocalDateTime endDateTime = e.getEndDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime().with(e.getEndTime());
                    VEvent event = new VEvent(startDateTime, endDateTime, e.getEventName());
                    calendar.getComponents().add(event);
                }

                if(e.getFrequency() == AbstractEvent.Frequencies.DAILY){
                    java.time.Duration duration = Duration.between(e.getStartTime(), e.getEndTime());
                }
                
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public AbstractEvent[] importFromCalander(String path){
        return null;
    }
}
