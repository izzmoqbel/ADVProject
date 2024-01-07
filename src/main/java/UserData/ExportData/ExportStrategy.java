package UserData.ExportData;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

public interface ExportStrategy {
    void exportData(String userId, String folderPath) throws SystemBusyException, BadRequestException, NotFoundException;
}
