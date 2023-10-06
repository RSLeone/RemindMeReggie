import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class MonitoredEvent extends AbstractEvent{

    private int numSteps;
    private int numCompletedSteps;
    private double percentCompleted;
    private ArrayList<Step> steps;
    private int nextStepNumber;

    public MonitoredEvent(String eventId, String eventName, LocalTime startTime, LocalTime endTime, Date startDate,
            Date endDate, String eventType, boolean isComplete, int severityLevel,
            Event.Frequencies frequency) 
    {
        super(eventId, eventName, startTime, endTime, startDate, endDate, eventType, isComplete, severityLevel, frequency);

        //Set the unique fields of the MonitoredEvent to default values
        numSteps = 0;
        numCompletedSteps = 0;
        percentCompleted = 0.0;
        steps = new ArrayList<Step>();
        nextStepNumber = 0;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public int getNumCompletedSteps() {
        return numCompletedSteps;
    }

    public void setNumCompletedSteps(int numCompletedSteps) {
        this.numCompletedSteps = numCompletedSteps;
    }

    public double getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(double percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public int getNextStepNumber() {
        return nextStepNumber;
    }

    public void setNextStepNumber(int nextStepNumber) {
        this.nextStepNumber = nextStepNumber;
    }

}
