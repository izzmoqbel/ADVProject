package UserData.compressFile;
import UserData.UserExists;
import iam.IUserService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutorCompress {
    private static final Logger logger = Logger.getLogger(ExecutorCompress.class.getName());


    private final IUserService userService;

    public ExecutorCompress(IUserService userService) {
        this.userService = userService;
    }
    public void start(String userId) {

        UserExists userExists = new UserExists(userService);



        if (!userExists.check(userId)) {
            logger.log(Level.SEVERE,"User does not exist: {0}", userId);
            return;
        }

        CompressionStrategy zipStrategy = new ZipCompressionStrategy();
        FolderCompressor zipCompressor = new FolderCompressor(zipStrategy);
        zipCompressor.compressFolder(userId, "zip");

        CompressionStrategy tarStrategy = new TarCompressionStrategy();
        FolderCompressor tarCompressor = new FolderCompressor(tarStrategy);
        tarCompressor.compressFolder(userId, "tar");

    }
}
