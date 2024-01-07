package UserData.docs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DocsFactory {
    private static final Logger logger = Logger.getLogger(DocsFactory.class.getName());

    private final String userID;
    private String fileType;

    public DocsFactory(String userID, String fileType) {
        this.fileType = fileType;
        this.userID = userID;
    }

    public void getDocs() {
        CreatDocs creatDocs = new CreatDocs(userID, fileType);
        try {
            creatDocs.creatDocs();
            logger.info("Document created successfully for user ID: " + userID + ", file type: " + fileType);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating document", e);
        }
    }

    public void setFileType(String fileType) {

        this.fileType = fileType;
        logger.info("File type set to: " + fileType);
    }
}
