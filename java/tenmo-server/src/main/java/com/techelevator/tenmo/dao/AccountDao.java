package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface AccountDao {

    Balance getBalance(String user);

    void transferMoney(Transfer transfer);

    void createTransfer(Transfer transfer);

    Transfer getTransferById(int id);

    //List<Transfer> getAllTransfersForUser(int userId);
}
