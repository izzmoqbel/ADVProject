package UserData;

import iam.IUserService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserExists {
      private static final Logger logger = Logger.getLogger(UserData.class.getName());
      private final IUserService userService;

    public UserExists(IUserService userService) {
        this.userService = userService;
    }


    public boolean check(String userId) {
        try {
            return userService.getUser(userId) != null;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error checking user existence: {0}", e.getMessage());
            return false;
        }
    }
}
