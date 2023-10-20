import java.util.ArrayList;

public class MonitoredEvent extends AbstractEvent{

    private int numSteps;
    private int numCompletedSteps;
    private ArrayList<Step> steps;
    private int nextStepNumber;

    private MonitoredEvent(MonitoredEventBuilder b) 
    {
        super(b);

        //Set the unique fields of the MonitoredEvent to default values
        this.numSteps = b.numSteps;
        this.numCompletedSteps = b.numCompletedSteps;
        this.steps = b.steps;
        this.nextStepNumber = b.nextStepNumber;
    }

    public static class MonitoredEventBuilder extends AbstractEventBuilder{
        private int numSteps = 0;
        private int numCompletedSteps = 0;
        private ArrayList<Step> steps = new ArrayList<>();
        private int nextStepNumber = 0;
        
        @Override
        public MonitoredEvent build(){
            return new MonitoredEvent(this);
        }
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

    public int getPercentCompleted() {
        return (int)Math.round(100*((double)numSteps / (double)numCompletedSteps));
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
