package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import com.techelevator.tenmo.model.Balance;

public class TransferUser {

    private int currentUserId;
    private int receiverUserId;
    BigDecimal transferAmount;

    public TransferUser(int currentUserId, int receiverUserId, BigDecimal transferAmount) {
        this.currentUserId = currentUserId;
        this.receiverUserId = receiverUserId;
        this.transferAmount = transferAmount;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void transfer(Balance balance, BigDecimal transfer){

    }

}
