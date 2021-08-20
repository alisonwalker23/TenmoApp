package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferUser;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface AccountDao {

    Balance getBalance(String user);

    void transferMoney(TransferUser transferUser);

    void createTransfer(TransferUser transferUser);

    Transfer getTransferById(int id);

    Transfer[] getAllTransfersByAccountId(int accountId);
}
