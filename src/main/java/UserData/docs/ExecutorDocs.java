package UserData.docs;


import UserData.UserData;
import UserData.UserExists;
import iam.IUserService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutorDocs {

    private static final Logger logger = Logger.getLogger(ExecutorDocs.class.getName());

    private final IUserService userService;

    public ExecutorDocs(IUserService userService) {
        this.userService = userService;
    }


    public void makePDF(String userId) {


        String []userData = {"POST","ACTIVITY","IAM","PAYMENT"};

        UserExists userExists = new UserExists(userService);


        if (!userExists.check(userId)) {
            logger.log(Level.SEVERE,"User does not exist: {0}", userId);
            return;
        }

        DocsFactory docs=new DocsFactory(userId,null);
        for(int i=0;i<=userData.length-1;i++){
            docs.setFileType(userData[i]);
            docs.getDocs();
        }

    }

}
