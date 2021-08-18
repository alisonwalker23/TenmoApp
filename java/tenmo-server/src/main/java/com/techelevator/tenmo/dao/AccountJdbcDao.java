package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class AccountJdbcDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   @Override
    public Balance getBalance(String username) {
        Balance balance = new Balance();
        String sql = "SELECT balance FROM accounts JOIN users USING(user_id) WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        if (results.next()) {
            long newBalance = results.getLong("balance");
            BigDecimal newBal = BigDecimal.valueOf(newBalance);
            balance.setBalance(newBal);
        }

        return balance;
    }
}
