package com.techelevator.tenmo.model;

import java.math.BigDecimal;

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

    public String toStringFullTransfer() {
        return "Transfer Information:\n" +
                "Amount: " + amount +
                "| Transfer ID: " + transferId +
                "| Transfer Type ID: " + transferTypeId +
                "| Transfer Status ID: " + transferStatusId +
                "| Account From: " + accountFrom +
                "| Account To: " + accountTo;
    }

    public String toStringLimited() {
        return "Past Transfers:\n" +
                "Amount: " + amount +
                "| Transfer ID: " + transferId +
                "| Account From: " + accountFrom +
                "| Account To: " + accountTo;
    }
}
