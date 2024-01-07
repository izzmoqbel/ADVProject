package UserData.FileStorage;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DropboxUploader  implements FileUploader{
    private static final Logger logger = Logger.getLogger(DropboxUploader.class.getName());

    static String secretsFilePath = "secret.env";

    static String dropboxToken = ReadToken.readToken(secretsFilePath, "DROPBOX_ACCESS_TOKEN");

    private static final String ACCESS_TOKEN =dropboxToken;

    @Override
    public void uploadFile(String userId) {

        DbxRequestConfig config = DbxRequestConfig.newBuilder("ADV-Project").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);


        String localFilePath = "C:/Users/DELL/IdeaProjects/AD-project/" + userId + "_DataCompress/" + userId +".zip";
        String dropboxPath = "/ADV-Project/Data.zip";

        try {
            if (fileExists(client, dropboxPath)) {
                logger.log(Level.WARNING, "The path {0} already exists.", dropboxPath);
                return ;
            }

            try (InputStream in = new FileInputStream(localFilePath)) {
                FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(in);

                String downloadLink = client.sharing().createSharedLinkWithSettings(dropboxPath).getUrl();

                logger.log(Level.INFO, "File uploaded successfully!");
                logger.log(Level.INFO, "Download link: {0}", downloadLink);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error uploading file to Dropbox: {0}", e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean fileExists(DbxClientV2 client, String dropboxPath) {
        try {
            client.files().getMetadata(dropboxPath);
            return true;  // file exists
        } catch (Exception e) {
            return false;
        }
    }

}
