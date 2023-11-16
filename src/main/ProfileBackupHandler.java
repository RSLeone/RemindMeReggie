package main;

public class ProfileBackupHandler {

    public static boolean generateBackup(String fileLocation, Profile p, Persistence persistence){
        if(p == null || persistence == null){
            return false;
        }

        persistence.setLocation(fileLocation);

        return persistence.save(p);
    }

    public static boolean restoreFromBackup(String location, Persistence persistence, String username){
        persistence.setLocation(location);
        Profile p = persistence.load(username);

        if(p == null){
            return false;
        }

        persistence.setDefaultLocation();
        
        return persistence.save(p);
    }
}
