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
import java.util.function.BiFunction;

public class AccountJdbcDaoTests extends TenmoDaoTests {

    private static final Transfer TRANSFER_1 = new Transfer(new BigDecimal(50.00), 1987, 2, 2, 1234, 2345);
    private static final Transfer TRANSFER_2 = new Transfer(new BigDecimal(90.50), 9876, 2, 2, 2345, 1234);
    private static final Transfer TRANSFER_3 = new Transfer(new BigDecimal(20.23), 8765, 2, 2, 3456, 2345);
    private static final User HARRY_POTTER = new User(9999L, "harrypotter", "harrypotter", "true");
    private static final Balance BALANCE_1 = new Balance();

    private AccountJdbcDao dao;
    private JdbcUserDao userDao;
    private TransferUser testTransfer;

    @Before
    public void setup() {
        dao = new AccountJdbcDao(dataSource);
        testTransfer = new TransferUser(1234, 2345, new BigDecimal("30.30"));
    }

    @Test
    public void getBalance_returns_correct_balance() {
        Balance expected = dao.getBalance("harrypotter");
        Balance actual = new Balance();
        actual.setBalance(new BigDecimal(1000));
        assertBalancesMatch(expected, actual);
    }

    /*@Test
    public void createTransfer_creates_transfer() {
        dao.createTransfer(testTransfer);
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(testTransfer.getCurrentUserId());
        transfer.setAccountTo(testTransfer.getReceiverUserId());
        transfer.setAmount(testTransfer.getTransferAmount());
        transfer.setTransferStatusId(2);
        transfer.setTransferTypeId(2);
        int newId = transfer.getTransferId();
        Transfer retrievedTransfer = dao.getTransferById(newId);
        assertTransfersMatch(transfer, retrievedTransfer);
    }*/

    @Test
    public void getTransferById_returns_correct_transfer() {
        Transfer expected = dao.getTransferById(1987);
        assertTransfersMatch(TRANSFER_1, expected);
    }

    @Test
    public void getAllTransfersByAccountId_returns_all_transfers() {
        Transfer[] transferArray = dao.getAllTransfersByAccountId(1234);
        Assert.assertEquals(2, transferArray.length);
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getAmount().compareTo(actual.getAmount()), 0);
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId(), 0.001);
    }
    private void assertBalancesMatch(Balance expected, Balance actual){
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }

}
