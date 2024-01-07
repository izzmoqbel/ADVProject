package UserData.compressFile;

import org.apache.commons.compress.archivers.ArchiveOutputStream;

import java.io.File;
import java.io.IOException;

public interface CompressionStrategy {
    void compressFolder(File folder, ArchiveOutputStream archiveOutputStream, String basePath) throws IOException;
}

