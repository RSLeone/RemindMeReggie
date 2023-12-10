package main;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;

import main.Event.EventBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.transform.recurrence.Frequency;
import net.fortuna.ical4j.util.RandomUidGenerator;


public class CalendarHandler {
    
    public static boolean exportToCalander(String path, Profile p){
        if(p == null || path.equals("")){
            return false;
        }
        if(p.getEvents().size() <= 0){
            return false;
        }

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
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.DAILY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, endDateTime, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    

                    calendar.add(recurringEvent);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.WEEKLY){
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.WEEKLY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, endDateTime, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                   

                    calendar.add(recurringEvent);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.MONTHLY){
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.MONTHLY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, endDateTime, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    

                    calendar.add(recurringEvent);
                }

                else if(e.getFrequency() == AbstractEvent.Frequencies.YEARLY){
                    Recur<Temporal> recur = new Recur.Builder<>().frequency(Frequency.YEARLY).build();

                    VEvent recurringEvent = new VEvent(startDateTime, endDateTime, e.getEventName());

                    recurringEvent.add(ug.generateUid());
                    recurringEvent.add(new RRule<>(recur));
                    

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
        catch (IOException e){
            
        }

        return false;
    }

    public static ArrayList<AbstractEvent> importFromCalander(String path){
        CalendarBuilder builder = new CalendarBuilder();

        ArrayList<AbstractEvent> returnVal = new ArrayList<>();

        try{
            FileInputStream fin = new FileInputStream(path);
            Calendar calendar = builder.build(fin);
            fin.close();

            for(Object obj : calendar.getComponents()){
                if(obj instanceof VEvent){
                    VEvent event = (VEvent) obj;

                    LocalDateTime startDateTime = LocalDateTime.from(event.getDateTimeStart().get().getDate());
                    LocalDateTime endDateTime = LocalDateTime.from(event.getDateTimeEnd().get().getDate());
                    
                    AbstractEvent.Frequencies frequency = AbstractEvent.Frequencies.NOT_RECURRING;

                    for(Property p: event.getProperties()){
                        if(p instanceof RRule){
                            RRule r = (RRule)p;
                            
                            if(r.getValue().equals("FREQ=DAILY"))
                                frequency = AbstractEvent.Frequencies.DAILY;
                            else if(r.getValue() .equals( "FREQ=WEEKLY"))
                                frequency = AbstractEvent.Frequencies.WEEKLY;
                            else if(r.getValue() .equals( "FREQ=MONTHLY"))
                                frequency = AbstractEvent.Frequencies.MONTHLY;
                            else if(r.getValue() .equals( "FREQ=YEARLY"))
                                frequency = AbstractEvent.Frequencies.YEARLY;
                        }
                    }
                    
                    Event e = (Event)(new EventBuilder()).eventName(event.getSummary().get().getValue()).startDateTime(startDateTime).endDateTime(endDateTime).frequency(frequency).eventType("Event").build();
                    returnVal.add(e);
                }
            }

            return returnVal;
        }
        catch (IOException | ParserException e){
            
        }
        
        return null;
    }
}
