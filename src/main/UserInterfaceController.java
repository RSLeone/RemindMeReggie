package main;
public class UserInterfaceController {
    Profile currentProfile;

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile p){
        this.currentProfile = p;
    }
}
