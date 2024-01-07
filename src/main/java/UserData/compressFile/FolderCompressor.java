package UserData.compressFile;

import UserData.docs.CreateFolder;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FolderCompressor {

    private CompressionStrategy compressionStrategy;
    private final Logger logger;

    public FolderCompressor(CompressionStrategy compressionStrategy) {
        this.compressionStrategy = compressionStrategy;
        this.logger = Logger.getLogger(FolderCompressor.class.getName());
    }

    public void compressFolder(String userID, String typeCompress) {




        String folderPath = userID + "_dataPDF";
        String savePath = userID+"_DataCompress/" + userID + "." + typeCompress;
        CreateFolder createFolder =new CreateFolder(savePath);
        createFolder.createFolderIfNotExists();
        File outputFile = new File(savePath);
        File folder = new File(folderPath);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            ArchiveOutputStream archiveOutputStream;

            if (savePath.endsWith(".zip")) {
                compressionStrategy = new ZipCompressionStrategy();
                archiveOutputStream = new ZipArchiveOutputStream(fileOutputStream);
            } else {
                compressionStrategy = new TarCompressionStrategy();
                archiveOutputStream = new TarArchiveOutputStream(fileOutputStream);
            }

            compressionStrategy.compressFolder(folder, archiveOutputStream, "");
            archiveOutputStream.finish();
            archiveOutputStream.close();

            logger.log(Level.INFO, "folder compressed successfully : " + folderPath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "error compressing folder : " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
