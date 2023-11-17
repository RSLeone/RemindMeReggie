package main;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class JsonFile extends Persistence{
    
    @Override
    public boolean save(Profile p){
        // Create a Gson instance
        Gson gson = new Gson();

        String jsonString = gson.toJson(p);

        try (FileWriter writer = new FileWriter(getLocation() + '/' + p.getUsername() + ".json")) {
            // Write the string to the file
            writer.write(jsonString);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public Profile load(String username){

        String fileContent = "";

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(getLocation() + "/" + username + ".json"));

            fileContent = new String(bytes, "UTF-8");
        }

        catch (IOException e){
            e.printStackTrace();
        }

        if(fileContent.isEmpty()){
            return null;
        }
        
        // Create a Gson instance
        Gson gson = new Gson();

        Profile p = gson.fromJson(fileContent, Profile.class);

        return p;
    }

    @Override
    public void setDefaultLocation() {
        setLocation("./profiles");
    }   

    
}
