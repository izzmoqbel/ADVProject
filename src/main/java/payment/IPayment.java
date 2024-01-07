package payment;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.util.List;

public interface IPayment {
    void pay(Transaction transaction);
    double getBalance(String userName);
    void removeTransaction(String userName, String id) throws SystemBusyException, BadRequestException, NotFoundException;
    List<Transaction> getTransactions(String userName) throws SystemBusyException, BadRequestException, NotFoundException;
}
