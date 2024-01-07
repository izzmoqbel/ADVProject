package UserData.compressFile;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZipCompressionStrategy implements CompressionStrategy {

    private static final Logger logger = Logger.getLogger(ZipCompressionStrategy.class.getName());

    private void addFolderToZip(File folder, ArchiveOutputStream archiveOutputStream, String basePath) throws IOException {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                String entryName = basePath + file.getName();
                ZipArchiveEntry entry = new ZipArchiveEntry(file, entryName);
                archiveOutputStream.putArchiveEntry(entry);
                ArchiveEntryHandler archiveEntryHandler =new ArchiveEntryHandler(archiveOutputStream);
                archiveEntryHandler.addFileToArchive(file,entryName);
                if (file.isDirectory()) {
                    addFolderToZip(file, archiveOutputStream, entryName + "/");
                }
            }
        }
    }

    @Override
    public void compressFolder(File folder, ArchiveOutputStream archiveOutputStream, String basePath) throws IOException {
        try {
            addFolderToZip(folder, archiveOutputStream, basePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error compressing folder: " + folder.getAbsolutePath(), e);
            throw e;
        }
    }
}
