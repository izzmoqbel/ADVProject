package UserData.docs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextFileReader {
    private static final Logger logger = Logger.getLogger(TextFileReader.class.getName());

    private final Path filePath;

    public TextFileReader(Path filePath) {
        this.filePath = filePath;
    }

    public String readTextFile() {
        Path path = Paths.get(filePath.toUri());
        if(Files.exists(path)){}
        else {
            logger.log(Level.SEVERE,"Error reading text file");
            return "error";
        }


        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading text file: " + filePath, e);
            return "Error reading text file";
        }
        return content.toString();
    }
}
