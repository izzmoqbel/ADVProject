package UserData.ExportData;

import java.io.File;

public class CreateFolder {

    public  String create(String userId) {
        String folderPath = userId + "_data/";
        new File(folderPath).mkdirs();
        return folderPath;
    }
}
