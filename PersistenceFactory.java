import java.util.ArrayList;

public class PersistenceFactory {
    public enum persistenceType {LocalFile}
    private ArrayList<Persistence> persistentObjs;

    PersistenceFactory(){
        persistentObjs = new ArrayList<>();
    }
    
    public Persistence getPersistent(persistenceType e){
        if(e == persistenceType.LocalFile){
            LocalFile lf = new LocalFile();
            persistentObjs.add(lf);
            return lf;
        }
        return null;
    }
}
