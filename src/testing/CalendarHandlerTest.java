package testing;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;
import main.Event.EventBuilder;

public class CalendarHandlerTest{
    String location = "./calendarTestFiles/";
    Profile p;

    @Before
    public void init(){
        p = new Profile("TestProfile", "123456");
    }

    @After
    public void clean(){
        p = null;
    }

    @Test
    public void testExportImproperFileLocation(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.YEARLY);
        boolean result = CalendarHandler.exportToCalander(location + "./notexistingfolder/testExportYearlyReccuringEvent.ics", p);

        assertEquals(false, result);
    }

    @Test
    public void testExportNullProfile(){
        boolean result = CalendarHandler.exportToCalander(location + "testExportVoidProfile.ics", null);

        assertEquals(false, result);
    }   

    @Test
    public void testExportNoEventProfile(){
        boolean result = CalendarHandler.exportToCalander(location + "testExportNoEventProfile.ics", p);

        assertEquals(false, result);
    }   

    @Test
    public void testExportNotReccuringEvent(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.NOT_RECURRING);
        boolean result = CalendarHandler.exportToCalander(location + "testExportNoEventProfile.ics", p);

        assertEquals(true, result);
    }
    
    @Test
    public void testExportDailyReccuringEvent(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.DAILY);
        boolean result = CalendarHandler.exportToCalander(location + "testExportDailyReccuringEvent.ics", p);

        assertEquals(true, result);
    }

    @Test
    public void testExportWeeklyReccuringEvent(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.WEEKLY);
        boolean result = CalendarHandler.exportToCalander(location + "testExportWeeklyReccuringEvent.ics", p);

        assertEquals(true, result);
    }

    @Test
    public void testExportMonthlyReccuringEvent(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.MONTHLY);
        boolean result = CalendarHandler.exportToCalander(location + "testExportMonthlyReccuringEvent.ics", p);

        assertEquals(true, result);
    }

    @Test
    public void testExportYearlyReccuringEvent(){
        EventHandler.addEvent(p, "Fun time", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plus(2, ChronoUnit.HOURS), location, 0, AbstractEvent.Frequencies.YEARLY);
        boolean result = CalendarHandler.exportToCalander(location + "testExportYearlyReccuringEvent.ics", p);

        assertEquals(true, result);
    }

    @Test
    public void testImportImproperFileLocation(){
        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "idontexist.ics");

        assertEquals(null, list);
    }

    @Test
    public void testImportInvalidFile(){
        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "invalid.ics");

        assertEquals(null, list);
    }

    @Test
    public void testImportNotReccuringEvent(){
        testExportNotReccuringEvent();

        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "testExportNoEventProfile.ics");
        ArrayList<AbstractEvent> list2 = p.getEvents();

        for(int i = 0; i < list.size(); i++){
            if(i < list2.size()){
                assertEquals(list.get(i).getEventName(), list2.get(i).getEventName());
                assertEquals(list.get(i).getFrequency(), list2.get(i).getFrequency());
                assertEquals(list.get(i).getStartDateTime(), list2.get(i).getStartDateTime());
                assertEquals(list.get(i).getEndDateTime(), list2.get(i).getEndDateTime());
            }
            else{
                assertEquals(true, false);
            }
        }
        
    }
    
    @Test
    public void testImportDailyReccuringEvent(){
        testExportDailyReccuringEvent();

        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "testExportDailyReccuringEvent.ics");
        ArrayList<AbstractEvent> list2 = p.getEvents();

        for(int i = 0; i < list.size(); i++){
            if(i < list2.size()){
                assertEquals(list.get(i).getEventName(), list2.get(i).getEventName());
                assertEquals(list.get(i).getFrequency(), list2.get(i).getFrequency());
                assertEquals(list.get(i).getStartDateTime(), list2.get(i).getStartDateTime());
                assertEquals(list.get(i).getEndDateTime(), list2.get(i).getEndDateTime());
            }
            else{
                assertEquals(true, false);
            }
        }
    }

    @Test
    public void testImportWeeklyReccuringEvent(){
        testExportWeeklyReccuringEvent();

        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "testExportWeeklyReccuringEvent.ics");
        ArrayList<AbstractEvent> list2 = p.getEvents();

        for(int i = 0; i < list.size(); i++){
            if(i < list2.size()){
                assertEquals(list.get(i).getEventName(), list2.get(i).getEventName());
                assertEquals(list.get(i).getFrequency(), list2.get(i).getFrequency());
                assertEquals(list.get(i).getStartDateTime(), list2.get(i).getStartDateTime());
                assertEquals(list.get(i).getEndDateTime(), list2.get(i).getEndDateTime());
            }
            else{
                assertEquals(true, false);
            }
        }
    }

    @Test
    public void testImportMonthlyReccuringEvent(){
        testExportMonthlyReccuringEvent();

        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "testExportMonthlyReccuringEvent.ics");
        ArrayList<AbstractEvent> list2 = p.getEvents();

        for(int i = 0; i < list.size(); i++){
            if(i < list2.size()){
                assertEquals(list.get(i).getEventName(), list2.get(i).getEventName());
                assertEquals(list.get(i).getFrequency(), list2.get(i).getFrequency());
                assertEquals(list.get(i).getStartDateTime(), list2.get(i).getStartDateTime());
                assertEquals(list.get(i).getEndDateTime(), list2.get(i).getEndDateTime());
            }
            else{
                assertEquals(true, false);
            }
        }
    }

    @Test
    public void testImportYearlyReccuringEvent(){
        testExportYearlyReccuringEvent();

        ArrayList<AbstractEvent> list = CalendarHandler.importFromCalander(location + "testExportYearlyReccuringEvent.ics");
        ArrayList<AbstractEvent> list2 = p.getEvents();

        for(int i = 0; i < list.size(); i++){
            if(i < list2.size()){
                assertEquals(list.get(i).getEventName(), list2.get(i).getEventName());
                assertEquals(list.get(i).getFrequency(), list2.get(i).getFrequency());
                assertEquals(list.get(i).getStartDateTime(), list2.get(i).getStartDateTime());
                assertEquals(list.get(i).getEndDateTime(), list2.get(i).getEndDateTime());
            }
            else{
                assertEquals(true, false);
            }
        }
    }

}