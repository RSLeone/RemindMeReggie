package main;

public class ProfileBackupHandler {

    PersistenceFactory factory = new PersistenceFactory();

    public static boolean generateBackup(String fileLocation, Profile p, PersistenceFactory.persistenceType t){
        if(p == null){
            return false;
        }

        Persistence persistence = (new PersistenceFactory()).getPersistent(t);

        persistence.setLocation(fileLocation);

        return persistence.save(p);
    }

    public static boolean restoreFromBackup(String location, PersistenceFactory.persistenceType t, String username){
        Persistence persistence = (new PersistenceFactory()).getPersistent(t);
        persistence.setLocation(location);
        Profile p = persistence.load(username);

        if(p == null){
            return false;
        }

        persistence.setDefaultLocation();
        
        return persistence.save(p);
    }
}
