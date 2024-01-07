package UserData.docs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class CreateFolder {
    private static final Logger logger = Logger.getLogger(CreateFolder.class.getName());

    String path;

    public CreateFolder(String path) {
        this.path = path;
    }

    public void createFolderIfNotExists() {
        Path folderPath = Paths.get(path).getParent();
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
                logger.info("Folder created: " + folderPath);

            } catch (IOException e) {
                logger.severe("Error creating folder: " + folderPath);
                e.printStackTrace();
            }
        }
    }
}
