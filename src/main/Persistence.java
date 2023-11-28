package main;

public abstract class Persistence {
    private String location;

    public abstract boolean save(Profile p);
    public abstract Profile load(String username);
    public abstract boolean delete(String username);

    Persistence(){
        setDefaultLocation();
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
    
    public abstract void setDefaultLocation();
}
