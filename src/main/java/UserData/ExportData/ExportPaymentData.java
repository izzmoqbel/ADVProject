package UserData.ExportData;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import exception.Util;
import payment.IPayment;
import payment.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportPaymentData implements ExportStrategy {
    private static final Logger logger = Logger.getLogger(ExportIamData.class.getName());

    private final IPayment paymentService;
    GivePath givePath = new GivePath();


    public ExportPaymentData(IPayment paymentService) {
        this.paymentService = paymentService;
    }
    @Override
    public void exportData(String userId, String folderPath) throws SystemBusyException, BadRequestException, NotFoundException {
        try {
            Util.validateUserName(userId);

            if (!isPremiumUser(userId)) {
                logger.log(Level.INFO, "User {0} is not a premium user, so they don't have payment data.", userId);
                return;
            }

            List<Transaction> userTransactions = paymentService.getTransactions(userId);

            if (userTransactions != null && !userTransactions.isEmpty()) {
                String fileName = folderPath + givePath.path(userId,"PAYMENT");

                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("Payment Data for Premium User: " + userId);
                    writer.println("----------------------------------");

                    for (Transaction transaction : userTransactions) {
                        writer.println("Transaction ID: " + transaction.getId());
                        writer.println("Amount: " + transaction.getAmount());
                        writer.println("Description: " + transaction.getDescription());
                        writer.println("Transaction Date: " + paymentService.getTransactions(userId));
                        writer.println("----------------------");
                    }

                    logger.log(Level.INFO, "Payment data exported to file: {0}", fileName);
                } catch (IOException e) {
                    logger.log(Level.SEVERE,"Error exporting payment data for user :"+ userId +"{0}" ,e.getMessage());
                }
            } else {
                logger.log(Level.INFO, "No payment data found for User ID: {0}", userId);
            }
        } catch (SystemBusyException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error: {0}" + e.getMessage(), e);
        }
    }

    private boolean isPremiumUser(String userId) throws NotFoundException, SystemBusyException, BadRequestException {
        return paymentService.getBalance(userId) > 0;
    }
}
