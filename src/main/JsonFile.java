package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonFile extends Persistence{

    public class AbstractEventAdapter implements JsonSerializer<AbstractEvent>, JsonDeserializer<AbstractEvent> {
        @Override
        public JsonElement serialize(AbstractEvent src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
            result.add("properties", context.serialize(src, src.getClass()));
    
            return result;
        }
    
        @Override
        public AbstractEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
    
            try {
                return context.deserialize(element, Class.forName("main." + type));
            } catch (ClassNotFoundException cnfe) {
                throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
    }
    
    @Override
    public boolean save(Profile p){
        // Create a Gson instance
        Gson gson = new GsonBuilder().registerTypeAdapter(AbstractEvent.class, new AbstractEventAdapter()).create();

        String jsonString = gson.toJson(p);

        // creates directory if it does not exist
        File pathAsFile = new File(getLocation());
        if (!Files.exists(Paths.get(getLocation()))) {
            pathAsFile.mkdir();
        }

        try (FileWriter writer = new FileWriter(getLocation() + '/' + p.getUsername() + ".json")) {
            // Write the string to the file
            writer.write(jsonString);
            writer.close();
            return true;
        } catch (IOException e) {
            
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
            
        }

        if(fileContent.isEmpty()){
            return null;
        }
        
        // Create a Gson instance
        Gson gson = new GsonBuilder().registerTypeAdapter(AbstractEvent.class, new AbstractEventAdapter()).create();

        Profile p = gson.fromJson(fileContent, Profile.class);

        return p;
    }

    @Override
    public void setDefaultLocation() {
        setLocation("./profiles");
    }
    
    @Override
    public boolean delete(String username){
        File file = new File(getLocation() + "/" + username + ".json");

        return file.delete();
    }

    
}
