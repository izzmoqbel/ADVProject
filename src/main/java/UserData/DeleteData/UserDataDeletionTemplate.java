package UserData.DeleteData;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UserDataDeletionTemplate {
    private static final Logger logger = Logger.getLogger(UserDataDeletionTemplate.class.getName());

    public final void deleteUserData(String userId) {
        try {
            logger.log(Level.INFO, "Start deleting user data for user: {0}", userId);
            deleteAllUserActivities(userId);
            deleteIamData(userId);
            deletePaymentData(userId);
            deletePostData(userId);
            logger.log(Level.INFO, "User data deletion completed for user: {0}", userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting user data for user: " + userId, e);
        }
    }

    protected abstract void deleteIamData(String userId);

    protected abstract void deleteAllUserActivities(String userId);

    protected abstract void deletePaymentData(String userId);

    protected abstract void deletePostData(String userId);
}
