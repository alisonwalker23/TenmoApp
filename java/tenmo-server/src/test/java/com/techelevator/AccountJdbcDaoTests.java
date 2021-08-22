package com.techelevator;

import com.techelevator.tenmo.dao.AccountJdbcDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferUser;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountJdbcDaoTests extends TenmoDaoTests {

    private static final Transfer TRANSFER_1 = new Transfer(BigDecimal.valueOf(Long.parseLong("50")), 1987, 2, 2, 1234, 2345);
    private static final Transfer TRANSFER_2 = new Transfer(BigDecimal.valueOf(Long.parseLong("90.50")), 9876, 2, 2, 2345, 1234);
    private static final Transfer TRANSFER_3 = new Transfer(BigDecimal.valueOf(Long.parseLong("20.23")), 8765, 2, 2, 3456, 2345);
    private static final User HARRY_POTTER = new User(9999L, "harrypotter", "harrypotter", "true");

    private AccountJdbcDao dao;
    private JdbcUserDao userDao;
    private TransferUser testTransfer;

    @Before
    public void setup() {
        dao = new AccountJdbcDao(dataSource);
        testTransfer = new TransferUser(1234, 2345, BigDecimal.valueOf(Long.parseLong("30.30")));
    }

    /*@Test
    public void getBalance_returns_correct_balance() {
        Balance expected = dao.getBalance("harrypotter");
        Balance actual = 1000;
    }*/

    /*@Test
    public void createTransfer_creates_transfer() {
        TransferUser transferUser = dao.createTransfer(testTransfer);
        int newId = transferUser.getCurrentUserId();
        transferUser.setCurrentUserId(newId);
    } */

    @Test
    public void getTransferById_returns_correct_transfer() {
        Transfer expected = dao.getTransferById(1987);
        Assert.assertEquals(TRANSFER_1, expected);
    }

    @Test
    public void getAllTransfersByAccountId_returns_all_transfers() {
        Transfer[] transferArray = dao.getAllTransfersByAccountId(1234);
        Assert.assertEquals(2, transferArray.length);
    }

}
