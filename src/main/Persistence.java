package main;

public abstract class Persistence {
    private UserInterfaceController userInterFaceController;

    private String location;

    public abstract boolean save(Profile p);
    public abstract Profile load(String username);

    Persistence(){
        setDefaultLocation();
    }

    public UserInterfaceController getUserInterfaceController(){
        return this.userInterFaceController;
    }

    public void setUserInterfaceController(UserInterfaceController u){
        this.userInterFaceController = u;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
    
    public abstract void setDefaultLocation();
}
