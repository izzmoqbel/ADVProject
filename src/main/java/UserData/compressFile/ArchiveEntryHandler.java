package UserData.compressFile;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchiveEntryHandler {
    private static final Logger logger = Logger.getLogger(ArchiveEntryHandler.class.getName());

    private final ArchiveOutputStream archiveOutputStream;

    public ArchiveEntryHandler(ArchiveOutputStream archiveOutputStream) {
        this.archiveOutputStream = archiveOutputStream;
    }

    public void addFileToArchive(File file, String entryName) throws IOException {
        ArchiveEntry entry = archiveOutputStream.createArchiveEntry(file, entryName);

        archiveOutputStream.putArchiveEntry(entry);

        if (file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = fileInputStream.read(buffer)) > 0) {
                    archiveOutputStream.write(buffer, 0, length);
                }

                archiveOutputStream.closeArchiveEntry();
            } catch (IOException e) {

                logger.log(Level.SEVERE, "Error reading file: " + file.getAbsolutePath(), e);
                throw e;
            }
        }
    }
}
