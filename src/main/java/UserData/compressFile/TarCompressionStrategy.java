package UserData.compressFile;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;

public class TarCompressionStrategy implements CompressionStrategy {

    private static final Logger logger = Logger.getLogger(TarCompressionStrategy.class.getName());

    private void addFolderToTar(File folder, ArchiveOutputStream archiveOutputStream, String basePath) throws IOException {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                String entryName = basePath + file.getName();
                TarArchiveEntry entry = new TarArchiveEntry(file, entryName);

                archiveOutputStream.putArchiveEntry(entry);
                ArchiveEntryHandler archiveEntryHandler =new ArchiveEntryHandler(archiveOutputStream);
                archiveEntryHandler.addFileToArchive(file,entryName);

                if (file.isDirectory()) {
                    addFolderToTar(file, archiveOutputStream, entryName + "/");
                }
            }
        }
    }

    @Override
    public void compressFolder(File folder, ArchiveOutputStream archiveOutputStream, String basePath) throws IOException {
        try {
            addFolderToTar(folder, archiveOutputStream, basePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error compressing folder: " + folder.getAbsolutePath(), e);
            throw e;
        }
    }
}
