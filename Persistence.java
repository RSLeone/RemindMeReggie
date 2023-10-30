public abstract class Persistence {
    private UserInterfaceController userInterFaceController;
    public abstract void save(Profile p);
    public abstract Profile load();
}
