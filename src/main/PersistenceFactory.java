package main;
import java.util.ArrayList;

public class PersistenceFactory {
    public enum persistenceType {JsonFile}
    private ArrayList<Persistence> persistentObjs;

    public PersistenceFactory(){
        persistentObjs = new ArrayList<>();
    }
    
    public Persistence getPersistent(persistenceType e){
        if(e == persistenceType.JsonFile){
            JsonFile lf = new JsonFile();
            persistentObjs.add(lf);
            return lf;
        }
        return null;
    }
}
