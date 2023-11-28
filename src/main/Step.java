package main;
public class Step {
    
    private String stepName;
    private int stepNumber;
    private boolean isComplete;

    public Step(String stepName, int stepNumber, boolean isComplete) {
        this.stepName = stepName;
        this.stepNumber = stepNumber;
        this.isComplete = isComplete;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

}
