package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Transfer {

    BigDecimal amount;
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;

    public Transfer() {

    }

    public Transfer(BigDecimal amount, int transferTypeId, int transferStatusId, int accountFrom, int accountTo) {
        this.amount = amount;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public String toStringFullTransfer(String usernameTo, String usernameFrom) {
        return "\n------------------------------\n" +
                "Transfer Details\n" +
                "------------------------------\n"+
                "ID: " + transferId +
                "\nFrom: " + usernameFrom +
                "\nTo: " + usernameTo +
                "\nType: Send" +
                "\nStatus: Approved"+
                "\nAmount: " + NumberFormat.getCurrencyInstance().format(amount);



    }

    public String toStringLimitedTo(String username) {
        return "ID: " + transferId + "     From: " + username+ "   Amount: " + NumberFormat.getCurrencyInstance().format(amount);

    }
    public String toStringLimitedFrom(String username) {
        return "ID: " + transferId + "     To:   " + username+ "   Amount: " + NumberFormat.getCurrencyInstance().format(amount);

    }
}
