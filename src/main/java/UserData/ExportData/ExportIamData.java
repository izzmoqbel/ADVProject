package UserData.ExportData;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import exception.Util;
import iam.IUserService;
import iam.UserProfile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportIamData  implements ExportStrategy{
    private static final Logger logger = Logger.getLogger(ExportIamData.class.getName());


    private final IUserService userService;

    GivePath givePath = new GivePath();
    public ExportIamData(IUserService userService) {
        this.userService = userService;
    }


    public void exportData(String userId, String folderPath) throws SystemBusyException, NotFoundException, BadRequestException {


        try {
            Util.validateUserName(userId);

            UserProfile userProfile = userService.getUser(userId);


            if (userProfile != null) {
                String fileName = folderPath +  givePath.path(userId,"IAM");;


                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("User Profile Data for User: " + userProfile.getUserName());
                    writer.println("----------------------------------");
                    writer.println("First Name: " + userProfile.getFirstName());
                    writer.println("Last Name: " + userProfile.getLastName());
                    writer.println("Phone Number: " + userProfile.getPhoneNumber());
                    writer.println("Email: " + userProfile.getEmail());
                    writer.println("Role: " + userProfile.getRole());
                    writer.println("Department: " + userProfile.getDepartment());
                    writer.println("Organization: " + userProfile.getOrganization());
                    writer.println("Country: " + userProfile.getCountry());
                    writer.println("City: " + userProfile.getCity());
                    writer.println("Street: " + userProfile.getStreet());
                    writer.println("Postal Code: " + userProfile.getPostalCode());
                    writer.println("Building: " + userProfile.getBuilding());
                    writer.println("User Type: " + userProfile.getUserType());
                    writer.println("----------------------------------");
                    logger.log(Level.INFO, "IAM data exported successfully to: {0}", fileName);
                }
            } else {
                logger.log(Level.SEVERE, "User {0} not found for export", userId);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error exporting IAM data: {0}" + e.getMessage(), e);
        }
    }
}



