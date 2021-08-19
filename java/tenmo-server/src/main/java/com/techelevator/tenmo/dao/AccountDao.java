package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    Balance getBalance(String user);

    boolean transferMoney(Principal principal,String user, BigDecimal amount);

    boolean transferUpdateAccount(Principal principal,String user, BigDecimal amount);

    Transfer getTransferById(int id);
}
