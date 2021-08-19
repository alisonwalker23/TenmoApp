package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    Balance getBalance(String user);

    boolean transferMoney(Principal principal,String user, BigDecimal amount);

    void createTransfer(Transfer transfer);

    Transfer getTransferById(int id);
}
