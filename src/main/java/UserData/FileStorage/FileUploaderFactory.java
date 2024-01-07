package UserData.FileStorage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUploaderFactory {
    private static final Logger logger = Logger.getLogger(FileUploaderFactory.class.getName());


    public FileUploader getUploader(String uploaderType){
        logger.log(Level.WARNING, "Invalid uploaderType: null");
        if(uploaderType == null){
            return null;
        }
        if(uploaderType.equals("DROPBOX")){
            logger.log(Level.INFO, "Creating DropboxUploader");
            return new DropboxUploader();

        } else if(uploaderType.equals("GOOGLE_DRIVE")){
            logger.log(Level.INFO, "Creating GoogleDriveUploader");
            //return new DropboxUploader();
            //we must add the oGoogleDrive Object

        }
        logger.log(Level.WARNING, "Invalid uploaderType: {0}", uploaderType);
        return null;
    }
}
