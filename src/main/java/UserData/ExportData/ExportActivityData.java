package UserData.ExportData;

import activity.IUserActivityService;
import activity.UserActivity;
import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import exception.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExportActivityData implements ExportStrategy {
    private static final Logger logger = Logger.getLogger(ExportActivityData.class.getName());


    private final IUserActivityService userActivityService;
    GivePath givePath = new GivePath();


    public ExportActivityData(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    public void exportData(String userId, String folderPath) throws SystemBusyException, BadRequestException, NotFoundException {

        try {
            Util.validateUserName(userId);

            if (isNewUser(userId)) {
                logger.log(Level.INFO, "User {} is a new user and doesn't have activity data.", userId);
                return;
            }

            List<UserActivity> userActivities = userActivityService.getUserActivity(userId);

            if (userActivities != null && !userActivities.isEmpty()) {
                String fileName = folderPath + givePath.path(userId,"ACTIVITY");
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("User Activity Data for User: " + userId);
                    writer.println("----------------------------------");

                    for (UserActivity activity : userActivities) {
                        writer.println("Activity ID: " + activity.getId());
                        writer.println("Type: " + activity.getActivityType());
                        writer.println("Date: " + activity.getActivityDate());
                        writer.println("----------------------");
                    }

                    logger.log(Level.INFO, "User activity data exported to file: {0}", fileName);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error exporting user activity data for user : "+ userId, e);
                }
            } else {
                logger.log(Level.INFO, "No user activity found for User ID: {0}", userId);
            }
        } catch (SystemBusyException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error: {0}"+ e.getMessage(), e);
        }
    }

    private boolean isNewUser(String userId) throws BadRequestException, SystemBusyException, NotFoundException {
        try {
            userActivityService.getUserActivity(userId);
            return false;  // User exist
        } catch (NotFoundException e) {
            return true;   // User not exist
        } catch (SystemBusyException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error checking user existence for user {0}"+ userId, e);
            return false;
        }

    }
}



