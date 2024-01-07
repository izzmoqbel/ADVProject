package UserData.docs;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.io.IOException;

public interface Docs {
    void creatDocs() throws IOException, SystemBusyException, NotFoundException, BadRequestException;
}
