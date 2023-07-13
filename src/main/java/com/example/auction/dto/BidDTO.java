package com.example.auction.dto;

import java.time.LocalDateTime;

public class BidDTO {

    private String bidderName;
    private LocalDateTime bidDate;
    private Object amount;

    public BidDTO(String bidderName, LocalDateTime bidDate, Object amount) {
        this.bidderName = bidderName;
        this.bidDate = bidDate;
        this.amount = amount;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }
}
