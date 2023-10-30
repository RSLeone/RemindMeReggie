import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LocalFile extends Persistence{
    private String filePath;
    private String fileDir = "./Profiles";
    
    @Override
    public void save(Profile p){
        setFilePath(p.getUsername());
        
        File f = new File(filePath);
        File dir = new File(fileDir);
        
        try{
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("[");
            writer.newLine();
            writer.write("\t{");
            writer.newLine();
            writer.write("\t\t\"username\":\"" + p.getUsername() + "\",");
            writer.newLine();
            writer.write("\t\t\"hashedPassword\":\"" + p.getHashedPassword() + "\",");
            writer.newLine();
            writer.write("\t\t\"nextEventId\":\"" + p.getNextEventId() + "\",");
            writer.newLine();
            writer.write("\t\t\"events\":[");
            writer.newLine();

            writer.close();
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Profile load(){
        return null;
    }

    private void setFilePath(String userName){
        filePath = "./Profiles/" + userName + ".json";
    }
}
