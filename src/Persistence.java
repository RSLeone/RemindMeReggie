public abstract class Persistence {
    private UserInterfaceController userInterFaceController;
    public abstract void save(Profile p);
    public abstract Profile load(String username);

    public UserInterfaceController getUserInterfaceController(){
        return this.userInterFaceController;
    }

    public void setUserInterfaceController(UserInterfaceController u){
        this.userInterFaceController = u;
    }
}
