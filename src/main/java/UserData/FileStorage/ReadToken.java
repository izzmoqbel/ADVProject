package UserData.FileStorage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadToken {
    private static final Logger logger = Logger.getLogger(FileUploaderFactory.class.getName());

    public static String readToken(String filePath, String tokenName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2 && parts[0].trim().equals(tokenName)) {
                    logger.log(Level.INFO, "Token '{0}' found in file '{1}'", new Object[]{tokenName, filePath});
                    return parts[1].trim();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when reading token from file "+ filePath + " : {0}",  e.getMessage());
        }

        logger.log(Level.WARNING, "Token '{0}' not found in file '{1}'", new Object[]{tokenName, filePath});
        return null; // Token not found
    }
}



