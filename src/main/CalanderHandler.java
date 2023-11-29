package main;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.io.FileOutputStream;
import java.time.Duration;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.transform.recurrence.Frequency;
import net.fortuna.ical4j.util.RandomUidGenerator;


public class CalanderHandler {
    
    public boolean exportToCalander(String path, Profile p){
        try{
            Calendar calendar = new Calendar();

            calendar.add(new ProdId("RemindMeReggie"));
            calendar.add(new Version(new ParameterList(), Version.VALUE_2_0));

            for(AbstractEvent e: p.getEvents()){
                
                LocalDateTime startDateTime = e.getStartDateTime();
                LocalDateTime endDateTime = e.getEndDateTime();

                // Create a unique identifier generator (UID)
                RandomUidGenerator ug = new RandomUidGenerator();

                if(e.getFrequency() == AbstractEvent.Frequencies.NOT_RECURRING){
                    VEvent event = new VEvent(startDateTime, endDateTime, e.getEventName());
                    
                    event.add(ug.generateUid());
                    calendar.add(event);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.DAILY){
                    
                    LocalDateTime endTime = LocalDateTime.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth(), endDateTime.getHour(), endDateTime.getMinute(), endDateTime.getSecond());
                    Duration duration = Duration.between(startDateTime, endTime);
                    
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.DAILY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, duration, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    recurringEvent.add(new DtEnd<>(endTime));

                    calendar.add(recurringEvent);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.WEEKLY){
                    LocalDateTime endTime = LocalDateTime.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth(), endDateTime.getHour(), endDateTime.getMinute(), endDateTime.getSecond());
                    Duration duration = Duration.between(startDateTime, endTime);
                    
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.WEEKLY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, duration, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    recurringEvent.add(new DtEnd<>(endTime));

                    calendar.add(recurringEvent);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.MONTHLY){
                    LocalDateTime endTime = LocalDateTime.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth(), endDateTime.getHour(), endDateTime.getMinute(), endDateTime.getSecond());
                    Duration duration = Duration.between(startDateTime, endTime);
                    
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.MONTHLY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, duration, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    recurringEvent.add(new DtEnd<>(endTime));

                    calendar.add(recurringEvent);
                }

                else{
                    return false;
                }
                
            }

            FileOutputStream fout = new FileOutputStream(path);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
            fout.close();

            return true;
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
