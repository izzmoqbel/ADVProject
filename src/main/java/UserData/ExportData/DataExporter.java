package UserData.ExportData;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.util.logging.Level;
import java.util.logging.Logger;
public class DataExporter {
    private static final Logger logger = Logger.getLogger(DataExporter.class.getName());

    private final ExportStrategy iamStrategy;
    private final ExportStrategy activityStrategy;
    private final ExportStrategy paymentStrategy;
    private final ExportStrategy postStrategy;
    public DataExporter(ExportStrategy iamStrategy, ExportStrategy activityStrategy,
                        ExportStrategy paymentStrategy, ExportStrategy postStrategy) {
        this.iamStrategy = iamStrategy;
        this.activityStrategy = activityStrategy;
        this.paymentStrategy = paymentStrategy;
        this.postStrategy = postStrategy;
    }

    public void exportAllData(String userId, String folderPath) throws SystemBusyException, BadRequestException, NotFoundException {
        try {
        logger.log(Level.INFO, "Start exporting data for user: {0}", userId);
        iamStrategy.exportData(userId, folderPath);
        activityStrategy.exportData(userId, folderPath);
        paymentStrategy.exportData(userId, folderPath);
        postStrategy.exportData(userId, folderPath);
        logger.log(Level.INFO, "Data export completed for user: {0}", userId);

        }catch (SystemBusyException | BadRequestException | NotFoundException e) {
            logger.log(Level.SEVERE, "Error exporting data for user: " + userId, e);
        }
    }

}

