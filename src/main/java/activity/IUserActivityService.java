package activity;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import java.util.List;

public interface IUserActivityService {

    void addUserActivity(UserActivity userActivity);

    List<UserActivity> getUserActivity(String userId) throws SystemBusyException, BadRequestException, NotFoundException;

    void removeUserActivity(String userId, String id) throws SystemBusyException, BadRequestException, NotFoundException;
}
