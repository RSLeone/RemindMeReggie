public abstract class Persistence {
    private UserInterfaceController userInterFaceController;
    public abstract void save();
    public abstract void load();

    public UserInterfaceController getUserInterfaceController(){
        return this.userInterFaceController;
    }

    public void setUserInterfaceController(UserInterfaceController u){
        this.userInterFaceController = u;
    }
}
