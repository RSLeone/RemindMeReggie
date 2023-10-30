import java.util.ArrayList;

public class PersistenceFactory {
    public enum persistenceType {LocalFile}
    private ArrayList<Persistence> persistentObjs;
    
    public Persistence getPersistent(persistenceType e){
        if(e == persistenceType.LocalFile){
            return new LocalFile();
        }
        return null;
    }
}
