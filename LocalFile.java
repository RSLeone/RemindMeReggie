import java.io.File;

public class LocalFile implements Persistence{
    private String filePath;
    
    public void save(){
        
    }
    public void load(){

    }

    public void setFilePath(String fileName){
        filePath = "./Backup/" + fileName + ".json";
    }
}
