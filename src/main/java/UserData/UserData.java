package UserData;


import UserData.ExportData.CreateFolder;
import UserData.ExportData.DataExporter;
import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import exception.Util;
import iam.IUserService;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserData {

    private static final Logger logger = Logger.getLogger(UserData.class.getName());

    private final DataExporter dataExporter;
    private final IUserService userService;
    private final CreateFolder creatFolder= new CreateFolder();


    public UserData(DataExporter dataExporter, IUserService userService) {
        this.dataExporter = dataExporter;
        this.userService = userService;
    }

    public void start(String userId) throws SystemBusyException, BadRequestException, NotFoundException {
        try {
            Util.validateUserName(userId);

            UserExists userExists = new UserExists(userService);


            if (!userExists.check(userId)) {
                logger.log(Level.SEVERE,"User does not exist: {0}", userId);
                return;
            }



            dataExporter.exportAllData(userId,creatFolder.create(userId));

        } catch (SystemBusyException | BadRequestException | NotFoundException e) {
            logger.log(Level.SEVERE, "Error when export data for user " + userId + "{0}",e.getMessage());
        }

    }



}

