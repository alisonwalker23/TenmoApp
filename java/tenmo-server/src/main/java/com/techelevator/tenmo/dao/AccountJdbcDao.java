package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;

@Component
public class AccountJdbcDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   @Override
    public Balance getBalance(String user) {
        Balance balance = new Balance();
        String sql = "SELECT balance FROM accounts JOIN users USING(user_id) WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user);

        if (results.next()) {
            double newBalance = results.getDouble("balance");
            BigDecimal newBal = new BigDecimal(newBalance);

            balance.setBalance(newBal);
        }

        return balance;
    }

    @Override
    public Balance transferMoney(Principal principal, String user, BigDecimal amount) {
        //Need to provide the list of users on client side
        //We take in their choice

        Balance balanceSender = new Balance();
        String sql = "SELECT balance FROM accounts JOIN users USING(user_id) WHERE username = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, principal.getName());

        if (result.next()) {
            double newBalance = result.getDouble("balance");
            BigDecimal newBal = new BigDecimal(newBalance);

            balanceSender.setBalance(newBal);
        }


        Balance balanceReceiver = new Balance();
        String sql1 = "SELECT balance FROM accounts JOIN users USING(user_id) WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql1, user);
        if (results.next()) {

            double receiverBalance = result.getDouble("balance");
            BigDecimal newRecBal = new BigDecimal(receiverBalance);
            balanceReceiver.setBalance(newRecBal);
        }


        if (balanceSender.getBalance().compareTo(amount) == 1) {
            BigDecimal newSenderBalance = balanceSender.getBalance().subtract(amount);
            balanceSender.setBalance(newSenderBalance);

            BigDecimal newReceiverBalance = balanceReceiver.getBalance().add(amount);
            balanceReceiver.setBalance(newReceiverBalance);

            //add transfer status = approved

        }

        return null;
    }

}
