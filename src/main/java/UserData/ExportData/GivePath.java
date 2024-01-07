package UserData.ExportData;

import UserData.FileStorage.FileUploaderFactory;
import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GivePath {

    private static final Logger logger = Logger.getLogger(FileUploaderFactory.class.getName());


    public String  path(String userId,String exportType) throws SystemBusyException, NotFoundException, BadRequestException {


       if(exportType == null){
           logger.log(Level.WARNING, "Invalid uploaderType: null");
           return null;
       }
        switch (exportType) {
            case "IAM":
                logger.log(Level.INFO, "Creating Profile Path");
                return userId + "_Profile.txt";

            case "ACTIVITY":
                logger.log(Level.INFO, "Creating Activity Path");
                return userId + "_Activity.txt";

            case "PAYMENT":
                logger.log(Level.INFO, "Creating Payment Path");
                return userId + "_Payment.txt";

            case "POST":
                logger.log(Level.INFO, "Creating Post Path");
                return userId + "_Post.txt";

        }
       logger.log(Level.WARNING, "Invalid uploaderType: {0}", exportType);
       return null;



    }
}
