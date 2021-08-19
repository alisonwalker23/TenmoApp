package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
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
    public boolean transferMoney(Principal principal, String user, BigDecimal amount) {
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

            String sqlSenderUpdate = "UPDATE accounts SET balance = ? WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
            jdbcTemplate.update(sqlSenderUpdate, balanceSender.getBalance(), principal.getName());

            BigDecimal newReceiverBalance = balanceReceiver.getBalance().add(amount);
            balanceReceiver.setBalance(newReceiverBalance);

            String sqlReceiverUpdate = "UPDATE accounts SET balance = ? WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
            jdbcTemplate.update(sqlReceiverUpdate, balanceReceiver.getBalance(), user);

            return true;
        }

        return false;
    }

    @Override
    public void createTransfer(Transfer transfer) {
        try {
            String sqlUpdateTransferRecords = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                    " VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?)";
            /*jdbcTemplate.queryForObject(sqlUpdateTransferRecords, Transfer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());*/
            jdbcTemplate.update(sqlUpdateTransferRecords, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        } catch (Exception ex) {
            System.out.println(ex);
        }



    }

    @Override
    public Transfer getTransferById(int id) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        while (results.next()) {
            transfer.setTransferId(results.getInt("transfer_id"));
            transfer.setTransferTypeId(results.getInt("transfer_type_id"));
            transfer.setTransferStatusId(results.getInt("transfer_status_id"));
            transfer.setAccountFrom(results.getInt("account_from"));
            transfer.setAccountTo(results.getInt("account_to"));
            transfer.setAmount(results.getBigDecimal("amount"));

        }

        return transfer;
    }


}
