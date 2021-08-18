package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    Balance getBalance(String user);

    Balance transferMoney(Principal principal,String user, BigDecimal amount);

}